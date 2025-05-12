package net.codebot.mobile.view;

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel


class SignUp(val viewModel: ViewModel, val controller: Controller, val authViewModel: AuthViewModel, val navigator: TabNavigator) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Email)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "SignUp",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        SignUpPage(viewModel, controller, authViewModel, navigator)
    }
}