package org.app.marcelodev.comucarona.feature.carridedetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import org.app.marcelodev.comucarona.feature.carridedetails.data.models.BottomSheetCarRideUser
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnBack
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnCallPhone
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnCallWhatsApp
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnOpenBottomSheet
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnOpenShare
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnReservationRide
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.car_ride_details_address_title_empty
import comucarona.composeapp.generated.resources.car_ride_details_date_title_empty
import comucarona.composeapp.generated.resources.car_ride_details_hour_title_empty
import org.app.marcelodev.comucarona.components.bottomsheet.CCBottomSheet
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.button.CCOutlinedButton
import org.app.marcelodev.comucarona.components.carridecard.AddressBox
import org.app.marcelodev.comucarona.components.carridecard.UserSelectionBox
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.components.snackbar.CCSnackbar
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.theme.Primary
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.Success
import org.app.marcelodev.comucarona.theme.TextFieldLightColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarRideDetailsScreen(
    uiState: CarRideDetailsViewModelUiState.HasCarRideDetails,
    onEvent: (CarRideDetailsViewModelEventState) -> Unit,
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
                    modifier = Modifier.fillMaxWidth(),
                    text = uiState.carRideDetailsResponse?.description
                        ?: stringResource(Res.string.car_ride_details_description_title_empty),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = Bold,
                    color = TextFieldLightColor,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 20.dp)
            ) {
                HorizontalLine(Modifier.padding(bottom = 20.dp))

                CCButton(
                    Modifier.padding(bottom = 20.dp),
                    title = stringResource(Res.string.car_ride_details_reservation_button_title),
                    isLoading = uiState.isLoadingReservation,
                    isSuccess = uiState.isSuccessReservation,
                    isEnable = uiState.isEnableButton,
                    onButtonListener = { onEvent(OnReservationRide) }
                )
            }
        }

        // Contact bottom sheet
        CCBottomSheet(
            showSheet = uiState.showBottomSheet,
            onDismissRequest = {
                onEvent(CarRideDetailsViewModelEventState.OnDismissBottomSheet)
            },
            containerColor = White
        ) {
            if (uiState.carRideDetailsResponse != null) {
                UserDetailsBottomSheet(
                    data = uiState.carRideDetailsResponse.bottomSheetCarRideUser,
                    onClickPhone = {
                        onEvent(OnCallPhone)
                    },
                    onClickWhatsapp = {
                        onEvent(OnCallWhatsApp)
                    }
                )
            }
        }
    }
}

@Composable
fun UserDetailsBottomSheet(
    data: BottomSheetCarRideUser,
    onClickWhatsapp: () -> Unit = {},
    onClickPhone: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.car_ride_details_user_details_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = Bold,
            color = SoftBlack,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .background(Transparent)
                    .border(1.dp, TextFieldLightColor, shape = MaterialTheme.shapes.medium)
                    .padding(vertical = 20.dp, horizontal = 30.dp),
            ) {
                Text(
                    text = data.bottomSheetRiderPlate,
                    style = MaterialTheme.typography.titleSmall,
                    color = Primary,
                    fontWeight = Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        UserSelectionBox(
            riderPhotoUrl = data.bottomSheetRiderPhoto,
            riderUserName = data.bottomSheetRiderUsername,
            riderDescription = data.bottomSheetRiderDescription,
            showArrow = false
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CCOutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 7.dp),
                title = stringResource(Res.string.car_ride_details_user_details_wpp_title),
                titleColor = Success,
                borderColor = Success,
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_wpp),
                        contentDescription = stringResource(Res.string.car_ride_details_user_details_wpp_title),
                        modifier = Modifier.padding(end = 5.dp)
                    )
                },
                onButtonListener = {
                    onClickWhatsapp()
                }
            )
            CCOutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 7.dp),
                title = stringResource(Res.string.car_ride_details_user_details_phone_title),
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_phone),
                        contentDescription = stringResource(Res.string.car_ride_details_user_details_phone_title),
                        modifier = Modifier.padding(end = 5.dp)
                    )
                },
                onButtonListener = {
                    onClickPhone()
                }
            )
        }
    }
}

@Preview
@Composable
fun UserDetailsBottomSheetPreview() {
    UserDetailsBottomSheet(
        data = BottomSheetCarRideUser(
            bottomSheetRiderPlate = "ABC-1234",
            bottomSheetRiderUsername = "John Doe",
            bottomSheetRiderDescription = "Lorem ipsum dolor sit amet",
            bottomSheetRiderPhoto = "",
            bottomSheetCarRiderDescription = "Lorem ipsum dolor sit amet",
            bottomSheetRiderPhoneNumber = "123456789"
        ),
        onClickWhatsapp = {},
        onClickPhone = {}
    )
}

@Preview
@Composable
fun CarRideDetailsScreenPreview() {
    CarRideDetailsScreen(
        uiState = CarRideDetailsViewModelUiState.HasCarRideDetails(
            carRideDetailsResponse = null,
            snackbarType = SnackbarCustomType.SUCCESS,
            showSnackBar = false,
            showBottomSheet = false,
            isSuccessReservation = false,
            isLoading = false,
            isError = false,
            isLoadingReservation = false,
            isEnableButton = true
        ),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}