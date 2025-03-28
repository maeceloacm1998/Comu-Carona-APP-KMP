package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RideInProgressModel(
    @SerialName("uuid") val uuid: String,
    @SerialName("waitingAddress") val waitingAddress: String,
    @SerialName("destinationAddress") val destinationAddress: String,
    @SerialName("waitingHour") val waitingHour: String,
    @SerialName("destinationHour") val destinationHour: String,
    @SerialName("states") val states: List<String>,
    @SerialName("riderInformation") val riderInformation: RideInProgressInformationModel,
    @SerialName("isMyCarRide") val isMyCarRide: Boolean
)

@Serializable
data class RideInProgressInformationModel(
    @SerialName("username") val username: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("photoUrl") val photoUrl: String
)