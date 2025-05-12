package net.codebot.mobile.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String = "-1",
    var name: String = "Default",
    var profileUrl: String = "Default",
    var numMatchupsDecided: Int = -1,
    var faveGenre: String = "Default",
    var faveArtist: String = "Default"
) {
    val uid: String = "default"
}
