package org.app.marcelodev.comucarona.components.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCAlert(
    modifier: Modifier = Modifier,
    message: String,
    alertType: AlertCustomType = AlertCustomType.WARNING
) {
    val showAlertState = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(5000)
        showAlertState.value = false
    }

    if (showAlertState.value) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(alertType.background, shape = RoundedCornerShape(5.dp))
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(0.9f),
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = alertType.text,
                fontWeight = Medium,
            )

            Column(
                modifier = Modifier.weight(0.1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { showAlertState.value = false },
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Alert",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CCAlertPreview() {
    CCAlert(
        message = "Teste teste teste teste teste teste stes teste stes teste stes tes",
    )
}