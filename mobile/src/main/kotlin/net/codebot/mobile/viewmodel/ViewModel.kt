package net.codebot.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FieldValue
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.runBlocking
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.model.Model
import net.codebot.mobile.model.ISubscriber
import net.codebot.mobile.model.Track
import net.codebot.mobile.model.User
import net.codebot.mobile.persistance.*
import kotlin.math.pow
import kotlin.math.roundToInt
import com.google.firebase.firestore.FirebaseFirestore
import net.codebot.mobile.model.Logic
import kotlinx.coroutines.tasks.await

class ViewModel(private val model: Model, private val authViewModel: AuthViewModel) : ISubscriber {
    var currentUser by mutableStateOf(model.currentUser)

    var currentMatchup by mutableStateOf(Pair<DBSong?, DBSong?>(null, null))
        private set

    val rankedTracks: MutableList<DBSong> = mutableListOf()

    var currentPlaylist by mutableStateOf("34NbomaTu7YuOYnky8nLXL")

    val DB = DBStorage()

    fun fetchNextMatchup() {
        // add two modes, fetching from DB vs fetching from Spotify playlist
        val tracks = model.getAllTracks(currentPlaylist)
        if (tracks.size < 2) return

        val randomTracks = tracks.shuffled().take(2)
        currentMatchup = Pair(randomTracks[0], randomTracks[1])
    }

    fun skipTrack(skipped: DBSong) {
        val tracks = model.getAllTracks(currentPlaylist)
        if (tracks.isEmpty()) return

        val randomTracks = tracks.shuffled().take(1)
        if (skipped == currentMatchup.first) {
            currentMatchup = Pair(randomTracks[0], currentMatchup.second)
        } else {
            currentMatchup = Pair(currentMatchup.first, randomTracks[0])
        }
    }

    fun updateRanking(winner: DBSong) {

        // add songs to DB if not already
        runBlocking {
            var result = DB.supabase.from("Song").select {
                filter {
                    eq("songid", currentMatchup.first!!.songid)
                }
            }.decodeList<DBSong>()
            if (result.isEmpty()) {
                DB.supabase.from("Song").insert(currentMatchup.first!!)
            }
            result = DB.supabase.from("Song").select {
                filter {
                    eq("songid", currentMatchup.second!!.songid)
                }
            }.decodeList<DBSong>()
            if (result.isEmpty()) {
                DB.supabase.from("Song").insert(currentMatchup.second!!)
            }
        }
        if (authViewModel.signedIn()) {
            // User signed in: update DB data

            val user = authViewModel.getUserId()
            val userID = authViewModel.getUserIdd()
            val firestoreInst = FirebaseFirestore.getInstance()
            val docRef = firestoreInst.collection("users").document(userID)
            docRef.update("rankings", FieldValue.increment(1))

            // update numMatches in DB
            val userRow = runBlocking {
                DB.supabase.from("User").select {
                    filter {
                        eq("id", user)
                    }
                }.decodeSingle<DBUser>()
            }
            userRow.numMatchupsDecided += 1
            runBlocking {
                DB.supabase.from("User").upsert(userRow, onConflict = "id")
            }

            val personalList = runBlocking {
                DB.supabase.from("PersonalRank").select {
                    filter {
                        eq("userid", user)
                    }
                    order(column = "rank", order = Order.ASCENDING)
                }.decodeList<DBPersonalRank>()
            }.toMutableList()

            val loser = if (winner == currentMatchup.first) currentMatchup.second else currentMatchup.first
            val winnerIndex = personalList.indexOfFirst { it.songid == winner.songid }
            val loserIndex = personalList.indexOfFirst { it.songid == loser!!.songid }

            // Case 1: Both winner and loser are already in the rankedTracks list
            if (winnerIndex != -1 && loserIndex != -1) {
                if (winnerIndex > loserIndex) {
                    // Winner is behind the loser, so move winner ahead of loser
                    val removed = personalList.removeAt(winnerIndex) // Remove winner from the list
                    personalList.add(loserIndex, removed) // Insert winner ahead of loser
                }
                // If winner is already ahead of loser, do nothing (keep the same ranking)
            }
            // Case 2: Winner is in the rankedTracks list but loser is not
            else if (winnerIndex != -1 && loserIndex == -1) {
                if (loser != null) {
                    personalList.add(winnerIndex + 1, DBPersonalRank(user, loser.songid, 0))
                } // Add loser right behind the winner
            }
            // Case 3: Loser is in the rankedTracks list but winner is not
            else if (winnerIndex == -1 && loserIndex != -1) {
                personalList.add(loserIndex, DBPersonalRank(user, winner.songid, 0)) // Add winner right before the loser
            }
            // Case 4: Neither winner nor loser is in the rankedTracks list
            else {
                personalList.add(DBPersonalRank(user, winner.songid, 0)) // Add winner first
                if (loser != null) {
                    personalList.add(DBPersonalRank(user, loser.songid, 0))
                }  // Add loser after winner
            }
            // update all ranks
            for (index in personalList.indices) {
                personalList[index] = DBPersonalRank(user, personalList[index].songid, index + 1)
            }
            // update database
            runBlocking {
                DB.supabase.from("PersonalRank").upsert(personalList, onConflict = "userid, songid")
            }
            // Update global rankings HERE
            updateGlobalRanks(winner, loser!!)
        } else {
            // User not signed in: update local rankedTracks
            if (currentUser.numMatchupsDecided == -1) {
                currentUser.numMatchupsDecided = 1
            }
            else {
                currentUser.numMatchupsDecided += 1
            }

            val loser = if (winner == currentMatchup.first) currentMatchup.second else currentMatchup.first
            // Check if either winner or loser is already in the rankedTracks list
            val winnerIndex = rankedTracks.indexOf(winner)
            val loserIndex = rankedTracks.indexOf(loser)

            // Case 1: Both winner and loser are already in the rankedTracks list
            if (winnerIndex != -1 && loserIndex != -1) {
                if (winnerIndex > loserIndex) {
                    // Winner is behind the loser, so move winner ahead of loser
                    rankedTracks.removeAt(winnerIndex) // Remove winner from the list
                    rankedTracks.add(loserIndex, winner) // Insert winner ahead of loser
                }
                // If winner is already ahead of loser, do nothing (keep the same ranking)
            }
            // Case 2: Winner is in the rankedTracks list but loser is not
            else if (winnerIndex != -1 && loserIndex == -1) {
                if (loser != null) {
                    rankedTracks.add(winnerIndex + 1, loser)
                } // Add loser right behind the winner
            }
            // Case 3: Loser is in the rankedTracks list but winner is not
            else if (winnerIndex == -1 && loserIndex != -1) {
                rankedTracks.add(loserIndex, winner) // Add winner right before the loser
            }
            // Case 4: Neither winner nor loser is in the rankedTracks list
            else {
                rankedTracks.add(winner) // Add winner first
                if (loser != null) {
                    rankedTracks.add(loser)
                }  // Add loser after winner
            }
        }

        fetchNextMatchup()
    }

