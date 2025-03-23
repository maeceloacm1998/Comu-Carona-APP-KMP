package org.app.marcelodev.comucarona.components.contentloading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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