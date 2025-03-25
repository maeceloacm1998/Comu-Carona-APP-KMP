package org.app.marcelodev.comucarona.feature.home.bottomnavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import org.app.marcelodev.comucarona.theme.DisabledBackground
import org.app.marcelodev.comucarona.theme.Primary

@Composable
fun RowScope.TabItem(tab: Tab) {
    val navigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = navigator.current == tab,
        onClick = { navigator.current = tab },
        label = {
            Text(
                modifier = Modifier.height(30.dp),
                text = tab.options.title,
                        fontWeight = SemiBold,
                textAlign = Center,
                color = if (navigator.current == tab) Primary else DisabledBackground
            )
        },
        icon = {
            tab.options.icon?.let {
                Icon(
                    modifier = Modifier.size(23.dp),
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = if (navigator.current == tab) Primary else DisabledBackground
                )
            }
        }
    )
}