    fun getPersonalList(): List<DBSong> {
        if (authViewModel.signedIn()) {
            // get a list of DBSongs,
            val result = runBlocking {
                val columns = Columns.raw("Song(*)")
                DB.supabase.from("PersonalRank").select(columns) {
                    filter { eq("userid", authViewModel.getUserId()) }
                    order(column = "rank", order = Order.ASCENDING)
                    limit(count = 50)
                }.decodeList<DBSongMap>()
            }
            val DBtracks: MutableList<DBSong> = mutableListOf()
            result.forEach { m -> DBtracks.add(m.Song)}
            println(DBtracks.size)
            return DBtracks
        } else {
            return rankedTracks.take(50)
        }
    }

    fun getUserTop10(userid: String): List<DBSong> {
        val result = runBlocking {
            val columns = Columns.raw("Song(*)")
            DB.supabase.from("PersonalRank").select(columns) {
                filter { eq("userid", userid) }
                order(column = "rank", order = Order.ASCENDING)
                limit(count = 10)
            }.decodeList<DBSongMap>()
        }
        val DBtracks: MutableList<DBSong> = mutableListOf()
        result.forEach { m -> DBtracks.add(m.Song)}
        return DBtracks
    }

    fun getGlobalList(): List<DBSong> {
        // get a list of DBSongs,
        return runBlocking {
            DB.supabase.from("Song").select() {
                order(column = "globalrank", order = Order.DESCENDING)
                limit(count = 50)
            }.decodeList<DBSong>()
        }
    }

    /*fun winningProbability(rank1: Int, rank2: Int): Float {
        return 1.0f / (1 + 10.0f.pow((rank1 - rank2) / 400.0f))
    }*/

    fun updateGlobalRanks(winner: DBSong, loser: DBSong) {
        val winnerRank = runBlocking {
            DB.supabase.from("Song").select() {
                filter { eq("songid", winner.songid) }
            }.decodeSingle<DBSong>()
        }
        val loserRank = runBlocking {
            DB.supabase.from("Song").select() {
                filter { eq("songid", loser.songid) }
            }.decodeSingle<DBSong>()
        }
        val pWinnerWins = Logic.winningProbability(loserRank.globalrank, winnerRank.globalrank)
        val pLoserWins = Logic.winningProbability(winnerRank.globalrank, loserRank.globalrank)
        val k = 30
        winner.globalrank = winnerRank.globalrank + (k * (1 - pWinnerWins)).roundToInt()
        loser.globalrank = loserRank.globalrank - (k * pLoserWins).roundToInt()
        runBlocking {
            DB.supabase.from("Song").upsert(winner, onConflict = "songid")
            DB.supabase.from("Song").upsert(loser, onConflict = "songid")
        }
    }

    fun createUserDB(userName: String) {
        if (authViewModel.signedIn()) {
            println("user is signed in")
            val user = authViewModel.getUserId()
            runBlocking {
                DB.supabase.from("User").upsert(DBUser(id=user, name=userName), onConflict = "id")
            }
            println("finished adding user")
        } else {
            println("user is not signed in")
        }
    }

    fun searchUsers(userName: String): List<DBUser> {
        if (userName.isEmpty()) {
            return emptyList()
        }
        return runBlocking {
            DB.supabase.from("User").select() {
                //filter { eq("name", userName) }
                filter { like("name", "%" + userName + "%") }
                limit(count = 20)
            }.decodeList<DBUser>()
        }
    }

    fun updateUserStat(curUser: User){
        val updatedUser = runBlocking {
            DB.supabase.from("User").select() {
                    filter { eq("id", curUser.id) }
                }
        }.decodeList<DBUser>()

        if (updatedUser.isEmpty()) {
            return
        }
        else {
                curUser.numMatchupsDecided = updatedUser[0].numMatchupsDecided!!
                curUser.name = updatedUser[0].name!!
                curUser.faveGenre = updatedUser[0].faveGenre!!
                curUser.faveArtist = updatedUser[0].faveArtist!!
        }
    }

    init {
        model.subscribe(this)
        fetchNextMatchup()
    }

    override fun update() {
        currentUser = model.currentUser
    }
}
