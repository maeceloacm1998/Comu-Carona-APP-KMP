package org.app.marcelodev.comucarona.components.photoselect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.register_account_stage_of_photo_circular_button_title
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import org.app.marcelodev.comucarona.theme.GrayLight
import org.app.marcelodev.comucarona.theme.TextFieldColor
import org.app.marcelodev.comucarona.theme.TextFieldLineColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotoPlatformFileComponent(
    photoFile: PlatformFile? = null,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .size(300.dp)
            .background(GrayLight, shape = RoundedCornerShape(500.dp))
            .clickable {
                onClick()
            }
            .border(1.dp, TextFieldLineColor, shape = RoundedCornerShape(500.dp)),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        AnimatedVisibility(
            photoFile != null
        ) {
            AsyncImage(
                file = photoFile,
                contentDescription = photoFile?.name,
                contentScale = FillBounds,
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape)
            )
        }

        AnimatedVisibility(photoFile == null) {
            Column(
                verticalArrangement = Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.TwoTone.Person,
                    contentDescription = null,
                    tint = TextFieldColor
                )

                Text(
                    text = stringResource(Res.string.register_account_stage_of_photo_circular_button_title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextFieldColor
                )
            }
        }
    }
}