package org.app.marcelodev.comucarona.feature.createcarride.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.*
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideRequest
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_QUANTITY_SEATS
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_WAITING_ADDRESS
import org.app.marcelodev.comucarona.feature.createcarride.data.models.LastCarRide
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.create_car_ride_car_model_hint
import comucarona.composeapp.generated.resources.create_car_ride_car_model_message
import comucarona.composeapp.generated.resources.create_car_ride_car_model_title
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.app.marcelodev.comucarona.commons.utils.StringUtils.CAR_PLATE_LENGTH
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.carridecard.AddressBox
import org.app.marcelodev.comucarona.components.carridecard.UserSelectionBox
import org.app.marcelodev.comucarona.components.contentsuccess.CCSuccessContent
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.components.textfield.CCTextField
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.models.AvailableCarRide
import org.app.marcelodev.comucarona.theme.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StageOfCarModelScreen(
    uiState: CreateCarRideViewModelUiState.Steps,
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    val carModelFocusRequester = remember { FocusRequester() }
    val carColorFocusRequester = remember { FocusRequester() }
    val carPlateFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        carModelFocusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            event(OnGoToHome)
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.create_car_ride_car_model_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.create_car_ride_car_model_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(carModelFocusRequester),
            placeholder = stringResource(Res.string.create_car_ride_car_model_hint),
            value = uiState.carModel,
            onValueChange = { text ->
                event(OnCarModel(text))
            },
            keyboardType = KeyboardType.Text,
            isErrorMessage = false,
            onImeAction = {
                carPlateFocusRequester.requestFocus()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(carPlateFocusRequester),
            placeholder = stringResource(Res.string.create_car_ride_car_model_plate_hint),
            value = uiState.carPlate,
            onValueChange = { text ->
                event(OnCarPlate(text))
            },
            maxLength = CAR_PLATE_LENGTH,
            keyboardType = KeyboardType.Text,
            isErrorMessage = false,
            onImeAction = {
                carColorFocusRequester.requestFocus()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(carColorFocusRequester),
            placeholder = stringResource(Res.string.create_car_ride_car_model_color_hint),
            value = uiState.carColor,
            onValueChange = { text ->
                event(OnCarColor(text))
            },
            keyboardType = KeyboardType.Text,
            isErrorMessage = false,
            onImeAction = {
                event(OnNextStep(CAR_QUANTITY_SEATS))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_birth_date_button_title),
            isEnable = uiState.enabledCarModelScreen,
            onButtonListener = {
                event(OnNextStep(CAR_QUANTITY_SEATS))
            }
        )
    }
}

@Composable
fun StageOfQuantitySeatsScreen(
    uiState: CreateCarRideViewModelUiState.Steps,
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    val minSeats = 1
    val maxSeats = 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            event(OnRemoveNewStep(CreateCarRideSteps.CAR_MODEL))
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.create_car_ride_quantity_seats_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .border(1.dp, Primary, CircleShape)
                    .size(30.dp),
                onClick = {
                    if (uiState.quantitySeats >= minSeats) {
                        event(OnQuantitySeats(uiState.quantitySeats - 1))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Remove seats",
                    tint = Primary
                )
            }

            Text(
                text = uiState.quantitySeats.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 60.sp,
                color = Primary
            )

            IconButton(
                modifier = Modifier
                    .border(1.dp, Primary, CircleShape)
                    .size(30.dp),
                onClick = {
                    if (uiState.quantitySeats < maxSeats) {
                        event(OnQuantitySeats(uiState.quantitySeats + 1))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add seats",
                    tint = Primary
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_birth_date_button_title),
            isEnable = uiState.quantitySeats > 0,
            onButtonListener = {
                event(OnNextStep(CAR_WAITING_ADDRESS))
            }
        )
    }
}

