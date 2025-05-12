package net.codebot.mobile.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel

class Recommendations(
    val recommendationListViewModel: ViewModel,
    val recommendationListController: Controller,
    val authViewModel: AuthViewModel
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Search)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Recommendations",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        RecommendationList(recommendationListViewModel,recommendationListController, authViewModel)
    }
}