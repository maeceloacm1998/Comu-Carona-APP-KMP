package org.app.marcelodev.comucarona.feature.home


/**
 * Represents the UI state of the home screen.
 */
sealed interface HomeViewModelUiState {
    /**
     * Represents the state when the home screen is loading.
     */
    data class Data(
        val steps: String,
    ) : HomeViewModelUiState
}

/**
 * Represents the state of the home screen.
 */
data class HomeViewModelState(
    val steps: String = ""
) {

    /**
     * Converts the state to a UI state.
     */
    fun toUiState(): HomeViewModelUiState = HomeViewModelUiState.Data(
        steps = steps
    )
}
