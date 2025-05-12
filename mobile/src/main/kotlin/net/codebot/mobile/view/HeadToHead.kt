package net.codebot.mobile.view

import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.R
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.data.TrackRepository
import net.codebot.mobile.view.HeadToHeadScreen
import net.codebot.mobile.viewmodel.ViewModel

class HeadToHead(val viewModel: ViewModel, val controller: Controller, val authViewModel: AuthViewModel) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = R.drawable.h2h)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Head 2 Head",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HeadToHeadScreen(viewModel, controller, authViewModel)
    }
}