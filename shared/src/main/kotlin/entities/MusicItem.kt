package shared.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

data class MusicItem(
    val index: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int
)