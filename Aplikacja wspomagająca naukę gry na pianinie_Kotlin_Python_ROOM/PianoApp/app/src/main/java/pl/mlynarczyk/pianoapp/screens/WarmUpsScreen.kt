package pl.mlynarczyk.pianoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.OkDialog
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard

@Preview
@Composable
fun WarmUpsScreen() {
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var dialogOkResult by remember { mutableStateOf(false) }
            var showDialogOk by remember { mutableStateOf(false) }
            var warmUps: List<WarmUp> by remember { mutableStateOf<List<WarmUp>>(emptyList()) }
            var isCollecting: Boolean by remember { mutableStateOf(true) }
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(Unit) { //uruchamia korutyne tylko raz gdy kompozycja zostanie zbudowana
                coroutineScope.launch {
                    Repo.get().getAllWarmUps().collect { newWarmUps ->
                        warmUps = newWarmUps
                        isCollecting = false
                    }
                }
            }
            Column {
                TopBar(title = ScreensEnum.WARMUPS.namePl)
                if (isCollecting) {
                    LoadingIndicator()
                } else {
                    if (warmUps.isEmpty()) {
                        showDialogOk = true


                    }
                    else {
                        LazyColumn() {
                            items(warmUps) { warmUp ->
                                WarmUpCard(warmUp)
                            }
                        }
                    }
                }
            }
            if(showDialogOk){
                OkDialog(
                    "Brak rozgrzewek - sprawdź swoje połączenie z internetem",
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
fun WarmUpCard(warmUp: WarmUp=WarmUp(null,0,name="name","","descriptionnnnnnnnnnnnnnnnn","")) {

    Card(
        onClick = {
            Navigation.goTo(ScreensEnum.WARMUP,warmUp)
        },
        modifier = AppAppearance.cardModifier/*Modifier
            .padding(16.dp)
            .height(60.dp)
            .wrapContentWidth(),*/
    ) {
        RowInCard {
            Icon(ScreensEnum.WARMUPS.icon,contentDescription = null)
            Text(text = warmUp.name)
            Text(text = warmUp.description.take(20)+"...")
        }
    }

}
