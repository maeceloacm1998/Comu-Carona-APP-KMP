package org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnBack
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnCallPhone
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnCallWhatsApp
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnCancelMyRide
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnDismissBottomSheet
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnOpenBottomSheet
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnOpenCancelBottomSheet
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModelEventState.OnOpenShare
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.car_ride_details_address_title_empty
import comucarona.composeapp.generated.resources.car_ride_details_date_title_empty
import comucarona.composeapp.generated.resources.car_ride_details_hour_title_empty
import org.app.marcelodev.comucarona.components.bottomsheet.CCBottomSheet
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.carridecard.AddressBox
import org.app.marcelodev.comucarona.components.carridecard.UserSelectionBox
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.components.snackbar.CCSnackbar
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType.ERROR
import org.app.marcelodev.comucarona.feature.carridedetails.ui.UserDetailsBottomSheet
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.CancelBottomSheet
import org.app.marcelodev.comucarona.theme.Error
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldLightColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideInProgressDetailsScreen(
    uiState: RideInProgressDetailsViewModelUiState.HasCarRideDetails,
    onEvent: (RideInProgressDetailsViewModelEventState) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val isShowShareButton: Boolean = !uiState.carRideDetailsResponse?.shareDeeplink.isNullOrBlank()

    Scaffold(
        snackbarHost = {
            CCSnackbar(
                snackbarHostState = snackbarHostState,
                snackbarType = uiState.snackbarType
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .verticalScroll(rememberScrollState())
                    .background(White)
                    .padding(20.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CCButtonBack(onClick = {
                        onEvent(OnBack)
                    })

                    if(isShowShareButton) {
                        IconButton(
                            onClick = { onEvent(OnOpenShare) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = SoftBlack
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = uiState.carRideDetailsResponse?.dateTitle
                        ?: stringResource(Res.string.car_ride_details_date_title_empty),
                    style = MaterialTheme.typography.titleLarge,
                    color = SoftBlack,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(30.dp))

                AddressBox(
                    waitingHour = uiState.carRideDetailsResponse?.waitingHour
                        ?: stringResource(Res.string.car_ride_details_hour_title_empty),
                    destinationHour = uiState.carRideDetailsResponse?.destinationHour
                        ?: stringResource(Res.string.car_ride_details_hour_title_empty),
                    waitingAddress = uiState.carRideDetailsResponse?.waitingAddress
                        ?: stringResource(Res.string.car_ride_details_address_title_empty),
                    destinationAddress = uiState.carRideDetailsResponse?.destinationAddress
                        ?: stringResource(Res.string.car_ride_details_address_title_empty)
                )

                HorizontalLine(modifier = Modifier.padding(vertical = 25.dp))

                UserSelectionBox(
                    modifier = Modifier.clickable {
                        onEvent(OnOpenBottomSheet)
                    },
                    riderPhotoUrl = uiState.carRideDetailsResponse?.riderPhoto ?: "",
                    riderUserName = uiState.carRideDetailsResponse?.riderUsername
                        ?: stringResource(Res.string.car_ride_details_username_title_empty),
                    riderDescription = uiState.carRideDetailsResponse?.riderDescription
                        ?: stringResource(
                            Res.string.car_ride_details_description_title_empty
                        ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(Res.string.ride_in_progress_details_reservations_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = SoftBlack,
                    fontWeight = Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
                )

                if(uiState.carRideDetailsResponse?.reservations.isNullOrEmpty()) {
                    Text(
                        text = stringResource(Res.string.ride_in_progress_details_reservations_empty_title),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextFieldLightColor,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(White)
                    ) {
                        items(checkNotNull(uiState.carRideDetailsResponse?.reservations)) { reservation ->
                            UserSelectionBox(
                                riderPhotoUrl = reservation.photoUrl,
                                riderUserName = reservation.username,
                                riderDescription = "${reservation.birthDate} |\n${reservation.phoneNumber}",
                                showArrow = false,
                                showCar = false
                            )

                            HorizontalLine(modifier = Modifier.padding(vertical = 15.dp))
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 20.dp)
            ) {
                HorizontalLine(Modifier.padding(bottom = 10.dp))

                CCButton(
                    Modifier.padding(bottom = 20.dp),
                    title = stringResource(Res.string.ride_in_progress_details_cancel_button_title),
                    isLoading = uiState.isLoadingReservation,
                    isSuccess = uiState.isSuccessReservation,
                    isEnable = uiState.isEnableButton,
                    titleColor = Error,
                    containerColor = Transparent,
                    onButtonListener = { onEvent(OnOpenCancelBottomSheet) }
                )
            }
        }

        // Contact bottom sheet
        CCBottomSheet(
            showSheet = uiState.showBottomSheet,
            onDismissRequest = {
                onEvent(OnDismissBottomSheet)
            },
            containerColor = White,
        ) {
            if (uiState.carRideDetailsResponse != null) {
                UserDetailsBottomSheet(
                    data = uiState.carRideDetailsResponse.bottomSheetCarRideUser,
                    onClickWhatsapp = {
                        onEvent(OnCallWhatsApp)
                    },
                    onClickPhone = {
                        onEvent(OnCallPhone)
                    }
                )
            }
        }

        // Cancel Bottom Sheet
        CCBottomSheet(
            showSheet = uiState.showCancelBottomSheet,
            onDismissRequest = {
                onEvent(OnDismissBottomSheet)
            },
            containerColor = White,
        ) {
            CancelBottomSheet(
                title = stringResource(Res.string.ride_in_progress_details_cancel_bottom_sheet_title),
                description = stringResource(Res.string.ride_in_progress_details_cancel_bottom_sheet_message),
                goBackButtonTitle = stringResource(Res.string.ride_in_progress_details_cancel_bottom_sheet_cancel_button_title),
                confirmButtonTitle = stringResource(Res.string.ride_in_progress_details_cancel_bottom_sheet_confirm_button_title),
                onDismissBottomSheet = { onEvent(OnDismissBottomSheet) },
                onConfirmCancel = { onEvent(OnCancelMyRide) }
            )
        }
    }
}

@Preview
@Composable
fun RideInProgressDetailsScreenPreview() {
    RideInProgressDetailsScreen(
        uiState = RideInProgressDetailsViewModelUiState.HasCarRideDetails(
            carRideDetailsResponse = null,
            isLoadingReservation = false,
            isSuccessReservation = false,
            isEnableButton = true,
            showBottomSheet = false,
            showCancelBottomSheet = false,
            isLoading = false,
            isError = false,
            showSnackBar = false,
            snackbarType = ERROR
        ),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}