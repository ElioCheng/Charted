package net.codebot.mobile.model

import com.google.firebase.auth.FirebaseAuth
import net.codebot.mobile.data.TrackRepository
import net.codebot.mobile.persistance.DBSong
import net.codebot.mobile.persistance.DBStorage

class Model(
    private val database: DBStorage
) : IPublisher() {
    private val firebaseAuthenticator: FirebaseAuth = FirebaseAuth.getInstance()

    var currentUser: User = User()

    init {
//        login()
    }

    fun login() {
        var firebaseUser = firebaseAuthenticator.currentUser
        if (firebaseUser != null) {
            currentUser = database.fetchUser(firebaseUser.uid)
        }
        notifySubscribers()
    }

    fun createUser(userId: String) {
        val newUser = User(id=userId, name="New User", numMatchupsDecided = 0)
        database.createUser(newUser)
    }

    fun getAllTracks(playlistId: String): List<DBSong> {
        val trackRepo = TrackRepository()
        return trackRepo.getAllTracks(playlistId)
    }
}