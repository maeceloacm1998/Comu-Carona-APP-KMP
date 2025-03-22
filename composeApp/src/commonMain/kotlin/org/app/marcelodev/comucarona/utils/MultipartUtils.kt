package org.app.marcelodev.comucarona.utils

object MultipartUtils {

//    /**
//     * Create a MultipartBody.Part from a file path and a part name to be used in a Multipart request
//     * @param uri the Uri of the file to be uploaded
//     * @param partName the name of the part in the Multipart request
//     * @return a MultipartBody.Part object
//     */
//    fun getFilePartFromUri(context: Context, uri: Uri, partName: String): MultipartBody.Part {
//        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//        val tempFile = File.createTempFile("upload", ".tmp", context.cacheDir)
//        val outputStream = FileOutputStream(tempFile)
//
//        inputStream?.use { input ->
//            outputStream.use { output ->
//                input.copyTo(output)
//            }
//        }
//
//        val requestFile = RequestBody.create(
//            context.contentResolver.getType(uri)?.toMediaTypeOrNull(),
//            tempFile
//        )
//
//        return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
//    }
}