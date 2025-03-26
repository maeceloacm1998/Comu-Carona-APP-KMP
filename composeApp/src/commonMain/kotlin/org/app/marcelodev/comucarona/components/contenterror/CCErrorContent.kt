package org.app.marcelodev.comucarona.components.contenterror

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.app.marcelodev.comucarona.theme.TextColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CCErrorContent(
    modifier: Modifier = Modifier,
    title: String
) {
    val loadingLottieAnimation by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/search_car_ride.json").decodeToString()
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberLottiePainter(
                composition = loadingLottieAnimation,
                iterations = Compottie.IterateForever
            ),
            modifier = Modifier
                .size(120.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "Lottie animation"
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = TextColor,
            fontWeight = SemiBold,
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 30.dp,
            ),
        )
    }
}

@Preview
@Composable
fun CCErrorContentPreview() {
    CCErrorContent(
        title = "Title"
    )
}