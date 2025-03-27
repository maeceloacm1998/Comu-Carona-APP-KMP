package org.app.marcelodev.comucarona.feature.carridedetails.data

import org.app.marcelodev.comucarona.feature.carridedetails.data.models.CarRideDetails

interface CarRideDetailsRepository {
    suspend fun getCarRideDetails(id: String): Result<CarRideDetails>
    suspend fun reservationRide(riderId: String): Result<Unit>
}