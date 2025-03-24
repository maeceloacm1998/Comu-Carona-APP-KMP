package org.app.marcelodev.comucarona.feature.registeraccount.data

import org.app.marcelodev.comucarona.feature.registeraccount.data.models.PhotoRequest
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountResponse

interface RegisterAccountRepository {
    suspend fun registerAccount(
        request: RegisterAccountRequest,
        username: String
    ): Result<RegisterAccountResponse>

    suspend fun updatePhoto(photoUri: ByteArray): Result<PhotoRequest>
}