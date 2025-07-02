package org.app.marcelodev.comucarona.feature.registeraccount.domain

import org.app.marcelodev.comucarona.feature.registeraccount.data.RegisterAccountRepository
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.PhotoRequest
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences

class UploadPhotoUseCase(
    private val repository: RegisterAccountRepository,
    private val authPreferences: AuthPreferences
) {
    suspend operator fun invoke(
        photoUri: ByteArray
    ): Result<PhotoRequest> {
        return try {
            val photoUploadResponse = repository.updatePhoto(photoUri)
            photoUploadResponse.fold(
                onSuccess = { data ->
                    authPreferences.photoUrl = data.uri
                    Result.success(data)
                },
                onFailure = { throwable ->
                    Result.failure(throwable)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    operator fun invoke(image: String) {
        authPreferences.photoUrl = image
    }
}