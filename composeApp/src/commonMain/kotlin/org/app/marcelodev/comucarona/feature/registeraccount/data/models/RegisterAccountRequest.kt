package org.app.marcelodev.comucarona.feature.registeraccount.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterAccountRequest(
    @SerialName("fullName") val fullName: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("photoUrl") val photoUrl: String?
)