@Composable
fun StageOfWaitingAddressScreen(
    uiState: CreateCarRideViewModelUiState.Steps,
    title: String,
    textFieldValue: String = "",
    validationNextStep: Boolean = false,
    onValueChange: (String) -> Unit = {},
    onNextAction: () -> Unit = {},
    onBackAction: () -> Unit = {},
    onItemClicked: (address: String) -> Unit = {}
) {
    val focusRequesters = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequesters.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            onBackAction()
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesters),
            placeholder = stringResource(Res.string.create_car_ride_waiting_address_placeholder),
            value = textFieldValue,
            onDebouncedChange = { text ->
                onValueChange(text)
            },
            keyboardType = KeyboardType.Text,
            isErrorMessage = false,
            onImeAction = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            LazyColumn {
                if (uiState.waitingAddressList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum endereço encontrado.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextFieldColor
                        )
                    }
                } else {
                    items(uiState.waitingAddressList.size) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .clickable {
                                    onItemClicked(uiState.waitingAddressList[index])
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .size(20.dp),
                                    painter = painterResource(Res.drawable.ic_timer),
                                    contentDescription = "clock",
                                )
                                Text(
                                    text = uiState.waitingAddressList[index],
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextFieldColor
                                )
                            }

                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Add address",
                                tint = TextFieldColor
                            )
                        }
                        HorizontalLine(modifier = Modifier.padding(vertical = 10.dp))
                    }
                }
            }
        }

        HorizontalLine(modifier = Modifier.padding(vertical = 20.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_birth_date_button_title),
            isEnable = validationNextStep,
            onButtonListener = {
                onNextAction()
            }
        )
    }
}

@Composable
fun StageOfWaitingHourScreen(
    title: String,
    onValueChange: (String) -> Unit = {},
    onNextAction: () -> Unit = {},
    onBackAction: () -> Unit = {},
) {
    val firstTextFieldFocus = FocusRequester()
    val secondTextFieldFocus = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    var firstTextField by remember { mutableStateOf("") }
    var secondTextField by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        firstTextFieldFocus.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            onBackAction()
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CCTextField(
                modifier = Modifier
                    .weight(0.5f)
                    .height(150.dp)
                    .focusRequester(firstTextFieldFocus),
                placeholder = "00",
                placeholderSize = 60,
                textAlign = Center,
                maxLength = 2,
                value = firstTextField,
                onValueChange = { text ->
                    if (text.length == 2) {
                        secondTextFieldFocus.requestFocus()
                    }
                    firstTextField = text
                },
                keyboardType = KeyboardType.Number,
                isErrorMessage = false,
                onImeAction = {}
            )

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = ":",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 60.sp,
                color = TextFieldColor
            )

            CCTextField(
                modifier = Modifier
                    .height(150.dp)
                    .weight(0.5f)
                    .focusRequester(secondTextFieldFocus),
                placeholder = "00",
                placeholderSize = 60,
                textAlign = Center,
                maxLength = 2,
                value = secondTextField,
                onValueChange = { text ->
                    if (text.isEmpty()) {
                        firstTextFieldFocus.requestFocus()
                    }
                    secondTextField = text
                },
                keyboardType = KeyboardType.Number,
                isErrorMessage = false,
                onImeAction = {
                    onValueChange("${firstTextField}:${secondTextField}")
                    onNextAction()
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_birth_date_button_title),
            isEnable = (firstTextField + secondTextField).length == 4,
            onButtonListener = {
                onValueChange("${firstTextField}:${secondTextField}")
                onNextAction()
            }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StateOfWaitingCreateRideScreen(
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    val loadingLottieAnimation by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/search_car_ride.json").decodeToString()
        )
    }

    LaunchedEffect(Unit) {
        event(OnCreateCarRide)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberLottiePainter(
                composition = loadingLottieAnimation,
                iterations = Compottie.IterateForever
            ),
            modifier = Modifier
                .size(170.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "Lottie animation"
        )

        Text(
            text = stringResource(Res.string.create_car_ride_waiting_title),
            style = MaterialTheme.typography.titleSmall,
            color = TextColor,
            fontWeight = SemiBold,
            textAlign = Center,
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 40.dp,
            ),
        )
    }
}

@Composable
fun StateOfFinishCreateRideScreen(
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    CCSuccessContent(
        title = stringResource(Res.string.create_car_ride_success_title),
        description = stringResource(Res.string.create_car_ride_success_message),
        buttonText = stringResource(Res.string.create_car_ride_success_button_title),
        onClick = {
            event(OnGoToHome)
        }
    )
}

