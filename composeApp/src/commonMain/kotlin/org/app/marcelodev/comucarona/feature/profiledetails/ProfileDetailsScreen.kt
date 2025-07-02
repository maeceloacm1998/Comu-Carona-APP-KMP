package org.app.marcelodev.comucarona.feature.profiledetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.register_account_stage_of_birth_date_hint
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_hint
import comucarona.composeapp.generated.resources.register_account_stage_of_phone_number_hint
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.utils.StringUtils.BIRTH_DATE_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.formatBirthDate
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.horizontalline.HorizontalLine
import org.app.marcelodev.comucarona.components.permissions.RequestGalleryPermission
import org.app.marcelodev.comucarona.components.photoselect.PhotoPlatformFileComponent
import org.app.marcelodev.comucarona.components.shimmerimage.CCShimmerImage
import org.app.marcelodev.comucarona.components.snackbar.CCSnackbar
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.components.textfield.CCTextField
import org.app.marcelodev.comucarona.feature.profiledetails.ProfileDetailsViewModelEventState.*
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileDetailsScreen(
    uiState: ProfileDetailsViewModelUiState.HasProfileDetails,
    onEvent: (ProfileDetailsViewModelEventState) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    val profileDetailsInformation = checkNotNull(uiState.profileDetailsInformation)

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

                CCButtonBack(onClick = {
                    onEvent(OnBack)
                })

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    CCShimmerImage(
                        imageUrl = uiState.profileDetailsInformation.photoUrl.orEmpty(),
                        contentScale = FillBounds,
                        imageSize = 100,
                        onClick = {
                            coroutineScope.launch {
                                val image = FileKit.openFilePicker(type = FileKitType.Image)
                                image?.let { onEvent(OnUpdatePhoto(it)) }
                            }
                        }
                    )

                    RequestGalleryPermission()
                }

                Spacer(modifier = Modifier.padding(vertical = 30.dp))

                CCTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = stringResource(Res.string.register_account_stage_of_full_name_hint),
                    value = uiState.profileDetailsInformation.fullName,
                    onValueChange = { text ->
                        onEvent(OnUpdateFullName(fullName = text))
                    },
                    keyboardType = KeyboardType.Text,
                    isErrorMessage = false,
                    onImeAction = {}
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                CCTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = stringResource(Res.string.register_account_stage_of_birth_date_hint),
                    value = uiState.profileDetailsInformation.birthDate.formatBirthDate(),
                    onValueChange = { text ->
                        onEvent(OnUpdateBirthDate(birthDate = text))
                    },
                    keyboardType = KeyboardType.Text,
                    isErrorMessage = uiState.birthDateErro,
                    errorMessage = stringResource(Res.string.register_account_stage_of_birth_date_error_message),
                    maxLength = BIRTH_DATE_LENGTH,
                    onImeAction = {}
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                CCTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = stringResource(Res.string.register_account_stage_of_phone_number_hint),
                    value = uiState.profileDetailsInformation.phoneNumber,
                    onValueChange = { text ->
                        onEvent(OnUpdatePhoneNumber(phoneNumber = text))
                    },
                    keyboardType = KeyboardType.Text,
                    isErrorMessage = false,
                    onImeAction = {}
                )
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
                    title = stringResource(Res.string.profile_button_title),
                    isLoading = uiState.isLoadingUpdate,
                    isSuccess = uiState.isSuccessUpdate,
                    isEnable = uiState.isChangeFields,
                    onButtonListener = { onEvent(OnUpdateProfile) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileDetailsScreenPreview() {
    ProfileDetailsScreen(
        uiState = ProfileDetailsViewModelUiState.HasProfileDetails(
            profileDetailsInformation = RegisterAccountRequest(
                fullName = "",
                birthDate = "",
                phoneNumber = "",
                photoUrl = ""
            ),
            snackbarType = SnackbarCustomType.ERROR,
            isChangeFields = false,
            birthDateErro = false,
            isSuccessUpdate = false,
            isLoadingImage = false,
            isLoadingUpdate = false,
        ),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}