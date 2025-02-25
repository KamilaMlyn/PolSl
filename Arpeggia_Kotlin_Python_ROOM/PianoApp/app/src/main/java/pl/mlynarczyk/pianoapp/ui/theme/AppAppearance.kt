package pl.mlynarczyk.pianoapp.ui.theme

import android.content.Context
import android.inputmethodservice.Keyboard
import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.vectorResource
import pl.mlynarczyk.pianoapp.R
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.screens.SettingsScreen

object AppAppearance{
    val cardModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp) //odstep po bokach
        .wrapContentHeight()
        //.height(60.dp)
        .heightIn(min=60.dp)


   /* private val iconsPrimary : Color
        get() {
            return if (SettingsObject.darkTheme) Grey50
                    else Grey30
        }
    val iconsOnPrimary : Color
        get(){
            return if(SettingsObject.darkTheme)
                else
        }*/

}
@Composable
fun RowInCard(content: @Composable RowScope.() -> Unit) {
    Row(content = content,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically)
}
