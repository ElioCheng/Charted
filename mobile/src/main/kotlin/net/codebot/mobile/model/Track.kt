package net.codebot.mobile.model

data class Track(
    val id: String,            // Unique identifier
    val name: String,       // Track name
    val artist: String,     // Artist name
    val album: String,       // Album name
    val image: String,      // url of album cover
    val audio: String       // url of audio preview
)