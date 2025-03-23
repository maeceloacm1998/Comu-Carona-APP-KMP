package org.app.marcelodev.comucarona.commons.usecase

import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences

class LogoutUseCase(
    private val authPreferences: AuthPreferences,
) {
    operator fun invoke(
        navigator: Navigator
    ) {
        authPreferences.clearTokens()

        // Adicionar Rota de checkCode
//        NavigationUtils.replaceAllScreens(navigator, Routes.CheckCode)
    }
}