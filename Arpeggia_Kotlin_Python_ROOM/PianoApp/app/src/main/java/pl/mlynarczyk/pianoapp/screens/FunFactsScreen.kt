package pl.mlynarczyk.pianoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.OkDialog
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme

@Composable
fun FunFactsScreen() {

    var funFacts: List<FunFact> by remember { mutableStateOf<List<FunFact>>(emptyList()) }

    var isCollecting: Boolean by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var showDialogOk by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { //uruchamia korutyne tylko raz gdy kompozycja zostanie zbudowana
        coroutineScope.launch {
            Repo.get().getAllFunFacts().collect { newFunFacts ->
                funFacts = newFunFacts
                isCollecting = false
            }
        }
    }
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(title = ScreensEnum.FUNFACTS.namePl)
                if (isCollecting) {
                    LoadingIndicator()
                } else {
                    if (funFacts.isEmpty()) {
                        showDialogOk = true
                    }
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        if (funFacts.isNotEmpty()) {
                            items(funFacts) { funfact ->
                                FunFactCard(funfact)
                                //Spacer(modifier = Modifier.padding(15.dp))
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
fun FunFactCard(funFact: FunFact) {
    var favourite: Boolean by remember { mutableStateOf<Boolean>(funFact.favourite) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = AppAppearance.cardModifier,
        onClick = {
            Navigation.goTo(ScreensEnum.FUNFACT, funFact)
        }) {
        Column(modifier = Modifier.padding(30.dp)) {
            Text(text = funFact.title, textAlign = TextAlign.Center)
            Text(
                text =
                if (funFact.text.length > 20) {
                    funFact.text.take(50) + "..."
                } else {
                    funFact.text
                },
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    favourite = favourite.not()
                    val newFunFact = funFact.copy(favourite = favourite)
                    coroutineScope.launch {
                        Repo.get().update(newFunFact)
                    }
                }) {
                    Icon(
                        imageVector =
                        if (favourite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Ulubiona ciekawostka"
                    )
                }
                Text(
                    text = funFact.source,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


