package org.app.marcelodev.comucarona.feature.home.steps.initial.data

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.external.InitialAPI
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.models.AvailableCarRide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences
import org.app.marcelodev.comucarona.service.ktor.UserInformation
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class InitialRepositoryImpl(
    private val initialAPI: InitialAPI,
    private val authPreferences: AuthPreferences
): InitialRepository {
    override suspend fun getAvailableCarRides(): Result<List<AvailableCarRide>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = initialAPI.getAvailableCarRides()
                return@withContext response.handleResultResponse<List<AvailableCarRide>>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getUserInformation(): Result<UserInformation> {
        return withContext(Dispatchers.IO) {
            try {
                val userInformation = UserInformation(
                    name = authPreferences.userName ?: "",
                    photoUrl = authPreferences.photoUrl ?: ""
                )
                Result.success(userInformation)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}