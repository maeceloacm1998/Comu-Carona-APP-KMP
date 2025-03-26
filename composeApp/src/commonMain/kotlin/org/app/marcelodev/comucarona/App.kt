package org.app.marcelodev.comucarona

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import org.app.marcelodev.comucarona.feature.checkcode.ui.CheckCodeRoute
import org.app.marcelodev.comucarona.feature.home.HomeRoutePatern
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences
import org.app.marcelodev.comucarona.theme.AppTypography
import org.koin.compose.KoinContext
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val authPreferences: AuthPreferences = koinInject()

    val startDestination: Screen = if(authPreferences.accessToken != "") {
        HomeRoutePatern()
    } else {
        CheckCodeRoute()
    }


    KoinContext {
        MaterialTheme3(
            typography = AppTypography()
        ) {
            Navigator(
                screen = startDestination,
            ) { navigator ->
                FadeTransition(navigator)
            }
        }
    }
}