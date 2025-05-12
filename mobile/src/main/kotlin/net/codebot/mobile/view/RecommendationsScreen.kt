package net.codebot.mobile.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold  // For the Scaffold layout
import androidx.compose.foundation.background  // For the background modifier
import androidx.compose.foundation.layout.*    // For layout elements like Box, padding, height, fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp             // For defining dimensions in dp (like 56.dp for height)
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel
import shared.entities.Task
import shared.entities.MusicItem

// types of actions that our controller understands
enum class RecommendationEvent {
    Load,
    Play,
    Pause,
    Next,
    Previous,
    Like,
    Dislike,
    Skip,
    Share,
    Search,
    Click // Add this event for when the user clicks on a song/item
}

@Composable
fun CustomTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)  // Typical height for top bar
            .background(Color.LightGray)  // Custom background color
            .padding(horizontal = 16.dp, vertical = 8.dp)  // Padding for text alignment
    ) {
        Text(
            text = "Recommendations",
            color = Color.Black,  // Text color
            modifier = Modifier.align(Alignment.CenterStart)  // Align text to the start
        )
    }
}

@Composable
fun RecommendationList(
    viewModel: ViewModel,
    controller: Controller,
    authViewModel: AuthViewModel
) {
    val scrollState = rememberScrollState()
//    var selectedIndex by remember { mutableStateOf(1) }
//    var menuExpanded by remember { mutableStateOf(false) }

    //LaunchedEffect(controller) {
    //    controller.invoke(RecommendationEvent.Load, Unit)
    //}

    Scaffold(
        topBar = {
            CustomTopBar()
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).verticalScroll(scrollState)) {
                for (i in 0..9) {
                    ListItem(
                        headlineContent = { Text("${i + 1}. Title") },
                        supportingContent = { Text("Artist") },
                        trailingContent = {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = "Unfavorite"
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                    )

                    HorizontalDivider(
                        color = Color.Gray, // Custom color
                        thickness = 1.dp,   // Custom thickness
                        modifier = Modifier.padding(vertical = 8.dp) // Add vertical padding
                    )
                }
            }

        }
    )
}


@Composable
fun recommendationRowColor(selected: Boolean) = if (selected) Color.Yellow else Color.Transparent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun recommendationRow(
    musicItem: MusicItem,
    selectedIndex: Int,
    selected: () -> Unit,
    pressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(recommendationRowColor(selectedIndex == musicItem.index))
            .combinedClickable(
                onClick = selected,
                onLongClick = pressed
            )
    ) {
        Text(
            text = "[${musicItem.index}] ${musicItem.title} - ${musicItem.artist}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
