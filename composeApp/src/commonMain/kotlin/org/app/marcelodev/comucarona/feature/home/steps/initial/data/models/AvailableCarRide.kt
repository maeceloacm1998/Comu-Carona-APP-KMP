package org.app.marcelodev.comucarona.feature.home.steps.initial.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableCarRide(
    @SerialName("id") val id: String,
    @SerialName("waitingAddress") val waitingAddress: String,
    @SerialName("destinationAddress") val destinationAddress: String,
    @SerialName("waitingHour") val waitingHour: String,
    @SerialName("destinationHour") val destinationHour: String,
    @SerialName("riderPhotoUrl") val riderPhotoUrl: String,
    @SerialName("riderUserName") val riderUserName: String,
    @SerialName("riderDescription") val riderDescription: String
)