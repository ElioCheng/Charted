package net.codebot.mobile.view

import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.TabNavigator
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.R
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel

class Profile(val viewModel: ViewModel, val controller: Controller, val authViewModel: AuthViewModel , val navigator: TabNavigator) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = R.drawable.profile)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Profile",
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        ProfileScreen(viewModel, controller, authViewModel, navigator)
    }
}