package org.app.marcelodev.comucarona.feature.checkcode.data

import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeRequest
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.feature.checkcode.data.external.CheckCodeAPI
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class CheckCodeRepositoryImpl(
    private val checkCodeAPI: CheckCodeAPI
) : CheckCodeRepository {
    override suspend fun checkCode(checkCodeRequest: CheckCodeRequest): Result<CheckCodeResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = checkCodeAPI.checkCode(checkCodeRequest)

                return@withContext response.handleResultResponse<CheckCodeResponse>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}