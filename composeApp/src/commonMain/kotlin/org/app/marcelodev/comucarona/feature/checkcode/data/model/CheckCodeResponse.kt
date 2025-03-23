package org.app.marcelodev.comucarona.feature.checkcode.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckCodeResponse(
    @SerialName("username") val username: String,
    @SerialName("photoUrl") val photoUrl: String,
    @SerialName("authenticated") val authenticated: Boolean,
    @SerialName("created") val created: String,
    @SerialName("expiration") val expiration: String,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
)
