package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models

import androidx.compose.ui.graphics.Color
import org.app.marcelodev.comucarona.theme.Error
import org.app.marcelodev.comucarona.theme.Primary
import org.app.marcelodev.comucarona.theme.Secondary
import org.app.marcelodev.comucarona.theme.Success

enum class RideInProgressFilterOptions(
    val title: String,
    val color: Color
) {
    ALL("Todos", Secondary),
    MY_RIDE("Minha carona", Secondary),
    IN_PROGRESS("Em andamento", Primary),
    CANCELED("Cancelado", Error),
    FINISHED("Concluído", Success);

    companion object {
        fun fromValue(value: String): RideInProgressFilterOptions {
            return when(value) {
                ALL.title -> ALL
                MY_RIDE.title -> MY_RIDE
                IN_PROGRESS.title -> IN_PROGRESS
                CANCELED.title -> CANCELED
                FINISHED.title -> FINISHED
                else -> {
                    throw IllegalArgumentException("Invalid value")
                }
            }
        }
    }
}