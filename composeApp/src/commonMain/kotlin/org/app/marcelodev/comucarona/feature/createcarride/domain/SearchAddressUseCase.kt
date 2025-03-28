package org.app.marcelodev.comucarona.feature.createcarride.domain

import org.app.marcelodev.comucarona.feature.createcarride.data.CreateCarRideRepository

class SearchAddressUseCase(
    private val createCarRideRepository: CreateCarRideRepository
) {
    suspend operator fun invoke(address: String): Result<List<String>> {
        return createCarRideRepository.searchAddress(address)
    }
}