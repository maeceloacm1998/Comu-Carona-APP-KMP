package org.app.marcelodev.comucarona.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.DisabledBackground
import org.app.marcelodev.comucarona.theme.TextColor
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCOutlinedButton(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = TextFieldColor,
    containerColor: Color = Transparent,
    borderColor: Color = TextFieldColor,
    isEnable: Boolean = true,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
    icon: @Composable () -> Unit = { },
    onButtonListener: () -> Unit = { },
) {

    OutlinedButton(
        modifier = modifier
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = DisabledBackground,
            contentColor = borderColor,
            disabledContentColor = TextColor,
        ),
        border = BorderStroke(1.dp, borderColor),
        enabled = isEnable,
        onClick = onButtonListener,
        shape = shape,
    ) {
        icon()

        Text(
            text = title,
            color = titleColor,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun CCOutlinedButtonPreview() {
    CCOutlinedButton(title = "Button")
}