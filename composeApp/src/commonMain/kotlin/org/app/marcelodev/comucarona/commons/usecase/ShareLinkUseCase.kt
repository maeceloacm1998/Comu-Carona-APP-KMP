package org.app.marcelodev.comucarona.commons.usecase

import org.app.marcelodev.comucarona.commons.utils.ShareUtils

class ShareLinkUseCase {
    operator fun invoke(link: String, onErrorAction: (message: String) -> Unit = {}) {
        val callShare = ShareUtils.create()
        callShare.handleShare(link, onErrorAction)
    }

    companion object {
        private const val ERROR_MESSAGE = "Erro ao tentar abrir o share."
    }
}