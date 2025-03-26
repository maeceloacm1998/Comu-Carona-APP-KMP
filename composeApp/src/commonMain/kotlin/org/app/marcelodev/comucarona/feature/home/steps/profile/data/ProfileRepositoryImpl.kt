package org.app.marcelodev.comucarona.feature.home.steps.profile.data

import org.app.marcelodev.comucarona.feature.home.steps.profile.data.external.ProfileAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class ProfileRepositoryImpl(
    private val profileAPI: ProfileAPI
) : ProfileRepository {
    override suspend fun getProfile(): Result<RegisterAccountRequest> {
        return withContext(Dispatchers.IO) {
            try {
                val response = profileAPI.getProfile()
                return@withContext response.handleResultResponse<RegisterAccountRequest>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateProfile(newUserInformation: RegisterAccountRequest): Result<RegisterAccountRequest> {
        return withContext(Dispatchers.IO) {
            try {
                val response = profileAPI.updateProfile(newUserInformation)
                return@withContext response.handleResultResponse<RegisterAccountRequest>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}