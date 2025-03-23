package org.app.marcelodev.comucarona.feature.checkcode.data

import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeRequest
import org.app.marcelodev.comucarona.feature.checkcode.data.model.CheckCodeResponse

interface CheckCodeRepository {
    suspend fun checkCode(checkCodeRequest: CheckCodeRequest): Result<CheckCodeResponse>
}