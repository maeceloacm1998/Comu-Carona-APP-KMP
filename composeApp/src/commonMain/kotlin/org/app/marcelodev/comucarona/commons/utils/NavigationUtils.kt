package org.app.marcelodev.comucarona.commons.utils

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

/**
 * Utility object for navigation operations.
 * Example:
 * // 🍇, 🍉, 🍌, 🍐, 🥝, 🍋 (stack order)
 *
 * NavigationUtils.addNewScreen(stack, "🍒")
 * // 🍇, 🍉, 🍌, 🍐, 🥝, 🍋, 🍒
 *
 * NavigationUtils.removeLastScreen(stack)
 * // 🍇, 🍉, 🍌, 🍐, 🥝, 🍋
 *
 * NavigationUtils.removeScreensUntil(stack, "🍌")
 * // 🍇, 🍉, 🍌
 *
 * NavigationUtils.replaceLastScreen(stack, "🍍")
 * // 🍇, 🍉, 🍍
 *
 * NavigationUtils.replaceAllScreens(stack, "🍓")
 * // 🍓
 */
object NavigationUtils {

    /**
     * Adds a new screen to the navigation stack.
     *
     * @param navigator The navigator instance.
     * @param screen The screen to be added.
     */
    fun addNewScreen(navigator: Navigator, screen: Screen) {
        navigator.push(screen)
    }

    /**
     * Removes the last screen from the navigation stack.
     *
     * @param navigator The navigator instance.
     */
    fun removeLastScreen(navigator: Navigator) {
        navigator.pop()
    }

    /**
     * Removes screens from the navigation stack until the specified screen is reached.
     *
     * @param navigator The navigator instance.
     * @param screen The screen to stop at.
     */
    fun removeScreensUntil(navigator: Navigator, screen: Screen) {
        navigator.popUntil { it == screen }
    }

    /**
     * Replaces the last screen in the navigation stack with a new screen.
     *
     * @param navigator The navigator instance.
     * @param screen The screen to replace the last screen with.
     */
    fun replaceLastScreen(navigator: Navigator, screen: Screen) {
        navigator.replace(screen)
    }

    /**
     * Replaces all screens in the navigation stack with a new screen.
     *
     * @param navigator The navigator instance.
     * @param screen The screen to replace all screens with.
     */
    fun replaceAllScreens(navigator: Navigator, screen: Screen) {
        navigator.replaceAll(screen)
    }
}