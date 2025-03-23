package org.app.marcelodev.comucarona.components.chip

import androidx.compose.animation.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.Primary
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCChip(
    modifier: Modifier = Modifier,
    title: String,
    isActivated: Boolean = false,
    onClick: () -> Unit = { },
) {
    val borderColorState =  remember { Animatable(TextFieldColor) }
    val backgroundColorState = remember { Animatable(Color.Transparent) }
    val textColorState = remember { Animatable(TextFieldColor) }

    LaunchedEffect(isActivated) {
        borderColorState.animateTo(
            if (isActivated) Primary else TextFieldColor
        )
    }

    LaunchedEffect(isActivated) {
        backgroundColorState.animateTo(
            if (isActivated) Primary else Color.Transparent
        )
    }

    LaunchedEffect(isActivated) {
        textColorState.animateTo(
            if (isActivated) Color.White else TextFieldColor
        )
    }

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColorState.value,
        ),
        onClick = onClick,
        border = BorderStroke(1.dp, borderColorState.value),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = textColorState.value,
            fontWeight = Normal,
            modifier = Modifier.padding(
                start = 5.dp,
                end = 5.dp,
            ),
        )
    }
}

@Preview
@Composable
fun CCChip1Preview() {
    CCChip(
        title = "Todos",
        isActivated = true
    )
}

@Preview
@Composable
fun CCChip2Preview() {
    Column {
        CCChip(
            title = "Todos",
            isActivated = false
        )

        CCChip(
            title = "Minhas Caronas",
            isActivated = false
        )
    }
}