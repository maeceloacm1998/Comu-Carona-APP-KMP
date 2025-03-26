package org.app.marcelodev.comucarona.components.shimmerimage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.app.marcelodev.comucarona.theme.BackgroundSkeleton
import org.app.marcelodev.comucarona.theme.Error

@Composable
fun CCShimmerImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = BackgroundSkeleton,
    imageSize: Int = 35,
    onClick: () -> Unit = {}
) {

    var error: Boolean by remember { mutableStateOf(false) }
    var loading: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(imageSize.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    ) {

        if (error) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Error)
            )
        }

        if (loading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(placeholderColor)
            )
        }

        AsyncImage(
            model = imageUrl,
            onLoading = { loading = true },
            onError = { error = true },
            onSuccess = {
                loading = false
                error = false
            },
            contentDescription = null,
            contentScale = contentScale,
            modifier = Modifier.matchParentSize()
        )
    }
}