package org.app.marcelodev.comucarona.service.ktor.extensions

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.app.marcelodev.comucarona.service.ktor.NetworkingHttpState

/**
 * Handles an HTTP response and returns an encapsulated result.
 *
 * @param T The expected type of the HTTP response body.
 * @return A `Result` object encapsulating the success or failure of the operation.
 *
 * - If the HTTP response status is `HttpStatusCode.OK`, the function attempts to deserialize the response body
 *   into the generic type `T` and returns a `Result.success` containing the deserialized object.
 * - Otherwise, the function prints an error message with the HTTP status code to the console
 *   and returns a `Result.failure` containing an exception with the error message.
 *
 * @throws Throwable If the response status is not `HttpStatusCode.OK`, an exception is encapsulated in the `Result.failure`.
 */
suspend inline fun <reified T> HttpResponse.handleResultResponse(): Result<T> {
    return if (status == HttpStatusCode.OK) {
        val checkCodeResponse = this.body<T>()
        Result.success(checkCodeResponse)
    } else {
        println("Error requisition: ${status.value}")
        Result.failure(Throwable("Error: ${status.value}"))
    }
}

/**
 * Extracts an error state code from a `Throwable`'s message.
 *
 * @receiver Throwable The exception from which the error state code will be extracted.
 * @return The extracted error state code as an `Int`. If the message is null, the code cannot be parsed,
 *         or the format is invalid, it returns `0` as the default value.
 *
 * The function expects the `Throwable`'s message to contain a colon-separated string where the last part
 * represents the error code. For example: "Error: 401" would extract `401` as the error code.
 */
fun Throwable.getStateError(): Int {
    return message?.let {
        it.split(":").lastOrNull()?.trim()?.toIntOrNull()
    } ?: 0
}

/**
 * Handles an HTTP exception based on the error code extracted from a `Throwable`.
 *
 * @receiver Throwable The exception to be handled.
 * @param onUnauthorized A lambda function to be executed when the error code corresponds to an unauthorized error (`401`).
 *                       Default is an empty lambda.
 * @param onForbidden A lambda function to be executed when the error code corresponds to a forbidden error (`403`).
 *                    Default is an empty lambda.
 * @param onNotFound A lambda function to be executed when the error code corresponds to a not found error (`404`).
 *                   Default is an empty lambda.
 * @param others A lambda function to be executed for any other error codes. Default is an empty lambda.
 *
 * The function uses the `getStateError` method to extract the error code from the `Throwable`'s message.
 * It then matches the code against predefined HTTP states (`UNAUTHORIZED`, `FORBIDDEN`, `NOT_FOUND`) from
 * the `NetworkingHttpState` object and executes the corresponding lambda function.
 *
 * - If the error code matches `NetworkingHttpState.UNAUTHORIZED.code`, it logs the error and executes `onUnauthorized`.
 * - If the error code matches `NetworkingHttpState.FORBIDDEN.code`, it logs the error and executes `onForbidden`.
 * - If the error code matches `NetworkingHttpState.NOT_FOUND.code`, it logs the error and executes `onNotFound`.
 * - For any other error codes, it executes the `others` lambda.
 */
fun Throwable.handleHttpException(
    onUnauthorized: () -> Unit = {},
    onForbidden: () -> Unit = {},
    onNotFound: () -> Unit = {},
    others: () -> Unit = {}
) {
    val code = getStateError()
    when (code) {
        NetworkingHttpState.UNAUTHORIZED.code -> {
            println("HTTP EXCEPTION: Erro $code -> Erro de autorização.")
            onUnauthorized()
        }

        NetworkingHttpState.FORBIDDEN.code -> {
            println("HTTP EXCEPTION: Erro $code -> Acesso não autorizado ou usuário não encontrado.")
            onForbidden()
        }

        NetworkingHttpState.NOT_FOUND.code -> {
            println("HTTP EXCEPTION: Erro $code -> Não encontrado.")
            onNotFound()
        }

        else -> others()
    }

}