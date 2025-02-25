package pl.mlynarczyk.pianoapp.menu


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.screens.ScreensEnum

@Composable
fun TopBar(title: String,onClick : ()->Unit ={}) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(onClick = {
                onClick()
                Navigation.goTo(ScreensEnum.HOME)
            }) {
                Icon(
                    imageVector = ScreensEnum.HOME.icon,
                    contentDescription = ScreensEnum.HOME.namePl
                )
            }
            Text(text = title)
        }
    }

}
