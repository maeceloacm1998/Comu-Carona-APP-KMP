package org.app.marcelodev.comucarona.feature.checkcode.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckCodeRequest (
    val username: String,
    val code: String
)