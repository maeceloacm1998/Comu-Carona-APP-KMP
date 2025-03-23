package org.app.marcelodev.comucarona.feature.checkcode.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.checkcode.domain.CheckCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.service.ktor.NetworkingHttpState
import org.koin.core.component.KoinComponent

class CheckCodeViewModel(
    private val navigator: Navigator,
    private val checkCodeUseCase: CheckCodeUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(CheckCodeViewModelState(isLoading = false))

    val uiState = viewModelState
        .map(CheckCodeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: CheckCodeViewModelEventState) {
        when (event) {
            is CheckCodeViewModelEventState.OnChangedCode -> onChangedCode(event.code)
            is CheckCodeViewModelEventState.OnClickCheckCode -> onClickCheckCode()
        }
    }

    private fun onChangedCode(code: List<String>) {
        viewModelState.update { it.copy(code = code) }
    }

    private fun onClickCheckCode() {
        val isEnabled = viewModelState.value.code.all { it.isNotEmpty() }
        if (isEnabled) {
            viewModelScope.launch {
                onUpdateLoadingState(true)
                val result = checkCodeUseCase(
                    code = viewModelState.value.code.joinToString("")
                )

                result.fold(
                    onSuccess = {
                        onUpdateLoadingState(false)
                        onUpdateSuccessState(true)
                        println("Success: $it")
//                        onGoToHome()
                    },
                    onFailure = { throwable ->
                        println("Failure: ${throwable.message}")
                        val statusCode = throwable.message?.let {
                            // Extraímos o código do erro diretamente da mensagem da exceção
                            it.split(":").lastOrNull()?.trim()?.toIntOrNull()
                        } ?: 0

                        println("O Status Code dessa requisição é: $statusCode")

                        when (statusCode) {
                            NetworkingHttpState.UNAUTHORIZED.code -> {
                                // enter when the code is incorrect
                                onUpdateLoadingState(false)
                                onUpdateErrorState(true)
                            }

                            NetworkingHttpState.FORBIDDEN.code -> {
                                // enter when the user identifier is incorrect
                                onUpdateLoadingState(false)
                                onUpdateErrorState(false)
                                onUpdateSuccessState(true)
                                println("Tela de registro")
//                                onGoToRegisterAccount()
                            }

                            else -> {
                                onUpdateLoadingState(false)
                                onUpdateErrorState(true)
                            }
                        }
                    }
                )
            }
        }
    }

    private fun onGoToHome() {
        // TODO ADICIONAR ROTA DA HOME
//        NavigationUtils.replaceAllScreens(navigator, Routes.Home)
    }

    private fun onGoToRegisterAccount() {
    // TODO ADICIONAR ROTA DE CADASTRO
    //        NavigationUtils.replaceAllScreens(navigator, Routes.Home)
    }

    private fun onUpdateLoadingState(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateSuccessState(isSuccess: Boolean) {
        viewModelState.update { it.copy(isSuccess = isSuccess) }
    }

    private fun onUpdateErrorState(isError: Boolean) {
        viewModelState.update { it.copy(isError = isError) }
    }


}