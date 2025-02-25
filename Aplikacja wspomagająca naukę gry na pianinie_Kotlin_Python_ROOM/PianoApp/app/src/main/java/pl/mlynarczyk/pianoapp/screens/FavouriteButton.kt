package pl.mlynarczyk.pianoapp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Repo

@Composable
fun favButton(piece: Piece,modifier: Modifier = Modifier) : Boolean {
    val coroutineScope = rememberCoroutineScope()
    var fav : Boolean by remember { mutableStateOf(piece.favourite) }
    IconButton(onClick = {
        val newPiece = piece.copy(favourite = piece.favourite.not())
        coroutineScope.launch {
            Repo.get().update(newPiece)
            fav = newPiece.favourite
        }
    },
        modifier = modifier) {
        Icon(
            imageVector =
            if (fav) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "Ulubiony utwór"
        )
    }
    return piece.favourite.not()
}