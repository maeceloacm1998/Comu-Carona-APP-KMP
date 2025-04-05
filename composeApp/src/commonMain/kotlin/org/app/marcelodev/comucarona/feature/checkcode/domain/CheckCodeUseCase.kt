package org.app.marcelodev.comucarona.feature.checkcode.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.commons.utils.DeviceUtils
import org.app.marcelodev.comucarona.feature.checkcode.data.CheckCodeRepository
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeRequest
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeResponse
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences

class CheckCodeUseCase(
    private val repository: CheckCodeRepository,
    private val authPreferences: AuthPreferences
) {
    suspend operator fun invoke(code: String): Result<CheckCodeResponse> {
        val deviceUtils = DeviceUtils.create()
        val userIdentifier = deviceUtils.getUniqueDeviceId().lowercase()
        println("Advertising ID: $userIdentifier")

        return try {
            run {
                val request = CheckCodeRequest(code = code, username = "b4a5854d5efcafbc")
                return repository.checkCode(request).fold(
                    onSuccess = { checkCodeResponse ->
                        authPreferences.saveTokens(
                            accessToken = checkCodeResponse.accessToken,
                            refreshToken = checkCodeResponse.refreshToken
                        )
                        authPreferences.userName = checkCodeResponse.username
                        authPreferences.photoUrl = checkCodeResponse.photoUrl
                        Result.success(checkCodeResponse)
                    },
                    onFailure = { throwable ->
                        Result.failure(Exception(throwable.message))
                    }
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}