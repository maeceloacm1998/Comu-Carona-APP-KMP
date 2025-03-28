package org.app.marcelodev.comucarona.feature.createcarride.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.*
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_DESTINATION_HOUR
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.FINISH
import org.app.marcelodev.comucarona.feature.createcarride.domain.CreateCarRideUseCase
import org.app.marcelodev.comucarona.feature.createcarride.domain.GetLastCarRideUseCase
import org.app.marcelodev.comucarona.feature.createcarride.domain.SearchAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.koin.core.component.KoinComponent

class CreateCarRideViewModel(
    private val navigator: Navigator,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val createCarRideUseCase: CreateCarRideUseCase,
    private val getLastCarRideUseCase: GetLastCarRideUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(CreateCarRideViewModelState())
    private val stepsOrder: List<CreateCarRideSteps> =
        CreateCarRideSteps.entries.toTypedArray().toList()

    val uiState = viewModelState
        .map(CreateCarRideViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        fetchLastCarRide()
    }

    fun onEvent(event: CreateCarRideViewModelEventState) {
        when (event) {
            is OnNextStep -> onNextStep(event.step)
            is OnRemoveNewStep -> onRemoveNewStep(event.step)
            is OnCarModel -> onUpdateCarModel(event.carModel)
            is OnCarColor -> onUpdateCarColor(event.carColor)
            is OnCarPlate -> onUpdateCarPlate(event.carPlate)
            is OnQuantitySeats -> onUpdateQuantitySeats(event.quantitySeats)
            is OnWaitingAddress -> onUpdateWaitingAddress(event.waitingAddress)
            is OnDestinationAddress -> onUpdateDestinationAddress(event.destinationAddress)
            is OnWaitingHour -> onUpdateWaitingHour(event.waitingHour)
            is OnDestinationHour -> onUpdateDestinationHour(event.destinationHour)
            is OnClearAddressList -> onClearAddressList()
            is OnCreateCarRide -> onCreateCarRide()
            is OnDismissBottomSheet -> onDismissBottomSheet()
            is OnConfirmLastCarRideUsage -> onConfirmLastCarRideUsage()
            is OnDeclineLastCarRideUsage -> onDeclineLastCarRideUsage()
            is OnGoToHome -> onGoToHome()
        }
    }

    private fun fetchLastCarRide() {
        viewModelScope.launch {
            getLastCarRideUseCase()
                .onSuccess { lastCarRide ->
                    viewModelState.update {
                        it.copy(
                            hasLastCarRide = true,
                            lastCarRide = lastCarRide
                        )
                    }
                }
        }
    }

    private fun onNextStep(step: CreateCarRideSteps) {
        if (step == FINISH) {
            onCreateCarRide()
        } else {
            val index = stepsOrder.indexOf(step)
            onUpdateStep(stepsOrder[index])
        }
    }

    private fun onRemoveNewStep(step: CreateCarRideSteps) {
        val index = stepsOrder.indexOf(step)

        onUpdateStep(stepsOrder[index])
    }

    private fun onSearchAddress(
        address: String,
        onSuccess: (List<String>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            searchAddressUseCase(address).onSuccess { addresses ->
                onSuccess(addresses)
            }.onFailure { throwable ->
                onError(throwable)
            }
        }
    }

    private fun onCreateCarRide() {
        val state = viewModelState.value
        viewModelScope.launch {
            createCarRideUseCase(
                carModel = state.carModel,
                carPlate = state.carPlate,
                carColor = state.carColor,
                quantitySeats = state.quantitySeats,
                waitingAddress = state.waitingAddress,
                destinationAddress = state.destinationAddress,
                waitingHour = state.waitingHour,
                destinationHour = state.destinationHour
            ).onSuccess {
                onDismissBottomSheet()
                onRemoveNewStep(FINISH)
            }.onFailure {
                onRemoveNewStep(CAR_DESTINATION_HOUR)
            }
        }
    }

    private fun onGoToHome() {
        NavigationUtils.removeLastScreen(navigator)
    }

    private fun onUpdateEnabledCarModelScreen() {
        val carModel = viewModelState.value.carModel
        val carPlate = viewModelState.value.carPlate
        val carColor = viewModelState.value.carColor

        viewModelState.update {
            it.copy(enabledCarModelScreen = carModel.isNotEmpty() && carPlate.isNotEmpty() && carColor.isNotEmpty())
        }
    }

    private fun onUpdateStep(step: CreateCarRideSteps) {
        viewModelState.update { it.copy(steps = step) }
    }

    private fun onUpdateCarModel(carModel: String) {
        onUpdateEnabledCarModelScreen()
        viewModelState.update {
            it.copy(carModel = carModel)
        }
    }

    private fun onUpdateCarColor(carColor: String) {
        onUpdateEnabledCarModelScreen()
        viewModelState.update {
            it.copy(carColor = carColor)
        }
    }

    private fun onUpdateCarPlate(carPlate: String) {
        onUpdateEnabledCarModelScreen()
        viewModelState.update {
            it.copy(carPlate = carPlate)
        }
    }

    private fun onUpdateQuantitySeats(quantitySeats: Int) {
        viewModelState.update {
            it.copy(quantitySeats = quantitySeats)
        }
    }

    private fun onUpdateWaitingAddress(waitingAddress: String) {
        onSearchAddress(
            address = waitingAddress,
            onSuccess = { addresses ->
                viewModelState.update {
                    it.copy(waitingAddressList = addresses)
                }
            }, onError = {
                viewModelState.update {
                    it.copy(waitingAddressList = emptyList())
                }
            }
        )
        viewModelState.update {
            it.copy(waitingAddress = waitingAddress)
        }
    }

    private fun onUpdateDestinationAddress(destinationAddress: String) {
        onSearchAddress(
            address = destinationAddress,
            onSuccess = { addresses ->
                viewModelState.update {
                    it.copy(waitingAddressList = addresses)
                }
            }, onError = {
                viewModelState.update {
                    it.copy(waitingAddressList = emptyList())
                }
            }
        )

        viewModelState.update {
            it.copy(destinationAddress = destinationAddress)
        }
    }


    private fun onConfirmLastCarRideUsage() {
        val lastCarRideInformation =
            checkNotNull(viewModelState.value.lastCarRide?.carRideInformation)
        viewModelState.update {
            it.copy(
                carModel = lastCarRideInformation.carModel,
                carColor = lastCarRideInformation.carColor,
                carPlate = lastCarRideInformation.carPlate,
                quantitySeats = lastCarRideInformation.quantitySeats,
                waitingAddress = lastCarRideInformation.waitingAddress,
                destinationAddress = lastCarRideInformation.destinationAddress,
                waitingHour = lastCarRideInformation.waitingHour,
                destinationHour = lastCarRideInformation.destinationHour
            )
        }

        onCreateCarRide()
    }

    private fun onDeclineLastCarRideUsage() {
        onDismissBottomSheet()
    }

    private fun onClearAddressList() {
        viewModelState.update {
            it.copy(waitingAddressList = emptyList(), destinationAddressList = emptyList())
        }
    }

    private fun onUpdateWaitingHour(waitingHour: String) {
        viewModelState.update {
            it.copy(waitingHour = waitingHour)
        }
    }

    private fun onUpdateDestinationHour(destinationHour: String) {
        viewModelState.update {
            it.copy(destinationHour = destinationHour)
        }
    }

    private fun onDismissBottomSheet() {
        viewModelState.update {
            it.copy(hasLastCarRide = false)
        }
    }
}