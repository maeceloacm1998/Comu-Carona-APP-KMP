package org.app.marcelodev.comucarona.feature.checkcode.ui

/**
 * Represents the events that can be triggered in the check code screen.
 */
sealed class CheckCodeViewModelEventState {
    /**
     * Represents the event when the code is changed.
     *
     * @param code The new code.
     */
    data class OnChangedCode(val code: List<String>) : CheckCodeViewModelEventState()

    /**
     * Represents the event when the check code button is clicked.
     */
    data object OnClickCheckCode : CheckCodeViewModelEventState()

    /**
     * Represents the event to navigate to the home screen.
     */
    data object OnGoToHome : CheckCodeViewModelEventState()
}