@Composable
fun LastCarRideBottomSheet(
    lastCarRide: LastCarRide,
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.create_car_ride_last_car_ride_title),
            style = MaterialTheme.typography.titleMedium,
            color = SoftBlack,
            textAlign = Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        UserSelectionBox(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            riderPhotoUrl = lastCarRide.availableCarRide.riderPhotoUrl,
            riderUserName = lastCarRide.availableCarRide.riderUserName,
            riderDescription = lastCarRide.availableCarRide.riderDescription,
            showArrow = false
        )

        Spacer(modifier = Modifier.height(10.dp))

        AddressBox(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp),
            waitingHour = lastCarRide.availableCarRide.waitingHour,
            destinationHour = lastCarRide.availableCarRide.destinationHour,
            waitingAddress = lastCarRide.availableCarRide.waitingAddress,
            destinationAddress = lastCarRide.availableCarRide.destinationAddress
        )

        Spacer(modifier = Modifier.height(20.dp))

        CCButton(
            title = stringResource(Res.string.create_car_ride_last_car_ride_confirm_button_title),
            onButtonListener = {
                event(OnConfirmLastCarRideUsage)
            }
        )

        CCButton(
            title = stringResource(Res.string.create_car_ride_last_car_ride_cancel_button_title),
            containerColor = White,
            titleColor = Error,
            onButtonListener = {
                event(OnDeclineLastCarRideUsage)
            }
        )
    }
}


@Preview
@Composable
fun StageOfCarModelScreenPreview() {
    StageOfCarModelScreen(
        uiState = CreateCarRideViewModelUiState.Steps(
            steps = CreateCarRideSteps.CAR_MODEL,
            carModel = "",
            carColor = "",
            carPlate = "",
            quantitySeats = 0,
            waitingAddress = "",
            waitingAddressList = emptyList(),
            destinationAddress = "",
            destinationAddressList = emptyList(),
            waitingHour = "",
            destinationHour = "",
            enabledCarModelScreen = false,
            hasLastCarRide = true,
            lastCarRide = null,
            isLoading = false,
            isError = false,
            isSuccess = false,
        ),
        event = {}
    )
}

@Preview
@Composable
fun StageOfQuantitySeatsScreenPreview() {
    StageOfQuantitySeatsScreen(
        uiState = CreateCarRideViewModelUiState.Steps(
            steps = CAR_QUANTITY_SEATS,
            carModel = "",
            carColor = "",
            carPlate = "",
            quantitySeats = 0,
            waitingAddress = "",
            waitingAddressList = emptyList(),
            destinationAddress = "",
            destinationAddressList = emptyList(),
            waitingHour = "",
            destinationHour = "",
            enabledCarModelScreen = false,
            hasLastCarRide = true,
            lastCarRide = null,
            isLoading = false,
            isError = false,
            isSuccess = false,
        ),
        event = {}
    )
}

@Preview
@Composable
fun StageOfWaitingAddressScreenPreview() {
    StageOfWaitingAddressScreen(
        uiState = CreateCarRideViewModelUiState.Steps(
            steps = CAR_WAITING_ADDRESS,
            carModel = "",
            carColor = "",
            carPlate = "",
            quantitySeats = 0,
            waitingAddress = "",
            waitingAddressList = emptyList(),
            destinationAddress = "",
            destinationAddressList = emptyList(),
            waitingHour = "",
            destinationHour = "",
            enabledCarModelScreen = false,
            hasLastCarRide = true,
            lastCarRide = null,
            isLoading = false,
            isError = false,
            isSuccess = false,
        ),
        title = "Waiting Address",
        onNextAction = {}
    )
}

@Preview
@Composable
fun StageOfWaitingHourScreenPreview() {
    StageOfWaitingHourScreen(
        title = "Waiting Hour",
    )
}

@Preview
@Composable
fun LastCarRideBottomSheetPreview() {
    LastCarRideBottomSheet(
        lastCarRide = LastCarRide(
            carRideInformation = CreateCarRideRequest(
                carModel = "Modelo",
                carPlate = "Placa",
                carColor = "Cor",
                quantitySeats = 3,
                waitingAddress = "Endereço de espera",
                destinationAddress = "Endereço de destino",
                waitingHour = "00:00",
                destinationHour = "00:00",
                status = "",
                isTwoPassengersBehind = false
            ),
            availableCarRide = AvailableCarRide(
                id = "1",
                waitingAddress = "Endereço de espera",
                destinationAddress = "Endereço de destino",
                waitingHour = "00:00",
                destinationHour = "00:00",
                riderPhotoUrl = "dsadas",
                riderDescription = "Teste description",
                riderUserName = "Marcelo teste",
                hoursUntilExpiration = "Termina em: 23h"
            )
        ),
        event = {}
    )
}


@Preview
@Composable
fun StageOfWaitingCreateRideScreenPreview() {
    StateOfWaitingCreateRideScreen(
        event = {}
    )
}

@Preview
@Composable
fun StateOfFinishCreateRideScreenPreview() {
    StateOfFinishCreateRideScreen(
        event = {}
    )
}