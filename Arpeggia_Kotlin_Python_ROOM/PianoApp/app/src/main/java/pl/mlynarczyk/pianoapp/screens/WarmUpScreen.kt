package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.DisplayNotesV2
import pl.mlynarczyk.pianoapp.menu.FilePicker
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.SoundPlayer
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.menu.getFilePathFromUri
import pl.mlynarczyk.pianoapp.python.PythonRepo
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard
import java.io.File
import java.time.LocalDate

@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun WarmUpScreen(
    id: Int=0,
    name : String="name",
    notesFile: String="",
    description: String="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
    soundFile: String=""
) {
    val context: Context = LocalContext.current
    var analizeFile by remember { mutableStateOf<Boolean>(false) }
    var analizeDone by remember{ mutableStateOf(false) }
    var download by remember{ mutableStateOf(false) }
    var analysisMethodPanel by remember{ mutableStateOf(false) }
    var analizeRecording by remember{ mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(title = "Rozgrzewka: $name") { deleteFromTmp(context) }
                Column(modifier = Modifier.padding(16.dp)) {
                    Box {

                            DisplayNotesV2(notesFile, "warmUps", context) { download = true }
                        if(download) {
                            IconButton(
                                onClick = {
                                    downloadNotes(
                                    "warmUps",
                                    notesFile, context,
                                    {Toast.makeText(context,"Zapisano w pamięci",Toast.LENGTH_LONG).show()},
                                    {Toast.makeText(context,"Plik jest już zapisany w pamięci",Toast.LENGTH_LONG).show()}
                                    )
                                          },
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowCircleDown,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    SoundPlayer(soundFile, "warmUps",context)
                    Spacer(Modifier.height(15.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = description, textAlign = TextAlign.Center)
                    }
                    Spacer(Modifier.height(15.dp))
                    Button(onClick = {analizeFile = true; analizeDone = false/*analysisMethodPanel = true*/}, modifier = Modifier.fillMaxWidth()){
                        Text("Sprawdź emocjonalność wykonania!")
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 0.5.dp
                    )
                    AnalizePanel(id, false,analizeDone)
                    if(analizeFile){
                        OnAnalizeFileButtonClick(context, id,false) { analizeDone = true }
                    }
                }
                if(analysisMethodPanel){
                    Dialog(
                        onDismissRequest = { analysisMethodPanel = false},
                        properties = DialogProperties(usePlatformDefaultWidth = false),
                    ) {
                        Card(modifier = AppAppearance.cardModifier.padding(16.dp)) {
                            Column {
                                Button(
                                    onClick = { analizeFile = true; analizeDone = false },
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text("Dołącz nagranie z pliku")
                                }
                                Button(
                                    onClick = {
                                        analizeRecording = true
                                        analizeDone = false
                                              },
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text("Nagraj na żywo")
                                }
                                if(analizeRecording) {
                                    Spacer(Modifier.height(40.dp))
                                    AudioRecorderButton(context,id,false,false,null){ analizeDone = true }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    if(analizeDone){
        //analizeDone = analizeDone.not()
    }
}
@Suppress("DEPRECATION")
@Composable
fun AudioRecorderButton(context: Context,id:Int,table: Boolean,filePicking: Boolean,uri: Uri?,analizeDoneFunc: () -> Unit) {
    var isRecording by remember { mutableStateOf(false) }
    var analizeButtonClick by remember { mutableStateOf(false) }
    var recorder: MediaRecorder? = remember { null }
    val tmpDir = File(context.filesDir, "tmp")

    // Upewnij się, że katalog "tmp" istnieje
    if (!tmpDir.exists()) {
        tmpDir.mkdirs()
    }

    // Ścieżka pliku wynikowego
    val audioFile = File(tmpDir, "recorded_audio_${System.currentTimeMillis()}.wav")

    // Funkcja do rozpoczęcia nagrywania
    fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // Wybór formatu WAV nie jest bezpośredni w MediaRecorder
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile.absolutePath)

            try {
                prepare()
                start()
                isRecording = true
            } catch (e: Exception) {
                e.printStackTrace()
                isRecording = false
            }
        }
    }

    // Funkcja do zakończenia nagrywania
    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        isRecording = false
        analizeButtonClick=true
    }
    if(analizeButtonClick)
        OnAnalizeFileButtonClick(context,id,table,filePicking,uri,analizeDoneFunc)

    // UI
    Button(onClick = {
        if (isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }) {
        Text(if (isRecording) "Rozpocznij nagrywanie" else "Koniec nagrania")
    }
}
fun deleteFromTmp(context: Context) {
    val tmpDir = File(context.filesDir,"tmp")
    if(tmpDir.exists() && tmpDir.listFiles()?.isNotEmpty() == true) {
        for (file in tmpDir.listFiles()!!) {
            file.delete()
        }
    }
}

fun downloadNotes(table: String, notesFile: String,context: Context,onEnd: ()->Unit = {},onExists: ()->Unit={}) {
    val file = File(context.filesDir,"$table/$notesFile.svg")
    if(!file.exists()){
        val fileTmp = File(context.filesDir,"tmp/$notesFile.svg")
        fileTmp.copyTo(file)
        onEnd()
    }
    else{
        onExists()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnalizePanel(id: Int, table: Boolean,recomp: Boolean){
    var newResults by remember { mutableStateOf(false) }
    var resultsList by remember { mutableStateOf(listOf<ModelResult>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recomp) {

        coroutineScope.launch {

            val tmp = Repo.get().getModelResultsAbout(id, table).firstOrNull()
            if (tmp?.isNotEmpty() == true) {
                resultsList = Repo.get().getModelResultsAbout(id, table).first()
                newResults = true
                /* Repo.get().getModelResultsAbout(id, table).collect{
                resultsList = it

                newResults = true
                return@collect
            }*/
            } else {
                newResults = true
            }

        }
    }

    if(newResults) {
        if(resultsList.isEmpty()){

            Card(modifier = AppAppearance.cardModifier) {
                RowInCard {
                    Text("Brak analiz")
                }
            }
        }
        else {

            LazyColumn {
                items(resultsList) { result ->
                    ResultCard(result)
                }
            }
        }
    }
    else{
            LoadingIndicator()
    }
}

@Composable
fun ResultCard(result: ModelResult) {
    var cardInfo by remember { mutableStateOf(false) }
   Card(modifier = AppAppearance.cardModifier) {
        RowInCard {
            Text(result.date)
            Row(modifier = Modifier.weight(0.5f)) {
                for (i in 1..result.getEmotionality()) {
                    Icon(imageVector = Icons.Default.StarRate, contentDescription = null)

                }
                for (i in result.getEmotionality()+1 ..4) {

                    Icon(imageVector = Icons.Outlined.StarOutline,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            IconButton(onClick = {cardInfo = cardInfo.not()}) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
        }
    }
    if(cardInfo) {
        Row {
            Spacer(modifier = Modifier.width(40.dp))
            Card(modifier = AppAppearance.cardModifier) {
                Row {
                    Row(modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.StarRate, contentDescription = null)
                    }
                    Text((result.Q1*100).toString()+"%", modifier = Modifier.weight(1f))
                }//
                Row {
                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                    }
                    Text((result.Q2*100).toString()+"%", modifier = Modifier.weight(1f))
                }
                Row {
                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                    }
                    Text((result.Q3*100).toString()+"%", modifier = Modifier.weight(1f))
                }
                Row {
                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                        Icon(
                            Icons.Default.StarRate,
                            contentDescription = null
                        )
                    }
                    Text((result.Q4*100).toString()+"%", modifier = Modifier.weight(1f))
                }
            }
            IconButton(onClick = { cardInfo = false }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OnAnalizeFileButtonClick(context: Context,
                             id: Int, table: Boolean,
                             filePicking : Boolean = true, uri: Uri?=null,
                             analizeDoneFunc: ()->Unit) {
    var loading by remember { mutableStateOf(false) }
    var notWav by remember { mutableStateOf(false) }
    var filePicked by remember { mutableStateOf(false) }
    var fileURI by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val modelPath = copyAssetToInternalStorage(context, "model_emotions.tflite")
    if (filePicking) {
        FilePicker { uri ->
            filePicked = true
            fileURI = uri
        }
    }else{
        fileURI = uri
        filePicked = true
    }

    if (filePicked) {
        if (fileURI != null) {
            coroutineScope.launch(Dispatchers.IO) {
                val path = getFilePathFromUri(context, fileURI!!).toString()
                val contentResolver = context.contentResolver
                val mimeType = contentResolver.getType(fileURI!!)
                val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                if (ext != "wav") {
                    notWav = true

                } else {
                    loading = true
                    val date = LocalDate.now()
                    PythonRepo.get_emotions(path, modelPath, date, table, id)
                }
                loading = false
                analizeDoneFunc()
            }
        }
    }
    if (loading) {
        Card(modifier = AppAppearance.cardModifier) {
            RowInCard {
                LoadingIndicator(boxModifier = Modifier.size(20.dp))
            }
        }
    }
    if(notWav){
        Toast.makeText(context, "Plik musi być plikiem .wav", Toast.LENGTH_LONG)
            .show()
        notWav = false
    }
}
fun copyAssetToInternalStorage(context: Context, assetFileName: String): String {
    val file = File(context.filesDir, assetFileName)
    if (!file.exists()) {
        context.assets.open(assetFileName).use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
    return file.absolutePath
}








