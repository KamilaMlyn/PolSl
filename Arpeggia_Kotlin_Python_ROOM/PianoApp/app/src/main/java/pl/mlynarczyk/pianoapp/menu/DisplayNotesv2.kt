package pl.mlynarczyk.pianoapp.menu

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DisplayNotesV2(notesFile: String="",table: String, context: Context,onSuccessDisplay: ()->Unit={}) {
    var notesLoading by remember { mutableStateOf(true) }
    var notes: String?  by remember { mutableStateOf<String?>(null) }
    var notesClicked by remember { mutableStateOf(false) }
    var okDialog by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()


    Box(modifier = Modifier.height(200.dp).clipToBounds().fillMaxWidth() ) {
        if (notesLoading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LoadingIndicator(Modifier.height(10.dp), Modifier.size(15.dp))
                Text("Ładowanie nut")
            }
            coroutineScope.launch {
                delay(3000)
                notes = getNotesv2(table, "$notesFile.svg", context)
                notesLoading = false
            }
        } else {

            if(notes==null){
                Row(Modifier.align(Alignment.Center)) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                    Text("Nie udało się załadować nut")
                    if (okDialog) {
                        OkDialog("Nie udało się załadować zawartości - sprawdź swoje połączenie z internetem") {okDialog = false }
                    }
                }
            }
            else{
                val fileUri = Uri.fromFile(File(notes!!))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(fileUri)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Obraz SVG z Firebase Storage",
                    modifier = Modifier.clickable(onClick = { notesClicked = true }).fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                onSuccessDisplay()
            }
        }
        if (notesClicked) {
            Dialog(
                onDismissRequest = { notesClicked = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {

                notes?.let { ShowNotesDialog(it) }
                IconButton(
                    onClick = { notesClicked = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    }

}

@SuppressLint("SuspiciousIndentation")
suspend fun getNotesv2(table: String, fileNameWithExt: String, context: Context): String? {
    var file: File? = File(context.filesDir, "$table/$fileNameWithExt")
    var fileTmp = File(context.filesDir, "tmp/$fileNameWithExt")
    var fileFormFb: File? = null
    val tmpDir = File(context.filesDir, "tmp")

    if (file!!.exists()) {
        return file.absolutePath
    } else if (fileTmp.exists()) {
        return fileTmp.absolutePath
    } else if (isInternetAvailable(context)) {


        saveFromFirebaseToStorage(
            context,
            storagePath = "tmp",
            fBStoragePath = table,
            fileNameWithExt = fileNameWithExt,
            onSuccess = { fileFromStorage ->
                fileFormFb = fileFromStorage
            },
            onFailure = {
                fileFormFb = null
            }
        )
        return fileFormFb?.absolutePath
    } else {
        return null
    }

}


suspend fun isInternetAvailable(context: Context): Boolean {
    //val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //val network = connectivityManager.activeNetwork ?: return false
    //val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
   // val a = connectivityManager.getLinkProperties(network)
    return withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec("ping -c 1 google.com")
            val result = process.waitFor()
            result == 1
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}

@Composable
fun ShowNotesDialog(notes : String) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) } //dla przesuwania
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
        .fillMaxHeight()
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale *= zoom
                scale = scale.coerceIn(1f, 5f) //limit skalowania (1x - 5x)
                offset = Offset(offset.x + pan.x, offset.y + pan.y)
            }
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = offset.x,
            translationY = offset.y
        )) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(notes)
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = "Obraz SVG z Firebase Storage",
            modifier = Modifier.fillMaxSize()

        )
    }
}
