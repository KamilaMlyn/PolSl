package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.Boy
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.DirPicker
import pl.mlynarczyk.pianoapp.menu.FilePicker
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.menu.YesNoDialog
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import java.io.File


@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun SettingsScreen() {
    var darkMode: Boolean by remember { mutableStateOf(SettingsObject.darkTheme) }
    var backupClicked: Boolean by remember { mutableStateOf(false) }
    var isEdited: Boolean by remember { mutableStateOf(false) }
    var makeBackup: Boolean by remember { mutableStateOf(false) }
    var restoreBackup: Boolean by remember { mutableStateOf(false) }
    var name: String by remember { mutableStateOf(SettingsObject.userName) }
    val context = LocalContext.current
    //val modeChange by remember{derivedStateOf {darkMode}}
    PianoAppTheme(darkTheme = darkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column() {
                TopBar(ScreensEnum.SETTINGS.namePl)
                Card(modifier = AppAppearance.cardModifier.align(alignment = Alignment.CenterHorizontally)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        //Spacer(modifier = Modifier.width(10.dp))
                        Switch(checked = darkMode, onCheckedChange = {
                            SettingsObject.changeTheme()
                            darkMode = !darkMode
                            },
                           /* colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary, // Kolor "kciuka" włączonego
                                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface, // Kolor "kciuka" wyłączonego
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), // Kolor tła włączonego
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) // Kolor tła wyłączonego
                            )*/
                        )
                        Text("Tryb jasny/ciemny")
                        }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp
                )
                Card(
                    modifier = AppAppearance.cardModifier,
                    onClick = { Navigation.goTo(ScreensEnum.NOTIFICATIONS) }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                        Text("Powiadomienia")
                    }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp
                )
                Card(
                    onClick = { backupClicked = backupClicked.not() },
                    modifier = AppAppearance.cardModifier.animateContentSize()
                ) {
                    Column(
                        //modifier = AppAppearance.cardModifier,
                       // horizontalAlignment = Alignment.CenterHorizontally,
                        //verticalArrangement = Arrangement.Center
                    ) {
                        Row( horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.ArrowDropDownCircle, contentDescription = null)
                                Text("Kopia zapasowa")

                        }
                        if (backupClicked) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Button(onClick = { makeBackup = true },modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.ArrowCircleDown, contentDescription = null )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text("Utwórz kopię")
                                    }
                                }
                                Button(onClick = { restoreBackup = true}, modifier = Modifier.weight(1f)) {
                                    Row() {
                                        Icon(imageVector = Icons.Default.ArrowCircleUp,contentDescription = null)
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text("Przywróć kopię")
                                    }
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp
                )
                Card(modifier = AppAppearance.cardModifier) {
                    Column(
                        //horizontalAlignment = Alignment.CenterHorizontally,
                        //modifier = Modifier.fillMaxSize()
                    ) {
                        Row( horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Boy, contentDescription = null)
                           // Spacer(modifier = Modifier.width(10.dp))
                            Text("Jak się do Ciebie zwracać?")

                        }
                        Row {

                            //Spacer(modifier = Modifier.weight(1f))
                            TextField(
                                value = name,
                                onValueChange = {
                                    name = it
                                    isEdited = true
                                }
                            )
                            if (isEdited) {
                                IconButton(onClick = {
                                    isEdited = false
                                    SettingsObject.userName = name
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null
                                    )
                                }
                                IconButton(onClick = {
                                    isEdited = false
                                    name = SettingsObject.userName
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                       Spacer(Modifier.height(16.dp))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                DeleteButton()
                Spacer(Modifier.height(16.dp))
                DeleteNotesButton(context)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
    if (makeBackup) {
        val coroutineScope = rememberCoroutineScope()
        DirPicker { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    SettingsObject.makeBackup(context, uri)
                }
            }
        }

    }
    if (restoreBackup) {
        val coroutineScope = rememberCoroutineScope()
        FilePicker { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    SettingsObject.restoreBackup(context,uri)
                }

            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DeleteNotesButton(context: Context) {
    var deleteData by remember { mutableStateOf(false) }
    var dialogResult by remember { mutableStateOf<Boolean?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {deleteData = true}, modifier = Modifier.fillMaxWidth()) {
        Text("Usuń zapisane w urządzeniu nuty")
    }
    if(deleteData){
        YesNoDialog(
            "Czy na pewno chcesz usunąć wszystkie nuty zapisane w pamięci urządzenia?",
            onClick = { result ->
                dialogResult = result
                deleteData = false
            }
        )
    }
    if(dialogResult == true){
        coroutineScope.launch {
            val warmUpsDir = File(context.filesDir,"warmUps")
            val piecesDir = File(context.filesDir,"pieces")
            if(warmUpsDir.exists() && warmUpsDir.listFiles()?.isNotEmpty() == true){
                for(file in warmUpsDir.listFiles()!!){
                    file.delete()
                }
            }
            if(piecesDir.exists() && piecesDir.listFiles()?.isNotEmpty() == true){
                for(file in piecesDir.listFiles()!!){
                    file.delete()
                }
            }

        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DeleteButton() {
    var deleteData by remember { mutableStateOf(false) }
    var dialogResult by remember { mutableStateOf<Boolean?>(null) }
    val coroutineScope = rememberCoroutineScope()
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {deleteData = true}
    ){
        Text("Usuń swoje analizy z aplikacji")
    }
    if(deleteData){
        YesNoDialog(
            "Czy na pewno chcesz usunąć wszystkie swoje analizy z aplikacji?",
            onClick = { result ->
                dialogResult = result
                deleteData = false
            }
        )
    }
    if(dialogResult == true){
        coroutineScope.launch {
            Repo.get().dropModelResultsTable()
        }
    }
}