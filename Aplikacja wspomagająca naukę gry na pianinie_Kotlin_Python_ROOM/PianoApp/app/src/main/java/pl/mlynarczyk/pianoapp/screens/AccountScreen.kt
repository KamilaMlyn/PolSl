package pl.mlynarczyk.pianoapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import pl.mlynarczyk.pianoapp.Navigation
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp
import pl.mlynarczyk.pianoapp.data.Repo
import pl.mlynarczyk.pianoapp.menu.LoadingIndicator
import pl.mlynarczyk.pianoapp.menu.SettingsObject
import pl.mlynarczyk.pianoapp.menu.TopBar
import pl.mlynarczyk.pianoapp.ui.theme.AppAppearance
import pl.mlynarczyk.pianoapp.ui.theme.PianoAppTheme
import pl.mlynarczyk.pianoapp.ui.theme.RowInCard

@Composable
fun AccountScreen() {

    PianoAppTheme(darkTheme = SettingsObject.darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column {
                TopBar(ScreensEnum.ACCOUNT.namePl)
                FavCard(
                    screen = ScreensEnum.PIECES,
                    getItems = {Repo.get().getFavouritePieces()},
                    showItemsCards = {pieces->ShowFavPieces(pieces)}
                )
                FavCard(
                    screen = ScreensEnum.FUNFACTS,
                    getItems = {Repo.get().getFavouriteFunFacts()},
                    showItemsCards = {funFacts-> ShowFavFunFacts(funFacts) }
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                AnalysisCard(
                    true,
                    "utworów"
                )
                AnalysisCard(
                    false,
                    "rozgrzewek"
                )


            }

        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnalysisCard(table: Boolean, text: String) {
    var clicked: Boolean by remember { mutableStateOf(false) }
    var downloaded: Boolean by remember { mutableStateOf(false) }
    var show: Boolean by remember { mutableStateOf(false) }
    var ids: List<Int> by remember { mutableStateOf(emptyList()) }
    //var itemsList: List<ModelResult> by remember {mutableStateOf(emptyList())}
    //val itemsList = remember { mutableStateListOf<ModelResult>() }
    //val coroutineScope = rememberCoroutineScope()
    var piecesList = remember{mutableListOf<Piece>()}
    var warmUpsList = remember {mutableListOf<WarmUp>()}
    var idFKList: List<Int>?
Column {
    Card(modifier = AppAppearance.cardModifier.heightIn(max = 60.dp),
        onClick = {
            clicked = clicked.not()
        }
    ) {
        RowInCard {
            Icon(imageVector = Icons.Default.Stars, contentDescription = null)
            Text(text = "Analizy emocjonalności $text", modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            if (clicked) {
                if (!downloaded) {
                    LoadingIndicator(Modifier.height(10.dp), Modifier.size(15.dp))

                    val coroutineScope = rememberCoroutineScope()

                    coroutineScope.launch {
                        idFKList = Repo.get().getFkFrom(table).firstOrNull()
                        if (idFKList?.isNotEmpty() == true) {
                            ids = idFKList as List<Int>
                        } else {
                            downloaded = true
                            show = true
                            return@launch
                        }
                        if (table) {

                            ids.forEach { id_FK ->
                                val piece = Repo.get().getPieceById(id_FK)
                                piecesList.add(piece!!)
                            }

                        } else {
                            ids.forEach { id_FK ->
                                val warmUp = Repo.get().getWarmUpById(id_FK)
                                warmUpsList.add(warmUp!!)
                            }

                        }
                        downloaded = true
                        show = true
                    }


                } else {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "")
                    show = true

                }
            } else {
                show = false
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }

        }
    }
    if (show) {

        if (piecesList.isEmpty() && warmUpsList.isEmpty()) {
            Card(modifier = AppAppearance.cardModifier) {
                RowInCard {
                    Spacer(modifier = Modifier.width(40.dp))
                    Text("Brak analiz :<")
                }
            }
        } else {
            for (p in piecesList) {
                Row {
                    Spacer(modifier = Modifier.width(40.dp))
                    PieceAnalysisCard(p)
                }
            }
            for (w in warmUpsList) {

                Row {
                    Spacer(modifier = Modifier.width(40.dp))
                    WarmUpAnalysisCard(w)
                }
            }
        }
    }
    }
}

@Composable
fun WarmUpAnalysisCard(warmUp: WarmUp) {
    val table = false
    var clicked by remember { mutableStateOf(false) }
    var moveToWarmUp by remember { mutableStateOf(false) }
    Column {
        Card(
            modifier = AppAppearance.cardModifier,
            onClick = { moveToWarmUp = true }
        ) {
            RowInCard {
                Text(warmUp.name)
                Spacer(modifier = Modifier.weight(1f))
                VerticalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp,
                    modifier = Modifier.height(20.dp))
                IconButton(onClick = {clicked = clicked.not()}) {
                    if (clicked) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "")
                    } else {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    }
                }

            }


        }
        if (clicked) {
            Row {
                Spacer(Modifier.width(16.dp))
                AnalizePanel(warmUp.id!!, table,true)
            }
        }
        if(moveToWarmUp){
            Navigation.goTo(ScreensEnum.WARMUP,warmUp)
        }
    }
}

