package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel

interface RideInProgressRepository {
    suspend fun getRideInProgress(status: String?): Result<List<RideInProgressModel>>
}