package net.codebot.mobile.persistance

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.codebot.mobile.model.User

/*
 * Supabase storage
 * https://www.youtube.com/watch?v=_iXUVJ6HTHU
 */

@OptIn(DelicateCoroutinesApi::class)
class DBStorage {
    //    val client = HttpClient(CIO)
    val supabase = createSupabaseClient(
        supabaseUrl = "https://atywezuiiaaeaqhmwjen.supabase.co/",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF0eXdlenVpaWFhZWFxaG13amVuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzEwMzMwNjMsImV4cCI6MjA0NjYwOTA2M30.ziI20WInat2QUaouSmTriFWKjxZlSxyk_RoCh-bQvsw"
    ) {
        install(Postgrest)
    }

    fun fetchUser(userId: String): User {
        var user = User()
        runBlocking {
            user = supabase.from("User").select() {
                filter {
                    eq("userid", userId)
                }
            }.decodeAs<User>()
        }
        return user
    }

    fun createUser(user: User) {
//        val json = Json.encodeToJsonElement(user)
        runBlocking {
            supabase.from("User").insert(user)
        }
    }

//    override fun load(): List<Task> {
//        var list = mutableListOf<Task>()
//        runBlocking {
//            withContext(Dispatchers.IO) {
//                list = supabase.from("tasks").select().decodeList<Task>().toMutableList()
//            }
//        }
//        return list
//    }
//
//    override fun save(list: List<Task>) {
//        println("Save not yet implemented")
//    }
}

@Serializable
data class DBPersonalRank (
    val userid: String,
    val songid: String,
    val rank: Int,
)

@Serializable
data class DBSong (
    val songid: String,
    val title: String,
    val artist: String,
    val album: String,
    val imgurl: String,
    val audiourl: String,
    var globalrank: Int,
)

@Serializable
data class DBSongMap (
    val Song: DBSong,
)

@Serializable
data class DBUser (
    val id: String,
    val name: String? = null,
    val profileUrl: String? = null,
    var numMatchupsDecided: Int = 0,
    val faveGenre: String? = null,
    val faveArtist: String? = null,
)