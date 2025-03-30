package org.app.marcelodev.comucarona.feature.carridedetails.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarRideDetails(
    @SerialName("id") val id: String,
    @SerialName("dateTitle") val dateTitle: String,
    @SerialName("description") val description: String,
    @SerialName("riderPhoto") var riderPhoto: String,
    @SerialName("riderUsername") val riderUsername: String,
    @SerialName("riderDescription") val riderDescription: String,
    @SerialName("waitingAddress") val waitingAddress: String,
    @SerialName("destinationAddress") val destinationAddress: String,
    @SerialName("waitingHour") val waitingHour: String,
    @SerialName("destinationHour") val destinationHour: String,
    @SerialName("isShowConfirmButton") val isShowConfirmButton: Boolean,
    @SerialName("finishRide") val finishRide: Boolean,
    @SerialName("isFullSeats") val isFullSeats: Boolean,
    @SerialName("existingReservation") val existingReservation: Boolean,
    @SerialName("reservations") val reservations: List<Reservation>,
    @SerialName("shareDeeplink") val shareDeeplink: String,
    @SerialName("bottomSheetCarRideUser") val bottomSheetCarRideUser: BottomSheetCarRideUser
)

@Serializable
data class Reservation(
    @SerialName("username") val username: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("photoUrl") val photoUrl: String
)

@Serializable
data class BottomSheetCarRideUser(
    @SerialName("bottomSheetRiderPlate") val bottomSheetRiderPlate: String,
    @SerialName("bottomSheetRiderUsername") val bottomSheetRiderUsername: String,
    @SerialName("bottomSheetRiderDescription") val bottomSheetRiderDescription: String,
    @SerialName("bottomSheetRiderPhoto") val bottomSheetRiderPhoto: String,
    @SerialName("bottomSheetCarRiderDescription") val bottomSheetCarRiderDescription: String,
    @SerialName("bottomSheetRiderPhoneNumber") val bottomSheetRiderPhoneNumber: String
)