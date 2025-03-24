package org.app.marcelodev.comucarona.feature.registeraccount.domain

import org.app.marcelodev.comucarona.feature.registeraccount.data.RegisterAccountRepository
import org.app.marcelodev.comucarona.commons.utils.DeviceUtils
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences

class RegisterAccountUseCase(
    private val registerAccountRepository: RegisterAccountRepository,
    private val photoUseCase: UploadPhotoUseCase,
    private val authPreferences: AuthPreferences
) {

    suspend operator fun invoke(
        fullName: String,
        birthDate: String,
        phoneNumber: String,
        photoBityArray: ByteArray,
    ): Result<Unit> {
        return try {
            val username = DeviceUtils.Companion.create().getUniqueDeviceId().lowercase()
            val request = RegisterAccountRequest(
                fullName = fullName,
                birthDate = birthDate,
                phoneNumber = phoneNumber,
                photoUrl = "http://URL"
            )

            val registerUserResponse = registerAccountRepository.registerAccount(
                request = request,
                username = username
            )

            registerUserResponse.fold(
                onSuccess = { userResponse ->
                    authPreferences.saveTokens(
                        accessToken = userResponse.accessToken!!,
                        refreshToken = userResponse.refreshToken!!
                    )

                    val photoUploadResponse = photoUseCase(photoBityArray)
                    photoUploadResponse.fold(
                        onSuccess = { data ->
                            authPreferences.userName = userResponse.username
                            authPreferences.photoUrl = data.uri
                            Result.success(Unit)
                        },
                        onFailure = { throwable ->
                            Result.failure(throwable)
                        }
                    )
                },
                onFailure = { throwable ->
                    Result.failure(throwable)
                }
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}