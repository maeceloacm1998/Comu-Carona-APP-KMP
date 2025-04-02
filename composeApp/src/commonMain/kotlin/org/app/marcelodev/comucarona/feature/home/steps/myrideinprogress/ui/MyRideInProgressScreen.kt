package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.my_ride_in_progress_title
import comucarona.composeapp.generated.resources.ride_in_progress_empty_title
import org.app.marcelodev.comucarona.components.carridecard.AvailableCarRideCard
import org.app.marcelodev.comucarona.components.chip.CCChip
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModelEventState.OnNavigateToMyRideDetails
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModelEventState.OnSelectFilter
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldLineColor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MyRideInProgressScreen(
    uiState: MyRideInProgressViewModelUiState,
    onEvent: (MyRideInProgressViewModelEventState) -> Unit
) {
    val filterOptionsWithoutMyRides: List<RideInProgressFilterOptions> =
        RideInProgressFilterOptions.entries.filter { it != RideInProgressFilterOptions.MY_RIDE }

    Scaffold(
        topBar = {
            MyRideInProgressTopBar(
                modifier = Modifier
                    .background(White)
                    .padding(vertical = 20.dp),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(White)
        ) {
            HorizontalLine(
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp,
                    ),
                color = TextFieldLineColor,
                thickness = 1f
            )

            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(15.dp))
                }

                items(filterOptionsWithoutMyRides) { item ->
                    CCChip(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 5.dp),
                        title = item.title,
                        isActivated = uiState.rideInProgressFilterSelected == item,
                        onClick = {
                            onEvent(OnSelectFilter(item))
                        }
                    )
                }
            }

            if (uiState is MyRideInProgressViewModelUiState.HasRiderInProgress) {
                if(uiState.isLoadingList) {
                    CCLoadingShimmerContent()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(uiState.rideInProgressList) { items ->
                            AvailableCarRideCard(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                waitingHour = items.waitingHour,
                                destinationHour = items.destinationHour,
                                waitingAddress = items.waitingAddress,
                                destinationAddress = items.destinationAddress,
                                riderPhotoUrl = items.riderInformation.photoUrl,
                                riderUserName = items.riderInformation.username,
                                riderDescription = "Participa de alvo",
                                status = items.states.map { RideInProgressFilterOptions.fromValue(it) },
                                onClick = {
                                    onEvent(OnNavigateToMyRideDetails(items.uuid))
                                }
                            )
                        }
                    }
                }
            } else {
                CCErrorContent(
                    modifier = Modifier.fillMaxSize(),
                    title = stringResource(Res.string.ride_in_progress_empty_title)
                )
            }
        }
    }
}

@Composable
fun MyRideInProgressTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Res.string.my_ride_in_progress_title),
            style = MaterialTheme.typography.titleMedium,
            color = SoftBlack,
            fontWeight = SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun RideInProgressScreenPreview() {
    MyRideInProgressScreen(
        uiState = MyRideInProgressViewModelUiState.HasRiderInProgress(
            rideInProgressList = listOf(),
            rideInProgressListFiltered = listOf(),
            rideInProgressFilterSelected = RideInProgressFilterOptions.ALL,
            isLoadingList = false,
            isLoading = false,
            isError = false,
            isRefresh = false,
        ),
        onEvent = {}
    )
}