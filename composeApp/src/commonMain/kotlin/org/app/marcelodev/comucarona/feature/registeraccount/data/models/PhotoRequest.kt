package org.app.marcelodev.comucarona.feature.registeraccount.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoRequest(
   @SerialName("uri") val uri: String
)