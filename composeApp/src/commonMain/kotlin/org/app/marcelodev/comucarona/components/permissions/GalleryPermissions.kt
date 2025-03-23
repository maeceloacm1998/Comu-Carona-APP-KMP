package org.app.marcelodev.comucarona.components.permissions


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
//
//@Composable
//fun RequestGalleryPermission(
//    onPermissionGranted: () -> Unit,
//    onPermissionDenied: () -> Unit
//) {
//    val permissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
//    var permissionRequested by remember { mutableStateOf(false) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            onPermissionGranted()
//        } else {
//            onPermissionDenied()
//        }
//    }
//
//    // Trigger permission check when the Composable initializes
//    LaunchedEffect(Unit) {
//        if (!permissionState.status.isGranted && !permissionRequested) {
//            permissionRequested = true
//            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
//    }
//
//    Column {
//        if (permissionState.status.isGranted) {
//            Text("Adicione uma foto")
//        } else {
//            if (permissionState.status.shouldShowRationale) {
//                Text("Permission is required to access the gallery. Please grant the permission.")
//            } else {
//                Text("Permission is required to access the gallery.")
//            }
//
//            Button(onClick = { launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
//                Text("Request Permission")
//            }
//        }
//    }
//}