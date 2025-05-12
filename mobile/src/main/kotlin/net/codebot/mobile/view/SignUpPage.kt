package net.codebot.mobile.view


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import net.codebot.mobile.AuthState
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.R
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel

@Composable
fun SignUpPage(viewModel: ViewModel, controller: Controller, authViewModel: AuthViewModel, navigator: TabNavigator) {
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    val userAuthStatus = authViewModel.authState.observeAsState()
    val focusManager = LocalFocusManager.current

    val currentContext = LocalContext.current

    LaunchedEffect(userAuthStatus.value) {
        when (val state = userAuthStatus.value) {
            is AuthState.Authenticated -> {
                navigator.current = Profile(viewModel, controller, authViewModel, navigator)
            }
            is AuthState.Error -> Toast.makeText(
                currentContext,
                state.errorMessage, Toast.LENGTH_SHORT
            ).show()
            else -> {}
        }
    }
    Box(Modifier.fillMaxSize()
        .clickable(
            onClick = { focusManager.clearFocus() },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_no_background),
                contentDescription = "Charted Logo",
                contentScale = ContentScale.Crop,  // Ensures the image is cropped to fit within the shape
                modifier = Modifier
                    .size(120.dp)  // Set the size of the image
                    .padding(4.dp)
            )
            Text(text = "Sign Up", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username") },
                modifier = Modifier.width(250.dp).height(70.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                modifier = Modifier.width(250.dp).height(70.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.width(250.dp).height(70.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                authViewModel.signup(
                    userEmail,
                    userPassword,
                    userName,
                    viewModel::createUserDB,
                    { navigator.current = Profile(viewModel, controller, authViewModel, navigator) })
            }) { Text(text = "Create an Account") }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                navigator.current = Login(viewModel, controller, authViewModel, navigator)
            }) { Text(text = "Already have an account? Login.") }
        }
    }
}