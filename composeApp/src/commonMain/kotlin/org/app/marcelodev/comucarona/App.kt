package org.app.marcelodev.comucarona

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.FadeTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.checkcode.ui.CheckCodeRoute
import org.app.marcelodev.comucarona.feature.home.HomeRoute
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilder
import org.app.marcelodev.comucarona.theme.AppTypography
import org.koin.compose.KoinContext
import org.koin.core.component.KoinComponent
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    val authPreferences: AuthPreferences = koinInject()

    val startDestination: Screen = if(authPreferences.accessToken != "") {
        HomeRoute()
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

class Game1 : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<GameViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game 1",
                style = MaterialTheme3.typography.titleMedium
            )
            Button(onClick = {
                screenModel.onGoGame2Clicked()
            }) {
                Text("Click me")
            }
        }
    }
}

class GameViewModel(
    private val navigator: Navigator,
    private val sharedPreferences: SharedPreferencesBuilder
) : ScreenModel, ViewModel(), KoinComponent {
    var count by mutableStateOf(0)

    init {
        sharedPreferences.putString("test", "Hello, World!")
    }

    fun getTest() {
        CoroutineScope(Dispatchers.IO).launch {
            val test = withContext(Dispatchers.IO) {
                sharedPreferences.getString("test", "")
            }
            println(test)
        }
    }

    fun onGoGame2Clicked() {
        NavigationUtils.addNewScreen(
            navigator = navigator,
            screen = Game2()
        )
    }
}

class Game2 : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game 2",
                style = MaterialTheme3.typography.labelSmall
            )
            Button(onClick = {
                navigator.pop()
            }) {
                Text("Go back")
            }
        }
    }
}

object GameModule {
    val module = module {
        viewModel { params -> GameViewModel(navigator = params.get(), sharedPreferences = get()) }
    }
}