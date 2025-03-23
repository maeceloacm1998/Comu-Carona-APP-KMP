package org.app.marcelodev.comucarona.components.carridecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.ic_car_ride
import org.app.marcelodev.comucarona.components.shimmerimage.CCShimmerImage
import org.app.marcelodev.comucarona.theme.Primary
import org.app.marcelodev.comucarona.theme.TextFieldLightColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserSelectionBox(
    modifier: Modifier = Modifier,
    riderPhotoUrl: String,
    riderUserName: String,
    riderDescription: String,
    showCar: Boolean = true,
    showArrow: Boolean = true,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(showCar) {
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .weight(0.1f)
                        .padding(end = 10.dp),
                    painter = painterResource(Res.drawable.ic_car_ride),
                    contentDescription = "car_Ride_Icon",
                )
            }

            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                CCShimmerImage(
                    imageUrl = riderPhotoUrl,
                    contentScale = FillBounds,
                    imageSize = 35
                )

                Column(
                    modifier = Modifier.padding(start = 10.dp),
                ) {
                    Text(
                        text = riderUserName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Primary,
                        fontWeight = Bold
                    )

                    Spacer(modifier = Modifier.size(5.dp))

                    Text(
                        text = riderDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextFieldLightColor,
                        fontWeight = Bold
                    )
                }
            }

            if(showArrow) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp)
                        .weight(0.1f),
                    painter = rememberVectorPainter(image = Icons.Default.ArrowForward),
                    contentDescription = "Arrow_Forward",
                    tint = Primary
                )
            }
        }
    }
}


@Preview
@Composable
fun UserSelectionBoxPreview() {
    UserSelectionBox(
        riderPhotoUrl = "https://i.pin",
        riderUserName = "Teste",
        riderDescription = "Teste"
    )
}