package org.app.marcelodev.comucarona.feature.registeraccount.ui

import io.github.vinceglb.filekit.PlatformFile
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps

/**
 * Represents the events that can be triggered in the check code screen.
 */
sealed class RegisterAccountViewModelEventState {
 /**
  * Represents the event when the next step button is clicked.
  *
  * @param step The new step.
  */
 data class OnNextStep(val step: RegisterAccountSteps) : RegisterAccountViewModelEventState()

 /**
  * Represents the event when the previous step button is clicked.
  *
  * @param step The new step.
  */
 data class OnRemoveNewStep(val step: RegisterAccountSteps) :
  RegisterAccountViewModelEventState()

 /**
  * Represents the event when the full name is updated.
  *
  * @param fullName The new full name.
  */
 data class OnUpdateFullName(val fullName: String) : RegisterAccountViewModelEventState()

 /**
  * Represents the event when the phone number is updated.
  *
  * @param phoneNumber The new phone number.
  */
 data class OnUpdatePhoneNumber(val phoneNumber: String) : RegisterAccountViewModelEventState()

 /**
  * Represents the event when the photo is updated.
  */
 data class OnUpdatePhoto(val photo: PlatformFile) : RegisterAccountViewModelEventState()
}