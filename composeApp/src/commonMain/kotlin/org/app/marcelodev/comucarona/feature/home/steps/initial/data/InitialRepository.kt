package org.app.marcelodev.comucarona.feature.home.steps.initial.data

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.models.AvailableCarRide
import org.app.marcelodev.comucarona.service.ktor.UserInformation

interface InitialRepository {
    suspend fun getAvailableCarRides(): Result<List<AvailableCarRide>>
    suspend fun getUserInformation(): Result<UserInformation>
}