package org.app.marcelodev.comucarona.components.contentloading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun CCLoadingContent(
    isLoading: Boolean,
    isError: Boolean,
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    AnimatedVisibility(
        visible = isLoading,
        modifier = Modifier.fillMaxSize()
    ) {
        loadingContent()
    }

    AnimatedVisibility(
        visible = isError,
        modifier = Modifier.fillMaxSize()
    ) {
        errorContent()
    }

    AnimatedVisibility(
        visible = !isLoading && !isError,
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CCLoadingSwipeRefreshContent(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    isLoading: Boolean,
    isRefresh: Boolean,
    isError: Boolean,
    state: PullToRefreshState = rememberPullToRefreshState(),
    onRefresh: () -> Unit,
    loadingContent: @Composable () -> Unit,
    errorContent: @Composable () -> Unit,
    indicator: @Composable BoxScope.() -> Unit = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefresh,
            state = state
        )
    },
    content: @Composable () -> Unit
) {

    AnimatedVisibility(
        visible = isLoading,
        modifier = Modifier.fillMaxSize()
    ) {
        loadingContent()
    }

    AnimatedVisibility(
        visible = isError,
        modifier = Modifier.fillMaxSize()
    ) {
        errorContent()
    }

    AnimatedVisibility(
        visible = !isLoading && !isError,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier.pullToRefresh(state = state, isRefreshing = isRefresh, onRefresh = onRefresh),
            contentAlignment = contentAlignment
        ) {
            content()
            indicator()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CCLoadingShimmerContent() {
    val loadingLottieAnimation by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/search_car_ride.json").decodeToString()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
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
    }
}