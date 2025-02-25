package pl.mlynarczyk.pianoapp.menu

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Check
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DisplayNotes(notesFile: String="", context: Context) {
    var notesLoading by remember { mutableStateOf(true) }
    var notes: Pair<Int, String?> by remember { mutableStateOf(Pair(0, null)) }
    var notesClicked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.height(200.dp).clipToBounds().fillMaxWidth() ) {
        if (notesLoading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LoadingIndicator(Modifier.height(10.dp), Modifier.size(15.dp))
                Text("Ładowanie nut")
            }
            coroutineScope.launch {
                delay(3000)
                notes = getIdOrUrl("warmUps", notesFile, "svg", context)
                notesLoading = false
            }
        } else {
            if (notes.first != 0 || notes.second != null) {


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            notes.second ?: notes.first
                        )
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Obraz SVG z Firebase Storage",
                    modifier = Modifier.clickable(onClick = { notesClicked = true }),
                    contentScale = ContentScale.FillWidth
                )
                if (notes.first == 0) {
                    var notesDownloaded by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                if (downloadFromFbStorage()) {
                                    notesDownloaded = true
                                } else {
                                    //Toast
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector =
                            if (notesDownloaded) {
                                Icons.Default.Check
                            } else {
                                Icons.AutoMirrored.Filled.ExitToApp
                            },
                            contentDescription = null
                        )
                    }
                }

            } else {
                Row(Modifier.align(Alignment.Center)) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                    Text("Nie udało się załadować nut")
                }
            }
        }

        if (notesClicked) {
            Dialog(onDismissRequest = { notesClicked = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {

                ShowNotesDialog(notes)
                IconButton(
                    onClick = {notesClicked=false},
                    modifier = Modifier.align(Alignment.TopEnd)
                ){
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun ShowNotesDialog(notes : Pair<Int, String?>) {
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
                .data(
                    notes.second ?: notes.first
                )
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = "Obraz SVG z Firebase Storage",
            modifier = Modifier.fillMaxSize()

        )
    }
}