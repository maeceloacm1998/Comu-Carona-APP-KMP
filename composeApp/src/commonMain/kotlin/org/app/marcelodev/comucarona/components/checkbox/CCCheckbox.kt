package org.app.marcelodev.comucarona.components.checkbox

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.app.marcelodev.comucarona.theme.TextFieldLineColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.size(24.dp).border(1.dp, TextFieldLineColor, shape = RoundedCornerShape(4.dp)),
        colors = CheckboxDefaults.colors(
            checkedColor = TextFieldColor,
            uncheckedColor = TextFieldLineColor,
            checkmarkColor = White,
        ),
    )
}

@Preview
@Composable
fun CCCheckboxPreview() {
    CCCheckbox(
        checked = true,
        onCheckedChange = { }
    )
}