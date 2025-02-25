package pl.mlynarczyk.pianoapp.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance

@Composable
fun YesNoDialog(text: String, onClick : (Boolean) -> Unit) {


        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Card(modifier = AppAppearance.cardModifier.padding(16.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = {
                                onClick(true)
                            },
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text("Tak")

                        }
                        Button(
                            onClick = {
                                onClick(false)
                            },
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f)

                        ) {

                            Text("Nie")
                        }
                    }
                }
            }
        }

    }
@Composable
fun OkDialog(text: String, onClick : (Boolean) -> Unit){
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(modifier = AppAppearance.cardModifier.padding(16.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onClick(true)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ok")
                }
            }
        }
    }
}