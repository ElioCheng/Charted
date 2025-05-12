package net.codebot.mobile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.DefaultLoadControl
import com.google.firebase.firestore.FieldValue
import io.github.jan.supabase.realtime.Column
import net.codebot.mobile.R

@Composable
fun AudioPlayer(url: String, playingUrl: String, setPlayingUrl: (String) -> Unit) {
    if (url.isEmpty()) {
        Text("Audio Sample Unavailable", fontSize = 18.sp, textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp))
        return
    }
    // State for the ExoPlayer instance
    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Get context here
    val context = LocalContext.current

    // Initialize the player inside LaunchedEffect
    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            30_000,       // Minimum buffer size (30 seconds in milliseconds)
            30_000,       // Maximum buffer size (same as the clip duration)
            5_000,  // Buffer needed for playback to start
            5_000 // Buffer needed after re-buffer
        )
        .build()
    LaunchedEffect(url) {
        isPlaying = false
        player?.pause()
        player?.release()
        player = ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false
            playbackParameters = PlaybackParameters(1.0f)
        }
    }
    LaunchedEffect(playingUrl) {
        if (url != playingUrl) {
            isPlaying = false
            player?.pause()
        }
        if (url == playingUrl) {
            isPlaying = true
            player?.play()
        }
    }

    // Clean up the player when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            player?.pause()
            player?.release()
            player = null
        }
    }

    // Create the audio control UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
            // Play/Pause Button
            Button(onClick = {
                if (isPlaying) {
                    setPlayingUrl("")
                    //player?.pause()
                } else {
                    setPlayingUrl(url)
                    //player?.play()
                }
                //isPlaying = !isPlaying
            }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            // Stop Button
            Button(onClick = {
                player?.seekTo(0) // Seek to the beginning
                if (isPlaying) {
                    setPlayingUrl("")
                }
            }) {
                Text("Stop")
            }
//        }
    }
}

@Composable
fun PlaylistSelector(viewModel: ViewModel) {
    var showMenu by remember { mutableStateOf(false) }
    var playlistUrl by remember { mutableStateOf("Pop Hits 2024") }

    fun cleanUrl(url: String) : String {
        if (url.startsWith("http")) {
            var newUrl = url.removePrefix("https://open.spotify.com/playlist/")
            newUrl = newUrl.substringBefore("?")
            return newUrl
        }
        return url
    }

    Box(modifier = Modifier.clickable { showMenu = true })
    {
          Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Settings Icon"
          )
    }

    if (showMenu) {
        Popup(onDismissRequest = { showMenu = false }, properties = PopupProperties(focusable = true)) {
            Surface(
                modifier = Modifier.padding(16.dp).wrapContentHeight(),
            ) {
                Column (
                    modifier = Modifier.background(
                        Color.LightGray
                    ).padding(10.dp),
                ){
                    Text("Ranking songs from:")
                    TextField(
                        value = playlistUrl,
                        onValueChange = { playlistUrl = it },
                        label = { Text("Playlist") },

                        )
                    Row {
                        Button(onClick = {
                            viewModel.currentPlaylist = cleanUrl(playlistUrl)
                            viewModel.fetchNextMatchup()
                        }) {
                            Text("Apply Changes")
                        }
                        Button(onClick = {
                            playlistUrl = "Pop Hits 2024"
                            viewModel.currentPlaylist = "34NbomaTu7YuOYnky8nLXL"
                            viewModel.fetchNextMatchup()
                        }) {
                            Text("Return to default")
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun HeadToHeadScreen(viewModel: ViewModel, controller: Controller, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()
    var playingUrl by remember { mutableStateOf("") }
    val (track1, track2) = viewModel.currentMatchup

    Column(modifier = Modifier.fillMaxSize().padding(start=16.dp, end=16.dp)
        .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_no_background),
                contentDescription = "Charted Logo",
                contentScale = ContentScale.Crop,  // Ensures the image is cropped to fit within the shape
                modifier = Modifier
                            .size(40.dp)  // Set the size of the image
                            .padding(4.dp)
            )
            Text(
                text = "Charted",
                fontSize = 20.sp,
                modifier = Modifier.
                    weight(1f) // Pushes the next item to the end
            )
            PlaylistSelector(viewModel)
        }
        Spacer(modifier = Modifier.height(12.dp))

        track1?.let {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                    AsyncImage(
                        model = it.imgurl,
                        contentDescription = "Album Cover",
                        modifier = Modifier.fillMaxWidth(0.5f).clickable {
                            viewModel.updateRanking(it)
                        }
                    )
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Spacer(modifier = Modifier.height(45.dp))
//                        AudioPlayer(it.audiourl, playingUrl, setPlayingUrl = { playingUrl = it })
                        Button(
                            onClick = { viewModel.updateRanking(it) },
                        ) {
                            Text("Choose Track 1")
                        }
                        Button(onClick = {
                            //playingUrl = ""
                            viewModel.skipTrack(it)
                        }) {
                            Text("Skip")
                        }
                    }

                }

                Text("Track 1: ${it.title}\n by ${it.artist}", fontSize = 18.sp, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        track2?.let {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    AsyncImage(
                        model = it.imgurl,
                        contentDescription = "Album Cover",
                        modifier = Modifier.fillMaxWidth(0.5f).clickable {
                            viewModel.updateRanking(it)
                        }
                    )
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Spacer(modifier = Modifier.height(45.dp))
//                        AudioPlayer(it.audiourl, playingUrl, setPlayingUrl = { playingUrl = it })
                        Button(
                            onClick = { viewModel.updateRanking(it) },
                        ) {
                            Text("Choose Track 2")
                        }
                        Button(onClick = {
                            //playingUrl = ""
                            viewModel.skipTrack(it)
                        }) {
                            Text("Skip")
                        }
                    }

                }
                Text("Track 1: ${it.title}\n by ${it.artist}", fontSize = 18.sp, textAlign = TextAlign.Center)
            }
        }
    }
}