package org.app.marcelodev.comucarona.components.permissions


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isGranted.value) {
            Text("Adicione uma foto")
        } else {
            Text("Permissaão não autorizada.")
        }
    }
}