package org.app.marcelodev.comucarona.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.app.marcelodev.comucarona.feature.home.bottomnavigation.TabItem
import org.app.marcelodev.comucarona.feature.home.steps.initial.ui.InitialRoute

class HomeRoute : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        HomeRoute(uiState = uiState)
    }
}

@Composable
fun HomeRoute(uiState: HomeViewModelUiState) {
    check(uiState is HomeViewModelUiState.Data)

    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        TabNavigator(InitialRoute) {
            Scaffold(
                content = { paddingValues ->
                    CurrentTab()
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.White,
                    ) {
                        TabItem(InitialRoute)
                    }
                }
            )
        }
    }
}