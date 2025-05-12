package net.codebot.mobile.view

import androidx.compose.foundation.*
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


@Composable
fun ProfileScreen(viewModel: ViewModel, controller: Controller, authViewModel: AuthViewModel, navigator: TabNavigator) {
    val authState = authViewModel.authState.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    val userID = authViewModel.getUserIdd()
    var rankNum by remember { mutableStateOf<Int?>(null) }
    var userString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authState.value) {
        val firestoreInst = FirebaseFirestore.getInstance()
        val docRef = firestoreInst.collection("users").document(userID)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Access the "rankings" field
                    rankNum = document.getLong("rankings")?.toInt()!!
                } else {
                    println("No such document!")
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching document: ${exception.message}")
            }

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Access the "rankings" field
                    userString = document.getString("username").toString()
                } else {
                    println("No such document!")
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching document: ${exception.message}")
            }

        when(authState.value){
            is AuthState.Unauthenticated -> navigator.current = Login(viewModel, controller, authViewModel, navigator)
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            Row (
                modifier = Modifier.fillMaxWidth().background(
                    color = Color.LightGray
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
                    /*Button(onClick = { showDialog = true }) {
                        Text("Logout")
                    }

                    // Confirmation Dialog
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    authViewModel.logoutUser() // Call the logout function
                                    showDialog = false
                                }) {
                                    Text("Logout")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancel")
                                }
                            },
                            title = { Text("Confirm Logout") },
                            text = { Text("Are you sure you want to logout?") }
                        )
                    }*/
                }
                Column () {
                    Spacer(modifier = Modifier.height(25.dp))
                    Row (modifier = Modifier.background(Color.Transparent)) { Text(text = "User: " + userString, fontSize = 18.sp) }
                    Row (modifier = Modifier.background(Color.Transparent)) { Text("Match-Ups decided: " + rankNum, fontSize = 18.sp) }
                    //Row (modifier = Modifier.background(Color.Transparent)) { Text("Favourite Genre: " + viewModel.currentUser.faveGenre, fontSize = 18.sp) }
                    //Row (modifier = Modifier.background(Color.Transparent)) { Text("Favourite Artist: " + viewModel.currentUser.faveArtist, fontSize = 18.sp) }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Button(onClick = { showDialog = true }, contentPadding = PaddingValues(3.dp), shape = CircleShape, modifier = Modifier.size(60.dp).padding(8.dp)) {
                        //Text("Logout")
                        Image(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = "Logout",
                            contentScale = ContentScale.Crop,  // Ensures the image is cropped to fit within the shape
                            modifier = Modifier
                                .size(30.dp)  // Set the size of the image
                        )
                    }

                    // Confirmation Dialog
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    authViewModel.logoutUser() // Call the logout function
                                    showDialog = false
                                }) {
                                    Text("Logout")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancel")
                                }
                            },
                            title = { Text("Confirm Logout") },
                            text = { Text("Are you sure you want to logout?") }
                        )
                    }
                }
            } }
    ) { innerPadding ->
        Card ( modifier = Modifier.padding(innerPadding) ) {
            val scrollState = rememberScrollState()
            Column (modifier = Modifier.verticalScroll(scrollState)) {
                Text("Your Top 10")
                val rankedTracks = viewModel.getPersonalList()

                if (rankedTracks.isEmpty()) {
                    // Handle the case when rankedTracks is empty
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Make the Box fill the entire screen
                            contentAlignment = Alignment.Center // Center content within the Box
                    ) {
                        Text("Do some head-to-head first")
                    }
                }
                else {
                    rankedTracks.take(10).forEachIndexed { index, track ->
                        var trackTitle = "${1 + rankedTracks.indexOf(track)}: ${track.title}"
                        if (trackTitle.length > 25) {
                            trackTitle = trackTitle.substring(0, 25) + "..."
                        }
                        ListItem(
                            headlineContent = { Text(trackTitle, fontSize = 22.sp) },
                            supportingContent = { Text(" ${track.artist}") },
//                            trailingContent = { Text("Go") },
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