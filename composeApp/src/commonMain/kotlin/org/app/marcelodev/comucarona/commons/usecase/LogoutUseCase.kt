package org.app.marcelodev.comucarona.commons.usecase

import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.checkcode.ui.CheckCodeRoute
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences

class LogoutUseCase(
    private val authPreferences: AuthPreferences,
) {
    operator fun invoke(
        navigator: Navigator
    ) {
        authPreferences.apply {
            photoUrl = null
            userName = null
            clearTokens()
        }

        NavigationUtils.replaceAllScreens(navigator, CheckCodeRoute())
    }
}