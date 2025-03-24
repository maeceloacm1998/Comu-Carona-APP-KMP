package org.app.marcelodev.comucarona.feature.checkcode.data.external

import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface CheckCodeAPI {
    suspend fun checkCode(checkCodeRequest: CheckCodeRequest): HttpResponse
}

class CheckCodeAPIImpl(
    private val client: HttpClient,
) : CheckCodeAPI {
    override suspend fun checkCode(checkCodeRequest: CheckCodeRequest): HttpResponse {
        return client.post("auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(checkCodeRequest)
        }
    }
}
