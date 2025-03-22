package org.app.marcelodev.comucarona.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

object AnimatedUtils {

    /**
     * Returns a fade in transition with a slide from the left and a fade out transition with a slide to the right.
     * @return the fade in transition with a slide from the left and a fade out transition with a slide to the right.
     */
    fun <S> animatedTransitionPage(): AnimatedContentTransitionScope<S>.() -> ContentTransform = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(durationMillis = 200),
        ).togetherWith(
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 200),
            )
        )
    }
}