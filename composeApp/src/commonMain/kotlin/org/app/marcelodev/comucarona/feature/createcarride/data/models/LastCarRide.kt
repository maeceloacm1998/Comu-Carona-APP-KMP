package org.app.marcelodev.comucarona.feature.createcarride.data.models

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.models.AvailableCarRide
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastCarRide(
    @SerialName("availableCarRide") val availableCarRide: AvailableCarRide,
    @SerialName("carRideInformation") val carRideInformation: CreateCarRideRequest
)