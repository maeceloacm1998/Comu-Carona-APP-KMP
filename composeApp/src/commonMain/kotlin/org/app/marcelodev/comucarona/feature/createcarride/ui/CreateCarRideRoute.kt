package org.app.marcelodev.comucarona.feature.createcarride.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_DESTINATION_ADDRESS
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_DESTINATION_HOUR
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_MODEL
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_QUANTITY_SEATS
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_WAITING_ADDRESS
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.CAR_WAITING_HOUR
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.FINISH
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideSteps.WAITING_CREATE_RIDE
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnClearAddressList
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnDestinationAddress
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnDestinationHour
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnDismissBottomSheet
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnNextStep
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnRemoveNewStep
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnWaitingAddress
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModelEventState.OnWaitingHour
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.create_car_ride_destination_address_title
import comucarona.composeapp.generated.resources.create_car_ride_waiting_address_title
import comucarona.composeapp.generated.resources.create_car_ride_waiting_hour_title
import org.app.marcelodev.comucarona.commons.utils.AnimatedUtils.animatedTransitionPage
import org.app.marcelodev.comucarona.components.bottomsheet.CCBottomSheet
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

class CreateCarRideRoute : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CreateCarRideViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        CreateCarRideRoute(
            uiState = uiState,
            event = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCarRideRoute(
    uiState: CreateCarRideViewModelUiState,
    event: (CreateCarRideViewModelEventState) -> Unit
) {
    check(uiState is CreateCarRideViewModelUiState.Steps)

    Column {
        AnimatedContent(
            modifier = Modifier.weight(2f),
            targetState = uiState.steps,
            label = "AnimatedContent",
            transitionSpec = animatedTransitionPage()
        ) { targetState ->
            when (targetState) {
                CAR_MODEL -> StageOfCarModelScreen(
                    uiState = uiState,
                    event = event
                )

                CAR_QUANTITY_SEATS -> StageOfQuantitySeatsScreen(
                    uiState = uiState,
                    event = event
                )

                CAR_WAITING_ADDRESS -> StageOfWaitingAddressScreen(
                    uiState = uiState,
                    title = stringResource(Res.string.create_car_ride_waiting_address_title),
                    textFieldValue = uiState.waitingAddress,
                    validationNextStep = uiState.waitingAddress.isNotEmpty(),
                    onValueChange = { event(OnWaitingAddress(it)) },
                    onNextAction = {
                        event(OnClearAddressList)
                        event(OnNextStep(CAR_WAITING_HOUR))
                    },
                    onBackAction = { event(OnRemoveNewStep(CAR_QUANTITY_SEATS)) },
                    onItemClicked = { event(OnWaitingAddress(it)) }
                )

                CAR_WAITING_HOUR -> StageOfWaitingHourScreen(
                    title = stringResource(Res.string.create_car_ride_waiting_hour_title),
                    onValueChange = { event(OnWaitingHour(it)) },
                    onNextAction = { event(OnNextStep(CAR_DESTINATION_ADDRESS)) },
                    onBackAction = { event(OnRemoveNewStep(CAR_WAITING_ADDRESS)) }
                )

                CAR_DESTINATION_ADDRESS -> StageOfWaitingAddressScreen(
                    uiState = uiState,
                    title = stringResource(Res.string.create_car_ride_destination_address_title),
                    textFieldValue = uiState.destinationAddress,
                    validationNextStep = uiState.waitingAddress.isNotEmpty(),
                    onValueChange = { event(OnDestinationAddress(it)) },
                    onNextAction = {
                        event(OnClearAddressList)
                        event(OnNextStep(CAR_DESTINATION_HOUR))
                    },
                    onBackAction = { event(OnRemoveNewStep(CAR_WAITING_HOUR)) },
                    onItemClicked = { event(OnDestinationAddress(it)) }
                )

                CAR_DESTINATION_HOUR -> StageOfWaitingHourScreen(
                    title = stringResource(Res.string.create_car_ride_destination_hour_title),
                    onValueChange = { event(OnDestinationHour(it)) },
                    onNextAction = { event(OnNextStep(WAITING_CREATE_RIDE)) },
                    onBackAction = { event(OnRemoveNewStep(CAR_DESTINATION_ADDRESS)) }
                )

                WAITING_CREATE_RIDE -> StateOfWaitingCreateRideScreen(
                    event = event
                )

                FINISH -> StateOfFinishCreateRideScreen(
                    event = event
                )
            }
        }
        CCBottomSheet(
            showSheet = uiState.hasLastCarRide,
            onDismissRequest = {
                event(OnDismissBottomSheet)
            },
            containerColor = Color.White
        ) {
            if(uiState.lastCarRide != null) {
                LastCarRideBottomSheet(
                    lastCarRide = uiState.lastCarRide!!,
                    event = event
                )
            }
        }
    }
}
