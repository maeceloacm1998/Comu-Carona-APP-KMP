package org.app.marcelodev.comucarona

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform