package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel


interface MyRideInProgressRepository {
    suspend fun getMyRideInProgress(status: String?): Result<List<RideInProgressModel>>
}