package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.OkDialog
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PiecesScreen() {
    var pieces: List<Piece> by remember { mutableStateOf<List<Piece>>(emptyList()) }

    var isCollecting: Boolean by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var showDialogOk by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { //uruchamia korutyne tylko raz gdy kompozycja zostanie zbudowana
        coroutineScope.launch {
            Repo.get().getAllPieces().collect { newPieces ->
                pieces = newPieces
                isCollecting = false
            }
        }
    }
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                TopBar(title = ScreensEnum.PIECES.namePl)
                Column {
                    if (isCollecting) {
                        LoadingIndicator()
                    } else {
                        if (pieces.isEmpty()) {
                            showDialogOk = true
                        } else {
                            LazyColumn() {
                                items(pieces) { piece ->
                                    PieceCard(piece)
                                }
                            }
                        }
                    }
                }
            }
            if(showDialogOk){
                OkDialog(
                    "Brak utworów - sprawdź swoje połączenie z internetem",
                    onClick = {
                        showDialogOk = false
                        Navigation.goTo(ScreensEnum.HOME)
                    }
                )
            }
        }
    }
}

@Composable
fun PieceCard(piece : Piece){
    Card(
        onClick = {
            Navigation.goTo(ScreensEnum.PIECE,piece)
        },
        AppAppearance.cardModifier
    ){
        RowInCard{
            Icon(imageVector = ScreensEnum.PIECES.icon, contentDescription = null)
            Text(text = piece.name + " - " + piece.compositor)
            Spacer(modifier = Modifier.weight(1f))
            favButton(piece,Modifier)
            Checkbox(
                checked = piece.completed,
                enabled = false,
                onCheckedChange = {}
            )
/*  Text(text =
  if(pi.completed)
     "Ukończone"
 else
     "Nieukończone"
 )
 favButton(piece)*/
}

}
}
