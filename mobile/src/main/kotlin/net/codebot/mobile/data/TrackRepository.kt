package net.codebot.mobile.data

import kotlinx.coroutines.runBlocking
import net.codebot.mobile.model.Track
import net.codebot.mobile.persistance.DBSong
import kotlin.random.Random

class TrackRepository {

    // Hardcoded list of tracks
    /*private val trackList = listOf(
        Track("1", "Say What You Say", "Eminem", "The Eminem Show"),
        Track("2", "Candy Shop", "50 Cent", "Get Rich or Die Tryin"),
        Track("3", "123123123", "Artist 3", "Album 3"),
        Track("4", "Song 4", "Artist 4", "Album 4"),
        Track("5", "Song 5", "Artist 5", "Album 5")
    )*/
    private val spotifyApi = SpotifyHandler()

    // Get two random tracks
    /*fun getRandomTracks(): List<Track> {
        return trackList.shuffled().take(2)
    }*/

    // Get all tracks (for ranking purposes)
    fun getAllTracks(playlistId: String): List<DBSong> {
        return runBlocking {
            spotifyApi.buildSearchApi()
            spotifyApi.getPlaylist(playlistId)
        }
    }
}