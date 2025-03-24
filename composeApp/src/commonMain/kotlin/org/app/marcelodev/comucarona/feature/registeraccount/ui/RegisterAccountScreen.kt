package org.app.marcelodev.comucarona.feature.registeraccount.ui

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.comu_carona.feature.registeraccount.ui.RegisterAccountViewModelEventState
import com.app.comu_carona.feature.registeraccount.ui.RegisterAccountViewModelEventState.*
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_hint
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_message
import comucarona.composeapp.generated.resources.register_account_stage_of_full_name_title
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.gallery.GALLERY
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.utils.StringUtils.BIRTH_DATE_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.FULL_NAME_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.PHONE_NUMBER_LENGTH
import org.app.marcelodev.comucarona.commons.utils.StringUtils.formatBirthDate
import org.app.marcelodev.comucarona.commons.utils.StringUtils.formatPhoneNumber
import org.app.marcelodev.comucarona.components.button.CCButton
import org.app.marcelodev.comucarona.components.button.CCButtonBack
import org.app.marcelodev.comucarona.components.photoselect.PhotoImageBitmapComponent
import org.app.marcelodev.comucarona.components.textfield.CCTextField
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.BIRTH_DATE
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.FULL_NAME
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.PHONE_NUMBER
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.PHOTO
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldColor
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
fun StageOfBirthDateScreen(
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
            event(OnRemoveNewStep(BIRTH_DATE))
        })

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_birth_date_title),
            style = MaterialTheme.typography.titleLarge,
            color = SoftBlack
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(Res.string.register_account_stage_of_birth_date_message),
            style = MaterialTheme.typography.bodyLarge,
            color = TextFieldColor
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesters),
            placeholder = stringResource(Res.string.register_account_stage_of_birth_date_hint),
            value = uiState.birthDate.formatBirthDate(),
            onValueChange = { text ->
                event(OnUpdateBirthDate(text))
            },
            maxLength = BIRTH_DATE_LENGTH,
            keyboardType = KeyboardType.Number,
            isErrorMessage = false,
            onImeAction = {
                event(OnNextStep(BIRTH_DATE))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_birth_date_button_title),
            isEnable = uiState.birthDate.formatBirthDate().length == BIRTH_DATE_LENGTH,
            onButtonListener = {
                event(OnNextStep(BIRTH_DATE))
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
fun StageOfPhotoScreen(
    uiState: RegisterAccountViewModelUiState.Register,
    event: (RegisterAccountViewModelEventState) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }

    LaunchedEffect(Unit) {
        controller.providePermission(Permission.GALLERY)
    }

    BindEffect(controller)

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
            PhotoImageBitmapComponent(
                photoBitmap = uiState.photoUrl,
                onClick = {
                    coroutineScope.launch {

                     }
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.register_account_stage_of_photo_button_title),
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
            birthDate = "01/01/2000",
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
fun StageOfBirthDateScreenPreview() {
    StageOfBirthDateScreen(
        uiState = RegisterAccountViewModelUiState.Register(
            steps = BIRTH_DATE,
            fullName = "John Doe",
            birthDate = "01/01/2000",
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
            birthDate = "01/01/2000",
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
fun StageOfPhotoScreenPreview() {
    StageOfPhotoScreen(
        uiState = RegisterAccountViewModelUiState.Register(
            steps = PHOTO,
            fullName = "John Doe",
            birthDate = "01/01/2000",
            phoneNumber = "31999999999",
            photoUrl = null,
            isLoading = false,
            isError = false,
            isSuccess = false
        ),
        event = { }
    )
}