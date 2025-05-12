package net.codebot.mobile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel

@Composable
fun RankingScreen(viewModel: ViewModel, controller: Controller, authViewModel: AuthViewModel) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Text("Personal Rankings", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(10.dp))

        Column(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 50.dp).verticalScroll(scrollState)) {

            val rankedTracks = viewModel.getPersonalList()
            rankedTracks.forEachIndexed { index, track ->
                var trackTitle = ": ${track.title}"
                if (trackTitle.length > 25) {
                    trackTitle = trackTitle.substring(0, 25) + "..."
                }

                val textStyle = if (index < 3) {
                    TextStyle(
                        fontWeight = FontWeight.Bold, // Make it bold
                        fontSize = 24.sp // Customize font size as desired
                    )
                } else {
                    TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp
                    )
                }
                val rankColor = when (index) {
                    0 -> Color(0xFFD4AF37) // Gold
                    1 -> Color(0xFFC0C0C0) // Silver
                    2 -> Color(0xFFCD7F32) // Bronze
                    else -> Color.Black // Default rank color for others
                }

                ListItem(
                    headlineContent = {
                        Text(
                            text = buildAnnotatedString {
                                // Styled rank number
                                withStyle(
                                    style = SpanStyle(
                                        color = rankColor,
                                        fontWeight = textStyle.fontWeight,
                                        fontSize = textStyle.fontSize
                                    )
                                ) {
                                    append("${index + 1} ")
                                }
                                // Track title
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontWeight = textStyle.fontWeight,
                                        fontSize = textStyle.fontSize
                                    )
                                ) {
                                    append(trackTitle)
                                }
                            }
                        )
                    },
                    supportingContent = { Text(" ${track.artist}") },
                    leadingContent = {
                        AsyncImage(
                            model = track.imgurl,
                            contentDescription = "Album Cover",
                            modifier = Modifier.height(50.dp)
                        )
                    }
                )
            }
        }
    }
}
