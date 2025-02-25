package pl.mlynarczyk.pianoapp.menu

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.tasks.await
import pl.mlynarczyk.pianoapp.data.FirebaseStorageObject
import java.io.File
import java.io.FileOutputStream

@Composable
fun DirPicker(onResult: (Uri?) -> Unit) {
    val directoryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        onResult(uri)
    }

    LaunchedEffect(Unit) {
        directoryLauncher.launch(null)
    }
}

@Composable
fun FilePicker(onResult: (Uri?) -> Unit) {
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            onResult(uri)
        }
    }
    LaunchedEffect(Unit) {
        filePickerLauncher.launch(arrayOf("*/*"))
    }
}

fun getFilePathFromUri(context: Context, uri: Uri): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.MediaColumns.DATA)

    context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            filePath = cursor.getString(columnIndex)
        }
    }

    if (filePath == null) {
        // Alternatywna metoda: skopiowanie pliku do lokalnego katalogu
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, "temp_file")
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        filePath = tempFile.absolutePath
    }
    return filePath
}

suspend fun saveFromFirebaseToStorage(
    context: Context,
    storagePath: String,
    fBStoragePath: String = storagePath,
    fileNameWithExt: String,
    onSuccess: (file: File) -> Unit,
    onFailure: () -> Unit) {


    try {

        //  withTimeout(3000) {


        //.setConnectTimeout(5000)
        // Pobieranie pliku SVG z Firebase Storage
        var byteArray = FirebaseStorageObject.getStorageRef().child("$fBStoragePath/$fileNameWithExt")
            .getBytes(Long.MAX_VALUE).await()
       /*.addOnSuccessListener { byteArray ->*/

            // Zapisz pobrane dane (SVG) w pamięci wewnętrznej

            val dir = File(context.filesDir, storagePath)
            if (!dir.exists()) {
                dir.mkdirs()


            } else {
            }

            val file = File(dir, fileNameWithExt)
            FileOutputStream(file).use { outputStream ->
                outputStream.write(byteArray)
            }



            onSuccess(file)




    } catch (e: Exception) {

        onFailure()
        e.printStackTrace()
        return
    }
}