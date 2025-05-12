package net.codebot.mobile.data

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.models.PagingObject
import com.adamratzman.spotify.models.PlaylistTrack
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.models.SpotifySearchResult
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market
import net.codebot.mobile.model.Track
import net.codebot.mobile.persistance.DBSong

class SpotifyHandler {
    private val clientID = "7dfcb2f0bd064663aa2a44aee3277e1d"
    private val clientSecret = "19780c316e09451a95b5666032945a5a"
    private var api: SpotifyAppApi? = null

    init {
    }

    suspend fun buildSearchApi() {
        api = spotifyAppApi(clientID, clientSecret).build()
    }

    suspend fun getPlaylist(id: String): List<DBSong> {
        try {
            return parsePlaylist(api!!.playlists.getPlaylistTracks(playlist = id, limit = 50, market = Market.US))
        } catch (e: Exception) {
            return parsePlaylist(api!!.playlists.getPlaylistTracks(playlist = "34NbomaTu7YuOYnky8nLXL", limit = 50, market = Market.US))
        }
    }

    private fun parsePlaylist(playlist: PagingObject<PlaylistTrack>): List<DBSong> {
        val result = mutableListOf<DBSong>()
        for (track in playlist.items) {
            val id = track.track!!.asTrack!!.id
            val name = track.track!!.asTrack!!.name
            val artist = track.track!!.asTrack!!.artists[0].name ?: "Unknown Artist"
            val album = track.track!!.asTrack!!.album.name
            val image = track.track!!.asTrack!!.album.images!![0].url
            val audio = track.track!!.asTrack!!.previewUrl ?: ""
            result.add(DBSong(id, name, artist, album, image, audio, 1000))
        }
        return result
    }
}