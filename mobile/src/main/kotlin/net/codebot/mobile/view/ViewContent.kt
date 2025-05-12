package net.codebot.mobile.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import net.codebot.mobile.AuthViewModel
import net.codebot.mobile.controller.Controller
import net.codebot.mobile.viewmodel.ViewModel
import androidx.compose.foundation.layout.size

@Composable
fun Content(VM: ViewModel, C: Controller, authViewModel: AuthViewModel) {
    val viewModel by remember { mutableStateOf(VM) }
    val controller by remember { mutableStateOf(C) }

    TabNavigator (
        HeadToHead(viewModel, controller, authViewModel),
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(GlobalList(viewModel, controller, authViewModel), Ranking(viewModel, controller, authViewModel), HeadToHead(viewModel, controller, authViewModel), Search(viewModel, controller, authViewModel, it), Profile(viewModel, controller, authViewModel, it))
            )
        }
    ) { tabNavigator ->
        Scaffold(
            content = {
                innerPadding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {CurrentTab()}
            },
            bottomBar = {
                NavigationBar( ) {
                    // TabNavigationItem(Login(viewModel, controller, authViewModel, tabNavigator))
                    // TabNavigationItem(SignUp(viewModel, controller, authViewModel, tabNavigator))
                    // TabNavigationItem(Recommendations(viewModel, controller, authViewModel))
                    TabNavigationItem(GlobalList(viewModel, controller, authViewModel))
                    TabNavigationItem(Ranking(viewModel, controller, authViewModel))
                    TabNavigationItem(HeadToHead(viewModel, controller, authViewModel))
                    TabNavigationItem(Search(viewModel, controller, authViewModel, tabNavigator))
                    TabNavigationItem(Profile(viewModel, controller, authViewModel, tabNavigator))
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title, modifier = Modifier.size(26.dp)) }
    )
}