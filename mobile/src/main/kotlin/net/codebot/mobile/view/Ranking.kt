package net.codebot.mobile.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.R
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel

class Ranking(val viewModel: ViewModel, val controller: Controller, val authViewModel: AuthViewModel) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(id = R.drawable.personal)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Ranking",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        RankingScreen(viewModel, controller, authViewModel)
    }
}