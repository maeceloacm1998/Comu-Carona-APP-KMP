package org.app.marcelodev.comucarona.feature.registeraccount.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.feature.registeraccount.ui.RegisterAccountViewModelEventState.*
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_hint
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_message
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_title
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.utils.StringUtils.FULL_NAME_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.PHONE_NUMBER_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.formatPhoneNumber
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.permissions.RequestGalleryPermission
import org.app.marcelodev.comucarona.components.photoselect.PhotoPlatformFileComponent
import org.app.marcelodev.comucarona.components.textfield.CCTextField
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.*
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StageOfFullNameScreen(
    uiState: RegisterAccountViewModelUiState.Register,
    event: (RegisterAccountViewModelEventState) -> Unit
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
    ) {

        Spacer(modifier = Modifier.height(90.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_full_name_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_full_name_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesters),
            placeholder = stringResource(Res.string.register_account_stage_of_full_name_hint),
            value = uiState.fullName,
            onValueChange = { text ->
                event(OnUpdateFullName(text))
            },
            keyboardType = KeyboardType.Text,
            isErrorMessage = false,
            onImeAction = {
                event(OnNextStep(FULL_NAME))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_full_name_button_title),
            isEnable = uiState.fullName.length >= FULL_NAME_LENGTH,
            onButtonListener = {
                event(OnNextStep(FULL_NAME))
            }
        )
    }
}

@Composable
fun StageOfPhoneNumberScreen(
    uiState: RegisterAccountViewModelUiState.Register,
    event: (RegisterAccountViewModelEventState) -> Unit
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
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            event(OnRemoveNewStep(PHONE_NUMBER))
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_phone_number_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_phone_number_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesters),
            placeholder = stringResource(Res.string.register_account_stage_of_phone_number_hint),
            value = uiState.phoneNumber.formatPhoneNumber(),
            onValueChange = { text ->
                event(OnUpdatePhoneNumber(text))
            },
            maxLength = PHONE_NUMBER_LENGTH,
            keyboardType = KeyboardType.Number,
            isErrorMessage = false,
            onImeAction = {
                event(OnNextStep(PHONE_NUMBER))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_phone_number_button_title),
            isEnable = uiState.phoneNumber.formatPhoneNumber().length == PHONE_NUMBER_LENGTH,
            onButtonListener = {
                event(OnNextStep(PHONE_NUMBER))
            }
        )
    }
}

@Composable
fun StageOfInformationAboutPhotoPermission(
    event: (RegisterAccountViewModelEventState) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            event(OnRemoveNewStep(PHOTO))
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_permission_photo_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_permission_photo_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Image(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .fillMaxWidth(),
            painter = painterResource(Res.drawable.permission),
            contentDescription = "",
            contentScale = Crop,
        )

        Spacer(modifier = Modifier.height(30.dp))


        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_permission_photo_button_title),
            onButtonListener = {
                event(OnNextStep(PERMISSION))
            }
        )
    }
}

@Composable
fun StageOfPhotoScreen(
    uiState: RegisterAccountViewModelUiState.Register,
    event: (RegisterAccountViewModelEventState) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CCButtonBack(onClick = {
            event(OnRemoveNewStep(PHOTO))
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_photo_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_photo_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            PhotoPlatformFileComponent(
                photoFile = uiState.photoUrl,
                onClick = {
                    coroutineScope.launch {
                        val image = FileKit.openFilePicker(type = FileKitType.Image)
                        image?.let { event(OnUpdatePhoto(it)) }
                    }
                }
            )
            RequestGalleryPermission()
        }

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_photo_button_title),
            isEnable = uiState.photoUrl != null,
            isLoading = uiState.isLoading,
            isSuccess = uiState.isSuccess,
            onButtonListener = {
                event(OnNextStep(PHOTO))
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_photo_button_next_step_title),
            isLoading = uiState.isLoading,
            isSuccess = uiState.isSuccess,
            onButtonListener = {
                event(OnNextStep(PHOTO))
            }
        )
    }
}

@Preview
@Composable
fun StageOfFullNameScreenPreview() {
    StageOfFullNameScreen(
        uiState = RegisterAccountViewModelUiState.Register(
            steps = FULL_NAME,
            fullName = "John Doe",
            birthDateErro = false,
            phoneNumber = "31999999999",
            photoUrl = null,
            isLoading = false,
            isError = false,
            isSuccess = false
        ),
        event = { }
    )
}

@Preview
@Composable
fun StageOfPhoneNumberScreenPreview() {
    StageOfPhoneNumberScreen(
        uiState = RegisterAccountViewModelUiState.Register(
            steps = PHONE_NUMBER,
            fullName = "John Doe",
            birthDateErro = false,
            phoneNumber = "31999999999",
            photoUrl = null,
            isLoading = false,
            isError = false,
            isSuccess = false
        ),
        event = { }
    )
}

@Preview
@Composable
fun StageOfInformationAboutPhotoPermissionPreview() {
    StageOfInformationAboutPhotoPermission(event = {})
}

@Preview
@Composable
fun StageOfPhotoScreenPreview() {
    StageOfPhotoScreen(
        uiState = RegisterAccountViewModelUiState.Register(
            steps = PHOTO,
            fullName = "John Doe",
            birthDateErro = false,
            phoneNumber = "31999999999",
            photoUrl = null,
            isLoading = false,
            isError = false,
            isSuccess = false
        ),
        event = { }
    )
}