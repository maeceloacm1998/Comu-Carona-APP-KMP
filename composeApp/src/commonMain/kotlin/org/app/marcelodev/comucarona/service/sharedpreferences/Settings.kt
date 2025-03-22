package org.app.marcelodev.comucarona.service.sharedpreferences

import com.russhwolf.settings.Settings

expect fun provideSettings(): Settings

fun pvdSettings(): Settings = provideSettings()