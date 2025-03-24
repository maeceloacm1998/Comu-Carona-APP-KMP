package org.app.marcelodev.comucarona.feature.registeraccount.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterAccountResponse(
    @SerialName("username") val username: String,
    @SerialName("photoUrl") val photoUrl: String,
    @SerialName("authenticated") val authenticated: Boolean,
    @SerialName("created") val created: String,
    @SerialName("expiration") val expiration: String,
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null
)