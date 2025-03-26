package org.app.marcelodev.comucarona.feature.home.steps.profile.data

import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest


interface ProfileRepository {
    suspend fun getProfile(): Result<RegisterAccountRequest>
    suspend fun updateProfile(newUserInformation: RegisterAccountRequest): Result<RegisterAccountRequest>
}