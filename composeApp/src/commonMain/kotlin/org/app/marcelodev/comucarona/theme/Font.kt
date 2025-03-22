package org.app.marcelodev.comucarona.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import comucarona.composeapp.generated.resources.Inter_bold
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.inter_medium
import comucarona.composeapp.generated.resources.inter_regular
import comucarona.composeapp.generated.resources.urbanist_bold
import comucarona.composeapp.generated.resources.urbanist_medium
import comucarona.composeapp.generated.resources.urbanist_regular
import org.jetbrains.compose.resources.Font

@Composable
fun InterFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.Inter_bold, FontWeight.Bold),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_regular, FontWeight.Normal)
    )
}

@Composable
fun UrbanistFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.urbanist_bold, FontWeight.Bold),
        Font(Res.font.urbanist_medium, FontWeight.Medium),
        Font(Res.font.urbanist_regular, FontWeight.Normal),
    )
}