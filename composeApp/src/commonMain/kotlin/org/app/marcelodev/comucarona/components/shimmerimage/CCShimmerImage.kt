package org.app.marcelodev.comucarona.components.shimmerimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.app.marcelodev.comucarona.theme.BackgroundSkeleton
import org.app.marcelodev.comucarona.theme.Error

@Composable
fun CCShimmerImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = BackgroundSkeleton,
    imageSize: Int = 35
) {

    var error: Boolean by remember { mutableStateOf(false) }
    var loading: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(imageSize.dp)
            .clip(CircleShape)
    ) {

        if(error){
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Error)
            )
        }

        if(loading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(placeholderColor)
            )
        }

//        Image(
//            painter = rememberAsyncImagePainter(
//                model = imageUrl,
//                onState = {
//                    when (it) {
//                        is Error -> {
//                            error = true
//                        }
//                        is Loading -> {
//                            loading = true
//                        }
//
//                        is Success -> {
//                            error = false
//                            loading = false
//                        }
//
//                        AsyncImagePainter.State.Empty -> {}
//                    }
//                }
//            ),
//            contentDescription = null,
//            contentScale = contentScale,
//            modifier = Modifier.matchParentSize()
//        )
    }
}