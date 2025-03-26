package org.app.marcelodev.comucarona.components.permissions


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.gallery.GALLERY
import kotlinx.coroutines.launch

@Composable
fun RequestGalleryPermission() {
    val coroutineScope = rememberCoroutineScope()
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }

    val isGranted = remember { mutableStateOf(false) }

    fun onPermissionResult() {
        coroutineScope.launch {
            try {
                controller.providePermission(Permission.GALLERY)
                isGranted.value = true
            } catch(denied: DeniedException) {
                isGranted.value = false
            }
        }
    }

    LaunchedEffect(Unit) {
        onPermissionResult()
    }

    BindEffect(controller)

    Column {
        if (isGranted.value) {
            Text("Adicione uma foto")
        } else {
            Text("Permission is required to access the gallery. Please grant the permission.")

            Button(onClick = {
                onPermissionResult()
            }) {
                Text("Request Permission")
            }
        }
    }
}