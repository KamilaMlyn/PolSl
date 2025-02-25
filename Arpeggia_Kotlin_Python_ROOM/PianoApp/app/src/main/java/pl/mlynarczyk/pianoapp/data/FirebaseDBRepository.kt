package pl.mlynarczyk.pianoapp.data

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.Setting
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp
import pl.mlynarczyk.pianoapp.menu.MessagesObject


suspend fun updateWarmUpsTable(context: Context, firebaseDB: FirebaseDatabase) {
    if (MessagesObject.checkInternetConnection(context)) {
        val warmUpsRef = firebaseDB.getReference("warmUpsTable")

        withContext(Dispatchers.IO) {
            try {
                val snapshot = warmUpsRef.get()
                    .await() //pobranie ciekawostek z referencji BD Firebase i zawiesza watek do konca pobrania (await)
                val newWarmUpsList =
                    mutableListOf<WarmUp>() //lista ciekawostek do dodania do lokalnej BD
                for (dataSnapshot in snapshot.children) { //iteracja po ciekawostkach
                    val id = dataSnapshot.child("id")
                        .getValue(Int::class.java) //id ciekawostki z Firebase DB
                    if (id != null) {
                        if (Repo.get().warmUpExistsById(id)) { //jesli id istnieje w lokalnej BD
                            val version = dataSnapshot.child("version")
                                .getValue(Int::class.java) //lastUpdate ciekawostki z Firebase DB
                            if (Repo.get()
                                    .getVersionWarmUpById(id) != version
                            ) { //jesli w lokalnej bazie ciekawostka jest przestarzala
                                val updateWarmUp =
                                    dataSnapshot.getValue(WarmUp::class.java) //obiekt z ciekawostki z Firebase DB
                                if (updateWarmUp != null) {
                                    Repo.get().update(updateWarmUp)
                                }
                            }
                        } else { //jesli ciekawostka o podanym id nie istnieje w lokalnej BD
                            val newWarmUp =
                                dataSnapshot.getValue(WarmUp::class.java) //obiekt z ciekawostki z Firebase DB
                            if (newWarmUp != null) {
                                newWarmUpsList.add(newWarmUp)
                            }
                        }
                    }
                }
                if (newWarmUpsList.isNotEmpty()) {
                    Repo.get().insertWarmUps(newWarmUpsList)
                }
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
suspend fun updatePiecesTable(context: Context,firebaseDB: FirebaseDatabase) {
    if (MessagesObject.checkInternetConnection(context)) {
        val piecesRef = firebaseDB.getReference("piecesTable")
        withContext(Dispatchers.IO) {
            try {
                val snapshot = piecesRef.get()
                    .await() //pobranie ciekawostek z referencji BD Firebase i zawiesza watek do konca pobrania (await)
                val newPiecesList =
                    mutableListOf<Piece>() //lista ciekawostek do dodania do lokalnej BD
                for (dataSnapshot in snapshot.children) { //iteracja po ciekawostkach
                    val id = dataSnapshot.child("id")
                        .getValue(Int::class.java) //id ciekawostki z Firebase DB
                    if (id != null) {
                        if (Repo.get().pieceExistsById(id)) { //jesli id istnieje w lokalnej BD
                            val version = dataSnapshot.child("version")
                                .getValue(Int::class.java) //lastUpdate ciekawostki z Firebase DB
                            if (Repo.get()
                                    .getVersionPieceById(id) != version
                            ) { //jesli w lokalnej bazie ciekawostka jest przestarzala
                                val updatePiece =
                                    dataSnapshot.getValue(Piece::class.java) //obiekt z ciekawostki z Firebase DB
                                if (updatePiece != null) {
                                    if (Repo.get().isFavouritePieceById(id))
                                        updatePiece.favourite = true
                                    if(Repo.get().isCompletedPieceById(id))
                                        updatePiece.completed = true
                                    Repo.get().update(updatePiece)
                                }
                            }
                        } else { //jesli ciekawostka o podanym id nie istnieje w lokalnej BD
                            val newPiece =
                                dataSnapshot.getValue(Piece::class.java) //obiekt z ciekawostki z Firebase DB
                            if (newPiece != null) {
                                newPiecesList.add(newPiece)
                            }
                        }
                    }
                }
                if (newPiecesList.isNotEmpty()) {
                    Repo.get().insertPieces(newPiecesList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
suspend fun updateFunFactsTable(context: Context,firebaseDB: FirebaseDatabase) {
    if (MessagesObject.checkInternetConnection(context)) {

        val funFactsRef = firebaseDB.getReference("funFactsTable")
        withContext(Dispatchers.IO) {
            try {
                val snapshot = funFactsRef.get()
                    .await() //pobranie ciekawostek z referencji BD Firebase i zawiesza watek do konca pobrania (await)
                val newFunFactsList =
                    mutableListOf<FunFact>() //lista ciekawostek do dodania do lokalnej BD
                for (dataSnapshot in snapshot.children) { //iteracja po ciekawostkach

                    val id = dataSnapshot.child("id")
                        .getValue(Int::class.java) //id ciekawostki z Firebase DB

                    if (id != null) {

                        if (Repo.get().funFactExistsById(id)) { //jesli id istnieje w lokalnej BD

                            val version = dataSnapshot.child("version")
                                .getValue(Int::class.java) //lastUpdate ciekawostki z Firebase DB
                            if (Repo.get()
                                    .getVersionFunFactById(id) != version
                            ) { //jesli w lokalnej bazie ciekawostka jest przestarzala

                                val updateFunFact =
                                    dataSnapshot.getValue(FunFact::class.java) //obiekt z ciekawostki z Firebase DB
                                if (updateFunFact != null) {

                                    if (Repo.get().isFavouriteFunFactById(id)) {
                                        updateFunFact.favourite = true

                                    }
                                    Repo.get().update(updateFunFact)
                                }
                            }
                        } else { //jesli ciekawostka o podanym id nie istnieje w lokalnej BD

                            val newFunFact =
                                dataSnapshot.getValue(FunFact::class.java) //obiekt z ciekawostki z Firebase DB
                            if (newFunFact != null) {

                                newFunFactsList.add(newFunFact)
                            }
                        }
                    }
                }
                if (newFunFactsList.isNotEmpty()) {
                    Repo.get().insertFunFacts(newFunFactsList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

suspend fun checkSettingsTable(context: Context,firebaseDB: FirebaseDatabase) {
    if (MessagesObject.checkInternetConnection(context)) {
        val settingsRef = firebaseDB.getReference("settingsTable")
        withContext(Dispatchers.IO) {
            try {
                val snapshot = settingsRef.get()
                    .await() //pobranie ciekawostek z referencji BD Firebase i zawiesza watek do konca pobrania (await)
                val newSettingsList =
                    mutableListOf<Setting>() //lista ciekawostek do dodania do lokalnej BD
                for (dataSnapshot in snapshot.children) { //iteracja po ciekawostkach
                    val id = dataSnapshot.child("id")
                        .getValue(Int::class.java) //id ciekawostki z Firebase DB
                    if (id != null) {
                        if (!Repo.get()
                                .settingExistsById(id)
                        ) { //jesli id nie istnieje w lokalnej BD

                            val newSetting =
                                dataSnapshot.getValue(Setting::class.java) //obiekt z ciekawostki z Firebase DB
                            if (newSetting != null) {
                                newSettingsList.add(newSetting)
                            }
                        }
                    }
                }
                if (newSettingsList.isNotEmpty()) {
                    Repo.get().insertSettings(newSettingsList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

/* proba stworzenia jednej funkcji do aktualizacji - problem z przekazaniem typu elementow tabeli np. FunFact, Piece
suspend fun updateTable(firebaseDB: FirebaseDatabase, table: TablesEnum) {
    val tableRef = firebaseDB.getReference(table.name)
    var existsById : (Int)->Boolean
    var getVersionById : (Int)->Int
    var getObject : ()
    when(table){
        TablesEnum.PIECES -> {
            existsById = {id: Int -> Repo.get().pieceExistsById(id)}
            getVersionById = {id: Int -> Repo.get().getVersionPieceById(id)}
        }
        TablesEnum.FUNFACTS -> {
            existsById = {id: Int -> Repo.get().funFactExistsById(id)}
            getVersionById = {id: Int -> Repo.get().getVersionFunFactById(id)}
        }
        TablesEnum.WARMUPS -> {
            existsById = {id: Int -> Repo.get().warmUpExistsById(id)}
            getVersionById = {id: Int -> Repo.get().getVersionWarnUpById(id)}
        }
    }
    withContext(Dispatchers.IO) {
        try {
            val snapshot = tableRef.get().await() //pobranie ciekawostek z referencji BD Firebase i zawiesza watek do konca pobrania (await)
            val newItemsList = table.getEmptyList() //lista ciekawostek do dodania do lokalnej BD
            for (dataSnapshot in snapshot.children) { //iteracja po ciekawostkach
                val id = dataSnapshot.child("id").getValue(Int::class.java) //id ciekawostki z Firebase DB
                if(id != null) {
                    if (existsById(id)) { //jesli id istnieje w lokalnej BD
                        val version = dataSnapshot.child("version").getValue(Int::class.java) //lastUpdate ciekawostki z Firebase DB
                        if(getVersionById(id) != version){ //jesli w lokalnej bazie ciekawostka jest przestarzala
                            val updateObj = dataSnapshot.getValue(table.type) //obiekt z ciekawostki z Firebase DB
                            if(updateFunFact != null) {
                                Repo.get().update(updateFunFact)
                            }
                        }
                    }
                    else{ //jesli ciekawostka o podanym id nie istnieje w lokalnej BD
                        val newFunFact = dataSnapshot.getValue(FunFact::class.java) //obiekt z ciekawostki z Firebase DB
                        if(newFunFact != null)
                            newFunFactsList.add(newFunFact)
                    }
                }
            }
            if(newFunFactsList.isNotEmpty()){
                Repo.get().insertFunFacts(newFunFactsList)
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}
fun <T : Any> createListForType(clazz: KClass<T>): List<T> {
    return listOf()
}
*/