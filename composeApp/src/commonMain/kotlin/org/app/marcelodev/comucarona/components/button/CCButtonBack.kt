package org.app.marcelodev.comucarona.components.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldLineColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCButtonBack(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier.border(1.dp, TextFieldLineColor, shape = RoundedCornerShape(20)),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.Transparent,
        )
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Voltar",
            tint = SoftBlack
        )
    }
}

@Preview
@Composable
fun CCButtonBackPreview() {
    CCButtonBack()
}