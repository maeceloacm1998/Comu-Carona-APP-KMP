package org.app.marcelodev.comucarona.feature.createcarride.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCarRideRequest(
    @SerialName("carModel") val carModel: String,
    @SerialName("carColor") val carColor: String,
    @SerialName("carPlate") val carPlate: String,
    @SerialName("quantitySeats") val quantitySeats: Int,
    @SerialName("waitingAddress") val waitingAddress: String,
    @SerialName("destinationAddress") val destinationAddress: String,
    @SerialName("waitingHour") val waitingHour: String,
    @SerialName("destinationHour") val destinationHour: String,
    @SerialName("status") val status: String,
    @SerialName("isTwoPassengersBehind") val isTwoPassengersBehind: Boolean,
    @SerialName("twoPassengersBehind") val twoPassengersBehind: Boolean
)