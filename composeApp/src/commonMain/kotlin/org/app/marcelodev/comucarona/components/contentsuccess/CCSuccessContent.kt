package org.app.marcelodev.comucarona.components.contentsuccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.components.button.CCButton
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.ic_success
import org.app.marcelodev.comucarona.theme.SoftBlack
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CCSuccessContent(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 20.dp),
            painter = painterResource(Res.drawable.ic_success),
            contentDescription = "check"
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = SoftBlack,
            fontWeight = SemiBold,
            textAlign = Center,
            modifier = Modifier.padding(
                horizontal = 20.dp
            ),
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextFieldColor,
            textAlign = Center,
            modifier = Modifier.padding(
                horizontal = 20.dp,
            ),
        )

        Spacer(modifier = Modifier.height(30.dp))

        CCButton(
            modifier = Modifier.fillMaxWidth(),
            title = buttonText,
            isEnable = true,
            onButtonListener = onClick

        )
    }
}

@Preview
@Composable
fun CCSuccessContentPreview() {
    CCSuccessContent(
        title = "Carona criada com sucesso!",
        description = "Agora é só esperar os passageiros se inscreverem na sua carona.",
        buttonText = "ir para home"
    )
}