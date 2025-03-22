package org.app.marcelodev.comucarona

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilder
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            Navigator(
                screen = Game1(),
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
        val screenModel = koinScreenModel<GameViewModel>()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Game 1")
            Button(onClick = {
                screenModel.getTest()
            }) {
                Text("Click me")
            }
        }
    }
}

class GameViewModel(
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
}

class Game2 : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Game 2")
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
        viewModel { GameViewModel(get()) }
    }
}