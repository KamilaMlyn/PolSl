package pl.mlynarczyk.pianoapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp
import pl.mlynarczyk.pianoapp.screens.AccountScreen
import pl.mlynarczyk.pianoapp.screens.FunFactScreen
import pl.mlynarczyk.pianoapp.screens.FunFactsScreen
import pl.mlynarczyk.pianoapp.screens.HomeScreen
import pl.mlynarczyk.pianoapp.screens.NotificationsScreen
import pl.mlynarczyk.pianoapp.screens.PieceScreen
import pl.mlynarczyk.pianoapp.screens.PiecesScreen
import pl.mlynarczyk.pianoapp.screens.ScreensEnum
import pl.mlynarczyk.pianoapp.screens.SettingsScreen
import pl.mlynarczyk.pianoapp.screens.WarmUpScreen
import pl.mlynarczyk.pianoapp.screens.WarmUpsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Navigation {
    private var navController: NavHostController? = null

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun SetNavController(navContr: NavHostController) {
        navController = navContr

            NavHost(
                navController = navController!!,
                startDestination = ScreensEnum.HOME.screen
            ) {

                composable(ScreensEnum.HOME.screen) {

                    HomeScreen()
                }
                composable(ScreensEnum.PIECES.screen) {
                    PiecesScreen()
                }
                composable(ScreensEnum.PIECE.screen + "/{pieceId}/{comp}/{name}/{notes}/{fav}/{completed}/{version}/{description}/{sound}") {
                    val pieceId = it.arguments?.getString("pieceId")!!.toInt()
                    val comp = it.arguments?.getString("comp").toString()
                    val name = it.arguments?.getString("name").toString()
                    val sound = it.arguments?.getString("sound").toString()
                    val notes = it.arguments?.getString("notes")!!
                    val description = it.arguments?.getString("description")!!
                    val fav = it.arguments?.getString("fav").toBoolean()
                    val completed = it.arguments?.getString("completed").toBoolean()
                    val version = it.arguments?.getString("version")!!.toInt()

                    val piece = Piece(
                        pieceId,
                        version,
                        comp,
                        description,
                        name,
                        notes,
                        sound,
                        fav,
                        completed
                    )
                    PieceScreen(piece)
                }
                composable(ScreensEnum.WARMUPS.screen) {
                    WarmUpsScreen()
                }
                composable(ScreensEnum.WARMUP.screen + "/{warmUpId}/{name}/{notesPath}/{desc}/{soundPath}") {
                    val warmUpId = it.arguments?.getString("warmUpId")!!.toInt()
                    val name = it.arguments?.getString("name")!!
                    val notesPath =
                        URLDecoder.decode(it.arguments?.getString("notesPath")!!, "UTF-8")
                    val soundPath =
                        URLDecoder.decode(it.arguments?.getString("soundPath")!!, "UTF-8")
                    val desc = it.arguments?.getString("desc")!!
                    WarmUpScreen(warmUpId, name, notesPath, desc, soundPath)
                }
                composable(ScreensEnum.ACCOUNT.screen) {
                    AccountScreen()
                }
                composable(ScreensEnum.SETTINGS.screen) {
                    SettingsScreen()
                }
                composable(ScreensEnum.FUNFACTS.screen) {
                    FunFactsScreen()
                }
                composable(ScreensEnum.FUNFACT.screen + "/{id}/{title}/{text}/{source}/{favourite}/{version}") {
                    val id = it.arguments?.getString("id")!!.toInt()
                    val title = it.arguments?.getString("title")!!
                    val text = it.arguments?.getString("text")!!
                    val sourceURI = it.arguments?.getString("source")!!
                    val source = URLDecoder.decode(sourceURI, StandardCharsets.UTF_8.toString())
                    val favourite = it.arguments?.getString("favourite").toBoolean()
                    val version = it.arguments?.getString("version")!!.toInt()
                    FunFactScreen(id, title, text, source, favourite, version)
                }
                composable(ScreensEnum.NOTIFICATIONS.screen){
                    NotificationsScreen()
                }
            }


    }


    fun goTo(screenObj: ScreensEnum, obj: Any? = null) {

        try {
            navController?.navigate(
                screenObj.screen +
                        when (screenObj) {
                            ScreensEnum.PIECE -> {
                                val p: Piece = obj as Piece
                                "/${p.id}/${p.compositor}/${p.name}/${p.notesFile}/${p.favourite}/${p.completed}/${p.version}/${p.description}/${p.soundFile}"
                            }
                            ScreensEnum.WARMUP -> {
                                val wu: WarmUp = obj as WarmUp
                                "/${wu.id}/${wu.name}/${wu.notesFile}/${wu.description}/${wu.soundFile}"
                            }
                            ScreensEnum.FUNFACT -> {
                                val ff: FunFact = obj as FunFact
                                val source = URLEncoder.encode(ff.source, StandardCharsets.UTF_8.toString())
                                "/${ff.id}/${ff.title}/${ff.text}/${source}/${ff.favourite}/${ff.version}"
                            }
                            else -> ""
                        }
            )
        } catch (e: Exception) {
            Log.e("Error","Błąd przy przechodzeniu między ekranami")
            e.printStackTrace()
            //val context: Context = LocalContext.current
           // Toast.makeText(context, "Błąd w trakcie nawigacji", Toast.LENGTH_SHORT).show()
        }

    }
}

