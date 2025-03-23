package org.app.marcelodev.comucarona.components.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.DisabledBackground
import org.app.marcelodev.comucarona.theme.Primary
import org.app.marcelodev.comucarona.theme.Success
import org.app.marcelodev.comucarona.theme.TextColor
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CCButton(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = White,
    containerColor: Color = Primary,
    isLoading: Boolean = false,
    isSuccess: Boolean = false,
    isEnable: Boolean = true,
    onButtonListener: () -> Unit = { },
) {
//    val loadingLottieAnimation by rememberLottieComposition(
//        spec = LottieCompositionSpec.RawRes(R.raw.loading_lottie)
//    )

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSuccess) Success else containerColor,
            disabledContainerColor = DisabledBackground,
            contentColor = if (isSuccess) White else TextColor,
            disabledContentColor = TextColor,
        ),
        enabled = isEnable,
        onClick = onButtonListener,
        shape = MaterialTheme.shapes.small,
    ) {

        AnimatedVisibility(
            visible = isSuccess && !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "Check",
                tint = White,
                modifier = Modifier.size(24.dp)
            )
        }

        AnimatedVisibility(
            visible = isLoading && !isSuccess,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
//            LottieAnimation(
//                composition = loadingLottieAnimation,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterVertically),
//                contentScale = ContentScale.Crop,
//                iterations = LottieConstants.IterateForever // Makes the animation loop
//            )
        }

        AnimatedVisibility(
            visible = !isLoading && !isSuccess,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun CCButtonPreview() {
    CCButton(
        title = "Button"
    )
}

@Preview
@Composable
fun CCButtonLoadingPreview() {
    CCButton(
        title = "Button",
        isLoading = true
    )
}

@Preview
@Composable
fun CCButtonSuccessPreview() {
    CCButton(
        title = "Button",
        isSuccess = true
    )
}

@Preview
@Composable
fun CCButtonDisablePreview() {
    CCButton(
        title = "Button",
        isEnable = false
    )
}