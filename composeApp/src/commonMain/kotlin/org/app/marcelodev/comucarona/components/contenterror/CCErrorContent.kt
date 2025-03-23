package org.app.marcelodev.comucarona.components.contenterror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.TextColor
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CCErrorContent(
    modifier: Modifier = Modifier,
    title: String
) {
//    val loadingLottieAnimation by rememberLottieComposition(
//        spec = LottieCompositionSpec.RawRes(R.raw.search_car_ride)
//    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        LottieAnimation(
//            composition = loadingLottieAnimation,
//            modifier = Modifier
//                .size(120.dp),
//            contentScale = ContentScale.Crop,
//            iterations = LottieConstants.IterateForever // Makes the animation loop
//        )

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