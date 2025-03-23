package org.app.marcelodev.comucarona.feature.checkcode.data

import io.ktor.client.call.*
import io.ktor.http.*
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeRequest
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.feature.checkcode.data.external.CheckCodeAPI

class CheckCodeRepositoryImpl(
    private val checkCodeAPI: CheckCodeAPI
) : CheckCodeRepository {
    override suspend fun checkCode(checkCodeRequest: CheckCodeRequest): Result<CheckCodeResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = checkCodeAPI.checkCode(checkCodeRequest)

                return@withContext if (response.status == HttpStatusCode.OK) {
                    val checkCodeResponse = response.body<CheckCodeResponse>()
                    Result.success(checkCodeResponse)
                } else {
                    println("Error requisition: ${response.status.value}")
                    Result.failure(Throwable("Error: ${response.status.value}"))
                }
            } catch (e: Exception) {
                println("Ciau aqui, por isso da erro estranho")
                Result.failure(e)
            }
        }
    }
}