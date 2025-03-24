package org.app.marcelodev.comucarona.feature.registeraccount.data.external

import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*


interface RegisterAccountAPI {
    suspend fun registerAccount(
        request: RegisterAccountRequest,
        username: String
    ): HttpResponse

    suspend fun uploadImage(
        file: ByteArray
    ): HttpResponse
}

class RegisterAccountAPIImpl(
    private val client: HttpClient,
) : RegisterAccountAPI {

    override suspend fun registerAccount(request: RegisterAccountRequest, username: String): HttpResponse {
        return client.post("auth/signup/$username") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun uploadImage(file: ByteArray): HttpResponse {
        return client.submitFormWithBinaryData(
            url = "api/files/v1/upload/user-image",
            formData = formData {
                append(
                    key = "file",
                    value = file,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg") // Ajuste o tipo de conteúdo conforme necessário
                        append(HttpHeaders.ContentDisposition, "filename=\"user-image.jpg\"") // Nome do arquivo
                    }
                )
            }
        )
    }
}