@Composable
fun PieceAnalysisCard(piece: Piece) {
    val table = true
    var clicked by remember { mutableStateOf(false) }
    var moveToWarmUp by remember { mutableStateOf(false) }
    Column {
        Card(
            modifier = AppAppearance.cardModifier,
            onClick = { moveToWarmUp = true }
        ) {
            RowInCard {
                Text(piece.name + " - " + piece.compositor)
                Spacer(modifier = Modifier.weight(1f))
                VerticalDivider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 0.5.dp,
                    modifier = Modifier.height(20.dp))
                IconButton(onClick = {clicked = clicked.not()}) {
                    if (clicked) {
                        Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "")
                    } else {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                    }
                }

            }


        }
        if (clicked) {
            Row {
                Spacer(Modifier.width(16.dp))
                AnalizePanel(piece.id!!, table,false)
            }
        }
        if(moveToWarmUp){
            Navigation.goTo(ScreensEnum.PIECE,piece)
        }
    }
}


@OptIn(FlowPreview::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavCard(screen: ScreensEnum, getItems: ()-> Flow<List<Any>>, showItemsCards:@Composable (List<Any>) -> Unit) {
    var clicked: Boolean by remember { mutableStateOf(false) }
    var downloaded : Boolean by remember { mutableStateOf(false) }
    var show : Boolean by remember { mutableStateOf(false) }
    var itemsList: List<Any> by remember {mutableStateOf<List<Any>>(emptyList())}
    //val coroutineScope = rememberCoroutineScope()

    Card(modifier = AppAppearance.cardModifier.heightIn(max = 60.dp),
        onClick = {
            clicked = clicked.not()
        }
    ) {
        RowInCard {
            Icon(imageVector = screen.icon, contentDescription = null)
            Text(text = "Ulubione " + screen.namePl, modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.weight(1f))
            if(clicked){
                if(!downloaded) {
                    LoadingIndicator(Modifier.height(10.dp), Modifier.size(15.dp))

                    val coroutineScope = rememberCoroutineScope()

                    coroutineScope.launch {

                        var tmpItemsList: List<Any>? = null
                        tmpItemsList = getItems().firstOrNull()


                        if (tmpItemsList?.isNotEmpty() == true) {

                            getItems().collect { items ->

                                itemsList = items
                                show = true
                                downloaded = true
                            }

                        }

                        downloaded = true
                        show = true
                    }


                }
                else {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = "")
                    show = true

                }
            }
            else{
                show = false
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }

        }
    }
    if(show){
        Row {
            Spacer(modifier = Modifier.width(40.dp))
            if (itemsList.isEmpty()) {
                Card(modifier = AppAppearance.cardModifier) {
                    RowInCard {
                        Text("Brak ulubionych :<")
                    }
                }
            } else {
                showItemsCards(itemsList)
            }
        }
    }


}

@Composable
fun ShowFavPieces(pieces: List<Any>) {
    LazyColumn {
        items(pieces){ piece ->
            piece as Piece
            Row {
               // Spacer(modifier = Modifier.width(40.dp))
                Card(modifier = AppAppearance.cardModifier,
                    onClick = {
                        Navigation.goTo(ScreensEnum.PIECE,piece)
                }
                ) {
                    RowInCard{
                        val coroutineScope = rememberCoroutineScope()
                        Text(text = piece.name + " - " + piece.compositor)
                        var favourite : Boolean by remember { mutableStateOf<Boolean>(true) }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            favourite = favourite.not()
                            val newPiece = piece.copy(favourite = favourite)
                            coroutineScope.launch {
                                Repo.get().update(newPiece)
                            }
                        }) {
                            Icon(
                                imageVector =
                                if (favourite) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                                contentDescription = "Ulubiony utwór"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowFavFunFacts(funFacts: List<Any>) {
    LazyColumn {
        items(funFacts) { funFact ->
            funFact as FunFact
            Row {
               // Spacer(modifier = Modifier.width(40.dp))
                Card(onClick = {
                    Navigation.goTo(ScreensEnum.FUNFACT,funFact)

                },
                    modifier = AppAppearance.cardModifier) {
                    RowInCard {
                        val coroutineScope = rememberCoroutineScope()
                        Text(text = funFact.title)
                        var favourite: Boolean by remember { mutableStateOf<Boolean>(true) }
                        Spacer(Modifier.weight(1f))
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
                    }
                }
            }
        }
    }
}