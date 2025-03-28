package org.app.marcelodev.comucarona.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.app.marcelodev.comucarona.feature.home.bottomnavigation.TabItem
import org.app.marcelodev.comucarona.feature.home.steps.initial.ui.InitialRoute
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressRoute
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileRoute
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui.RideInProgressRoute

class HomeRoutePatern : Screen {
    @Composable
    override fun Content() {
        HomeRoute()
    }
}

@Composable
fun HomeRoute() {
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
                        TabItem(RideInProgressRoute)
                        TabItem(MyRideInProgressRoute)
                        TabItem(ProfileRoute)
                    }
                }
            )
        }
    }
}