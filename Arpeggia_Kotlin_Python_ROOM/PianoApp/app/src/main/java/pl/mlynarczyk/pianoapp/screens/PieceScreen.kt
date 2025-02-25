package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.DisplayNotesV2
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.SoundPlayer
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PieceScreen(
    piece: Piece
) {
    val context: Context = LocalContext.current
    var analize by remember { mutableStateOf<Boolean>(false) }
    var analizeDone by remember{ mutableStateOf(false) }
    var updatePiece  by remember { mutableStateOf(false) }
    var completed = remember { mutableStateOf(piece.completed) }
    var download by remember { mutableStateOf(false) }
    var downloadButton by remember { mutableStateOf(false) }
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(title = "${piece.name} - ${piece.compositor}"){ deleteFromTmp(context) }
                Column(modifier = Modifier.padding(16.dp)) {
                    Box {
                        DisplayNotesV2(piece.notesFile,"pieces", context) { downloadButton = true }
                        Column(modifier = Modifier.align(Alignment.TopEnd)) {
                            favButton(piece, Modifier)
                            Checkbox(
                                checked = completed.value,
                                onCheckedChange = {
                                    completed.value = completed.value.not()
                                    updatePiece = true
                                }
                            )
                            if(downloadButton) {
                                IconButton(onClick = { downloadNotes(
                                    "pieces",
                                    piece.notesFile, context,
                                    { Toast.makeText(context,"Zapisano w pamięci", Toast.LENGTH_LONG).show()},
                                    { Toast.makeText(context,"Plik jest już zapisany w pamięci",Toast.LENGTH_LONG).show()}
                                ) }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowCircleDown,
                                        contentDescription = null
                                    )
                                }
                            }

                        }
                    }
                    SoundPlayer(piece.soundFile,"pieces", context)
                    Spacer(Modifier.height(15.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(piece.description, textAlign = TextAlign.Center)
                    }
                    Spacer(Modifier.height(15.dp))

                    Button(
                        onClick = { analize = true; analizeDone = false },
                        Modifier.fillMaxWidth()
                    ) {
                        Text("Dołącz nagranie i sprawdź emocjonalność wykonania!")
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 0.5.dp
                    )
                    AnalizePanel(piece.id!!, true, analizeDone)
                    if(analize){
                        OnAnalizeFileButtonClick(context, piece.id,true) { analizeDone = true }
                    }
                }
            }
        }
    }

    if(updatePiece){
        piece.completed = completed.value
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            Repo.get().update(piece)

        }
        updatePiece = false
    }
    if(download){

    }
}

