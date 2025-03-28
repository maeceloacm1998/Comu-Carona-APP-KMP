package org.app.marcelodev.comucarona.feature.createcarride.data

import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideRequest
import org.app.marcelodev.comucarona.feature.createcarride.data.models.LastCarRide

interface CreateCarRideRepository {
    suspend fun searchAddress(address: String): Result<List<String>>
    suspend fun createCarRide(data: CreateCarRideRequest): Result<Unit>
    suspend fun getLastCarRide(): Result<LastCarRide>
}