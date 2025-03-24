package org.app.marcelodev.comucarona.feature.registeraccount.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterAccountRequest(
    val fullName: String,
    val birthDate: String,
    val phoneNumber: String,
    val photoUrl: String
)