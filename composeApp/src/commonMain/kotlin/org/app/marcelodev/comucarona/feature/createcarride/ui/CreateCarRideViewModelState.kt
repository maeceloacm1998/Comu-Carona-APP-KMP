package org.app.marcelodev.comucarona.feature.createcarride.ui

import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps
import org.app.marcelodev.comucarona.feature.createcarride.data.models.LastCarRide

sealed interface CreateCarRideViewModelUiState {

    data class Steps(
        val steps: CreateCarRideSteps,
        val carModel: String,
        val carColor: String,
        val carPlate: String,
        val quantitySeats: Int,
        var waitingAddress: String,
        var waitingAddressList: List<String>,
        var destinationAddress: String,
        var destinationAddressList: List<String>,
        var waitingHour: String,
        var destinationHour: String,
        var enabledCarModelScreen: Boolean,
        var hasLastCarRide: Boolean,
        var lastCarRide: LastCarRide?,
        val isLoading: Boolean,
        val isError: Boolean,
        val isSuccess: Boolean,
    ) : CreateCarRideViewModelUiState
}

data class CreateCarRideViewModelState(
    val steps: CreateCarRideSteps = CreateCarRideSteps.CAR_MODEL,
    val carModel: String = "",
    val carColor: String = "",
    val carPlate: String = "",
    val quantitySeats: Int = 0,
    var waitingAddress: String = "",
    var waitingAddressList: List<String> = emptyList(),
    var destinationAddress: String = "",
    var destinationAddressList: List<String> = emptyList(),
    var waitingHour: String = "",
    var destinationHour: String = "",
    var enabledCarModelScreen: Boolean = false,
    var hasLastCarRide: Boolean = false,
    var lastCarRide: LastCarRide? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
) {

    fun toUiState(): CreateCarRideViewModelUiState = CreateCarRideViewModelUiState.Steps(
        steps = steps,
        carModel = carModel,
        carColor = carColor,
        carPlate = carPlate,
        quantitySeats = quantitySeats,
        waitingAddress = waitingAddress,
        waitingAddressList = waitingAddressList,
        destinationAddress = destinationAddress,
        destinationAddressList = destinationAddressList,
        waitingHour = waitingHour,
        destinationHour = destinationHour,
        enabledCarModelScreen = enabledCarModelScreen,
        hasLastCarRide = hasLastCarRide,
        lastCarRide = lastCarRide,
        isLoading = isLoading,
        isError = isError,
        isSuccess = isSuccess,
    )
}