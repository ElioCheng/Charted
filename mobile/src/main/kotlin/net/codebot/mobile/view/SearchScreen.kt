package net.codebot.mobile.view

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import net.codebot.mobile.AuthState
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.R
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel
import net.codebot.mobile.persistance.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalFocusManager
import net.codebot.mobile.ui.theme.LightPink

@Composable
fun viewProfile(viewModel: ViewModel, user: DBUser) {

    Scaffold(
        topBar = {
            Row (
                modifier = Modifier.fillMaxWidth().background(
                    color = LightPink
                )
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.dog),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,  // Ensures the image is cropped to fit within the shape
                        modifier = Modifier
                            .size(100.dp)  // Set the size of the image
                            .padding(10.dp)
                            .clip(CircleShape)  // Crop the image to a circle shape
                    )
                }
                Column () {
                    Row (modifier = Modifier.background(Color.Transparent)) { Text(text = "User: " + user.name, fontSize = 18.sp) }
                    Row (modifier = Modifier.background(Color.Transparent)) { Text(text = "ID: " + user.id, fontSize = 18.sp) }
                    Row (modifier = Modifier.background(Color.Transparent)) { Text("Match-Ups decided: " + user.numMatchupsDecided, fontSize = 18.sp) }
                }
            } }
    ) { innerPadding ->
        Card ( modifier = Modifier.padding(innerPadding) ) {
            val scrollState = rememberScrollState()
            Column (modifier = Modifier.verticalScroll(scrollState)) {
                Text(user.name + "'s Top 10")
                val rankedTracks = viewModel.getUserTop10(user.id)

                if (rankedTracks.isEmpty()) {
                    // Handle the case when rankedTracks is empty
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Make the Box fill the entire screen
                        contentAlignment = Alignment.Center // Center content within the Box
                    ) {
                        Text("No Songs Ranked")
                    }
                }
                else {
                    rankedTracks.forEachIndexed { index, track ->
                        var trackTitle = "${1 + rankedTracks.indexOf(track)}: ${track.title}"
                        if (trackTitle.length > 25) {
                            trackTitle = trackTitle.substring(0, 25) + "..."
                        }
                        ListItem(
                            headlineContent = { Text(trackTitle, fontSize = 22.sp) },
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
    }
}

@Composable
fun SearchScreen(viewModel: ViewModel, controller: Controller, authViewModel: AuthViewModel, navigator: TabNavigator) {
    var username by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<DBUser>>(emptyList()) }
    // State to track the currently selected item (null if nothing is selected)
    var selectedUser by remember { mutableStateOf<DBUser?>(null) }
    val focusManager = LocalFocusManager.current

    Box(Modifier.fillMaxSize()
        .clickable(
            onClick = { focusManager.clearFocus() },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Username") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    // Call Foo with the query and update searchResults
                    searchResults = viewModel.searchUsers(username)
                    selectedUser = null // Reset the selected item when searching
                }) {
                    Text("Search")
                }
            }

            // Display results or selected item
            if (selectedUser == null) {
                // Show the list of search results
                LazyColumn {
                    items(searchResults) { result ->
                        ListItem(
                            headlineContent = { Text(result.id, fontSize = 22.sp) },
                            supportingContent = { Text(result.name!!, fontSize = 18.sp) },
                            modifier = Modifier.clickable { selectedUser = result }
                        )
                        HorizontalDivider(
                            color = Color.Black, // Customize the color
                            thickness = 1.dp,  // Customize the thickness
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                // Show the selected item's details
                viewProfile(viewModel, selectedUser!!)
            }
        }
    }
}