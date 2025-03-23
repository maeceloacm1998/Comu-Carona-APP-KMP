package org.app.marcelodev.comucarona.components.snackbar

import androidx.compose.ui.graphics.Color
import org.app.marcelodev.comucarona.theme.Error
import org.app.marcelodev.comucarona.theme.Secondary
import org.app.marcelodev.comucarona.theme.Success

enum class SnackbarCustomType(val background: Color, val text: Color) {
    SUCCESS(Success, Color.White),
    ERROR(Error, Color.White),
    WARNING(Secondary, Color.White);
}