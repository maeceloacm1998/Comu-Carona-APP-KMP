package org.app.marcelodev.comucarona.feature.registeraccount.data.external

import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.request.*


interface RegisterAccountAPI {
    suspend fun registerAccount(
        request: RegisterAccountRequest,
        username: String
    ): HttpResponse

//    suspend fun uploadImage(
//        file: MultipartBody.Part
//    ): HttpResponse

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

//
//    override suspend fun uploadImage(file: File): HttpResponse {
//        return client.post("api/files/v1/upload/user-image") {
//            contentType(ContentType.Application.Json)
//            MultiPartFormDataContent(
//                formData {
//                    append("file", file.readBytes(), Headers.build {
//                        append(HttpHeaders.ContentType, "image/jpeg")  // Ou outro tipo de arquivo
//                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
//                    })
//                })
//        }
//    }
}
