package org.app.marcelodev.comucarona.feature.home.steps.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
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
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnLogout
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnNavigateToProfileDetails
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.ic_profile
import comucarona.composeapp.generated.resources.profile_information_description
import comucarona.composeapp.generated.resources.profile_information_title
import org.app.marcelodev.comucarona.components.boxselection.CCBoxSelection
import org.app.marcelodev.comucarona.components.carridecard.UserSelectionBox
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldLineColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
    uiState: ProfileViewModelUiState.HasProfile,
    onEvent: (ProfileViewModelEventState) -> Unit
) {
    val profile: RegisterAccountRequest = checkNotNull(uiState.profileInformation)

    Scaffold(
        topBar = {
            ProfileTopBar(
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

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            UserSelectionBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                riderPhotoUrl = profile.photoUrl.orEmpty(),
                riderUserName = profile.fullName,
                riderDescription = "Participa de alvo da mocidade",
                showArrow = false
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            CCBoxSelection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                title = stringResource(Res.string.profile_information_title),
                description = stringResource(Res.string.profile_information_description),
                iconPainter = painterResource(Res.drawable.ic_profile),
                onClick = {
                    onEvent(OnNavigateToProfileDetails)
                }
            )

            Spacer(modifier = Modifier.padding(top = 5.dp))

            CCBoxSelection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                title = stringResource(Res.string.profile_exit_app_title),
                description = stringResource(Res.string.profile_exit_app_description),
                iconImageVector = Icons.Outlined.ExitToApp,
                onClick = {
                    onEvent(OnLogout)
                }
            )
        }
    }
}

@Composable
fun ProfileTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(Res.string.profile_title),
            style = MaterialTheme.typography.titleMedium,
            color = SoftBlack,
            fontWeight = SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileViewModelUiState.HasProfile(
            profileInformation = RegisterAccountRequest(
                fullName = "John Doe",
                photoUrl = "https://randomuser.me/api/portraits",
                phoneNumber = "(31) 99999-9999",
                birthDate = "02/05/2025"
            ),
            isLoading = false,
            isError = false
        ),
        onEvent = {}
    )
}