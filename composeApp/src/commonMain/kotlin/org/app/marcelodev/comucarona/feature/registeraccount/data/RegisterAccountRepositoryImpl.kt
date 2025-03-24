package org.app.marcelodev.comucarona.feature.registeraccount.data

import org.app.marcelodev.comucarona.feature.registeraccount.data.external.RegisterAccountAPI
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class RegisterAccountRepositoryImpl(
    private val registerAccountAPI: RegisterAccountAPI
) : RegisterAccountRepository {

    override suspend fun registerAccount(
        request: RegisterAccountRequest,
        username: String
    ): Result<RegisterAccountResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = registerAccountAPI.registerAccount(
                    request = request,
                    username = username
                )

                return@withContext response.handleResultResponse<RegisterAccountResponse>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


//
//    override suspend fun updatePhoto(photoUri: MultipartBody.Part): Result<PhotoRequest> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = registerAccountAPI.uploadImage(photoUri)
//                Result.success(response)
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//    }
}