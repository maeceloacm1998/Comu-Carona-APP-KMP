package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.domain.DeleteCarRideUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.CallPhoneUseCase
import org.app.marcelodev.comucarona.commons.usecase.CallWhatsappUseCase
import org.app.marcelodev.comucarona.commons.usecase.CallWhatsappUseCase.Companion.DEFAULT_MESSAGE_CAR_RIDE
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.usecase.ShareLinkUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType.ERROR
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType.WARNING
import org.app.marcelodev.comucarona.feature.carridedetails.data.models.CarRideDetails
import org.app.marcelodev.comucarona.feature.carridedetails.domain.GetCarRideDetailsUseCase
import org.app.marcelodev.comucarona.feature.home.HomeRoutePatern
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.domain.FinishCarRideUseCase
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsViewModelEventState.*
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class MyRideInProgressDetailsViewModel(
    private val riderId: String,
    private val navigator: Navigator,
    private val snackbarHostState: SnackbarHostState,
    private val getCarRideDetailsUseCase: GetCarRideDetailsUseCase,
    private val deleteCarRideUseCase: DeleteCarRideUseCase,
    private val finishCarRideUseCase: FinishCarRideUseCase,
    private val callWhatsappUseCase: CallWhatsappUseCase,
    private val callPhoneUseCase: CallPhoneUseCase,
    private val shareLinkUseCase: ShareLinkUseCase,
    private val logoutUseCase: LogoutUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val fullSeatsMessage = "Todas as vagas dessa carona ja foram preechidas! \uD83D\uDE25"
    private val viewModelState = MutableStateFlow(MyRideInProgressDetailsViewModelState())

    val uiState = viewModelState
        .map(MyRideInProgressDetailsViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        onFetchReservationRide(riderId)
    }

    fun onEvent(event: MyRideInProgressDetailsViewModelEventState) {
        when (event) {
            is OnCancelMyRide -> onFetchCancelMyRide()
            is OnDismissBottomSheet -> onDismissBottomSheet()
            is OnOpenBottomSheet -> onOpenBottomSheet()
            is OnOpenCancelBottomSheet -> onOpenCancelBottomSheet()
            is OnCallWhatsApp -> onCallWhatsApp()
            is OnCallPhone -> onCallPhone()
            is OnGoToHome -> onGoToHome()
            is OnRetry -> onFetchReservationRide(riderId)
            is OnOpenShare -> onOpenShareLink()
            is OnFinishCarRide -> onFinishCarRide()
            is OnBack -> {
                NavigationUtils.removeLastScreen(navigator)
            }
        }
    }

    private fun onFetchReservationRide(id: String) {
        onUpdateError(false)
        onUpdateLoading(true)

        viewModelScope.launch {
            getCarRideDetailsUseCase.invoke(id)
                .onSuccess { result ->
                    onUpdateLoading(false)
                    onUpdateCarRideDetails(result)

                    if (result.isFullSeats) {
                        onUpdateIsEnableButton(false)
                        onUpdateShowSnackBar(
                            showSnackBar = true,
                            snackBarMessage = fullSeatsMessage,
                            snackbarType = WARNING
                        )
                    }
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateLoading(false)
                            onUpdateError(true)
                        }
                    )
                }
        }
    }

    private fun onFetchCancelMyRide() {
        onUpdateLoadingReservation(true)

        viewModelScope.launch {
            deleteCarRideUseCase.invoke(riderId)
                .onSuccess {
                    onUpdateLoadingReservation(false)
                    onUpdateSuccessReservation(true)

                    onUpdateCompleteTitleAndDescription(
                        title = "Sua Carona foi cancelada!",
                        description = "Que pena que você cancelou a carona, mas continue sempre que puder compartilhando 😁🚗"
                    )
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateLoadingReservation(false)
                            onUpdateShowSnackBar(
                                showSnackBar = true,
                                snackBarMessage = throwable.message ?: "",
                                snackbarType = ERROR
                            )
                        }
                    )
                }
        }
    }

    fun onFinishCarRide() {
        onUpdateLoadingReservation(true)

        viewModelScope.launch {
            finishCarRideUseCase.invoke(riderId)
                .onSuccess {
                    onUpdateLoadingReservation(false)
                    onUpdateSuccessReservation(true)

                    onUpdateCompleteTitleAndDescription(
                        title = "Você terminou sua Carona!",
                        description = "Muito obrigado por ajudar nossa comunidade, continue compartilhando sempre que puder 😁🚗"
                    )
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateLoadingReservation(false)
                            onUpdateShowSnackBar(
                                showSnackBar = true,
                                snackBarMessage = throwable.message ?: "",
                                snackbarType = ERROR
                            )
                        }
                    )
                }
        }
    }

    private fun onUpdateCarRideDetails(carRideDetails: CarRideDetails) {
        viewModelState.update { it.copy(carRideDetailsResponse = carRideDetails) }
    }

    private fun onUpdateShowSnackBar(
        showSnackBar: Boolean,
        snackBarMessage: String,
        snackbarType: SnackbarCustomType
    ) {
        viewModelState.update {
            it.copy(
                showSnackBar = showSnackBar,
                snackbarType = snackbarType
            )
        }

        viewModelScope.launch {
            snackbarHostState.showSnackbar(snackBarMessage)
        }
    }

    private fun onDismissBottomSheet() {
        viewModelState.update { it.copy(showBottomSheet = false, showCancelBottomSheet = false) }
    }

    private fun onOpenBottomSheet() {
        viewModelState.update { it.copy(showBottomSheet = true) }
    }

    private fun onOpenCancelBottomSheet() {
        viewModelState.update { it.copy(showCancelBottomSheet = true) }
    }

    private fun onCallPhone() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        callPhoneUseCase(
            phoneNumber = data.bottomSheetCarRideUser.bottomSheetRiderPhoneNumber,
            onErrorAction = { errorMessage ->
                onUpdateShowSnackBar(
                    showSnackBar = true,
                    snackBarMessage = errorMessage,
                    snackbarType = ERROR
                )
            }
        )
    }

    private fun onCallWhatsApp() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        callWhatsappUseCase(
            phoneNumber = data.bottomSheetCarRideUser.bottomSheetRiderPhoneNumber,
            message = DEFAULT_MESSAGE_CAR_RIDE
        )
    }

    private fun onGoToHome() {
        NavigationUtils.replaceAllScreens(
            navigator = navigator,
            screen = HomeRoutePatern()
        )
    }

    private fun onOpenShareLink() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        shareLinkUseCase(
            link = data.shareDeeplink,
            onErrorAction = {
                onUpdateShowSnackBar(
                    showSnackBar = true,
                    snackBarMessage = it,
                    snackbarType = ERROR
                )
            })
    }

    private fun onUpdateSuccessReservation(isSuccess: Boolean) {
        viewModelState.update { it.copy(isSuccessReservation = isSuccess) }
    }

    private fun onUpdateLoadingReservation(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoadingReservation = isLoading) }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateError(error: Boolean) {
        viewModelState.update { it.copy(isError = error) }
    }

    private fun onUpdateIsEnableButton(isEnableButton: Boolean) {
        viewModelState.update { it.copy(isEnableButton = isEnableButton) }
    }

    private fun onUpdateCompleteTitleAndDescription(title: String, description: String) {
        viewModelState.update { it.copy(completeDescription = description, completeTitle = title) }
    }
}