package org.app.marcelodev.comucarona.commons.usecase

class CallPhoneUseCase() {
    // TODO IMPLEMENTAR NO ANDROID E IOS
//    operator fun invoke(
//        phoneNumber: String, onErrorAction: (message: String) -> Unit = {}
//    ) {
//        openDialer(
//            context = context,
//            phoneNumber = phoneNumber,
//            onErrorAction = onErrorAction
//        )
//    }
//
//    private fun openDialer(
//        context: Context,
//        phoneNumber: String,
//        onErrorAction: (message: String) -> Unit = {}
//    ) {
//        val intent = Intent(Intent.ACTION_DIAL).apply {
//            data = Uri.parse(phoneNumber)
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//        if (intent.resolveActivity(context.packageManager) != null) {
//            context.startActivity(intent)
//        } else {
//            onErrorAction(ERROR_MESSAGE)
//        }
//    }
//
//    companion object {
//        private const val ERROR_MESSAGE = "Erro ao tentar abrir o discador."
//    }
}