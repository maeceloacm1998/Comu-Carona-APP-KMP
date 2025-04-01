package org.app.marcelodev.comucarona.feature.createcarride.domain

import org.app.marcelodev.comucarona.feature.createcarride.data.CreateCarRideRepository
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideRequest

class CreateCarRideUseCase(
    private val createCarRideRepository: CreateCarRideRepository
) {
    suspend operator fun invoke(
        carModel: String,
        carPlate: String,
        carColor: String,
        quantitySeats: Int,
        waitingAddress: String,
        destinationAddress: String,
        waitingHour: String,
        destinationHour: String,
    ): Result<Unit> {
        val data = CreateCarRideRequest(
            carModel = carModel,
            carPlate = carPlate,
            carColor = carColor,
            quantitySeats = quantitySeats,
            waitingAddress = waitingAddress,
            destinationAddress = destinationAddress,
            waitingHour = waitingHour,
            destinationHour = destinationHour,
            status = "",
            isTwoPassengersBehind = false
        )

        return createCarRideRepository.createCarRide(data)
    }
}