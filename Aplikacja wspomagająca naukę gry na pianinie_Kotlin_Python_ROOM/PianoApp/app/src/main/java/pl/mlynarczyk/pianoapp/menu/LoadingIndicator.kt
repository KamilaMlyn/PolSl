package pl.mlynarczyk.pianoapp.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoadingIndicator(
    boxModifier: Modifier = Modifier
        .wrapContentSize()
        .fillMaxSize()
        .wrapContentSize(Alignment.Center),
    indicatorModifier: Modifier = Modifier
        .size(50.dp)

) {
    Box(modifier = boxModifier
    ) {
        CircularProgressIndicator(
            modifier = indicatorModifier
        )
    }
}