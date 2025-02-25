package pl.mlynarczyk.pianoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard


@Preview
@Composable
fun HomeScreen() {
    val menu = listOf(ScreensEnum.WARMUPS, ScreensEnum.PIECES, ScreensEnum.FUNFACTS)
    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(Modifier.fillMaxWidth()) {
                TopMenu()
                val userName = SettingsObject.userName
                Text("Witaj, ${userName}!", modifier = Modifier.padding(start = 20.dp))
                MainMenu(menu = menu)
            }
        }
    }

}

@Composable
fun MainMenu( menu : List<ScreensEnum>) {
    menu.forEach {
        Card(
            onClick = {
               //navController.navigate(it.screen)
                Navigation.goTo(it)
            },
            modifier = AppAppearance.cardModifier

            ) {
            RowInCard {
                Icon(imageVector = it.icon, contentDescription = it.namePl)
                Text(text = it.namePl)
            }
        }
    }
}

@Composable
fun TopMenu() {
    Spacer(modifier = Modifier.height(20.dp))
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)){
        IconButton(onClick = {Navigation.goTo(ScreensEnum.ACCOUNT)}){
            Icon(imageVector = ScreensEnum.ACCOUNT.icon, contentDescription = "Konto", Modifier.size(60.dp))
        }
        Text(text=ScreensEnum.HOME.namePl)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { Navigation.goTo(ScreensEnum.SETTINGS) }) {
            Icon(imageVector = ScreensEnum.SETTINGS.icon, contentDescription = ScreensEnum.SETTINGS.namePl,Modifier.size(60.dp))
        }
    }
}

