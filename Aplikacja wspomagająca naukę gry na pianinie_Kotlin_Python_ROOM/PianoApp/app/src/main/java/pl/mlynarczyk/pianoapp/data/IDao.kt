package pl.mlynarczyk.pianoapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Entities.Notification
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.Setting
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp

@Dao
interface IDao { //Data Access Object
// piecesTable:
    @Insert
    suspend fun insertPieces(pieces: List<Piece>) //suspend- asynchroniczna funkcja uzywajaca korutyny

    @Query("SELECT * FROM piecesTable")
    fun getAllPieces(): Flow<List<Piece>> //flow - w momencie uzycia informuje

    @Update
    suspend fun update(pieceEntity: Piece)

    @Query("DELETE FROM piecesTable")
    suspend fun dropPiecesTable()

    @Query("SELECT * FROM piecesTable WHERE piecesTable.id = :pieceId")
    suspend fun getPieceById(pieceId : Int) : Piece?

    @Query("SELECT * FROM piecesTable WHERE piecesTable.favourite = 1")
    fun getFavouritePieces() : Flow<List<Piece>>

    @Query("SELECT EXISTS(SELECT 1 FROM piecesTable WHERE id = :id)")
    suspend fun pieceExistsById(id: Int): Boolean

    @Query("SELECT version FROM piecesTable WHERE id = :id")
    suspend fun getVersionPieceById(id:Int): Int

    @Query("SELECT favourite FROM piecesTable WHERE id = :id")
    suspend fun isFavouritePieceById(id: Int): Boolean

    @Query("SELECT completed FROM piecesTable WHERE id = :id")
    suspend fun isCompletedPieceById(id: Int) : Boolean

//warmUpsTable:
    @Insert
    suspend fun insertWarmUps(warmUps: List<WarmUp>)

    @Query("SELECT * FROM warmUpsTable")
    fun getAllWarmUps(): Flow<List<WarmUp>>

    @Update
    suspend fun update(warmUp: WarmUp)

    @Query("DELETE FROM warmUpsTable")
    suspend fun dropWarmUpTable()

    @Query("SELECT * FROM warmUpsTable WHERE warmUpsTable.id = :warmUpId")
    suspend fun getWarmUpById(warmUpId : Int) : WarmUp?

    @Query("SELECT EXISTS(SELECT 1 FROM warmUpsTable WHERE id = :id)")
    suspend fun warmUpExistsById(id: Int): Boolean

    @Query("SELECT version FROM warmUpsTable WHERE id = :id")
    suspend fun getVersionWarmUpById(id:Int): Int

//funFactsTable:
    @Insert
    suspend fun insertFunFacts(funFacts: List<FunFact>)

    @Query("SELECT * FROM funFactsTable")
    fun getAllFunFacts(): Flow<List<FunFact>>

    @Update
    suspend fun update(funFact: FunFact)

    @Query("DELETE FROM funFactsTable")
    suspend fun dropFunFactsTable()

    @Query("SELECT * FROM funFactsTable WHERE funFactsTable.id = :funFactId")
    suspend fun getFunFactById(funFactId : Int) : FunFact?

    @Query("SELECT * FROM funFactsTable WHERE funFactsTable.favourite = 1")
    fun getFavouriteFunFacts() : Flow<List<FunFact>>

    @Query("SELECT EXISTS(SELECT 1 FROM funFactsTable WHERE id = :id)")
    suspend fun funFactExistsById(id: Int): Boolean

    @Query("SELECT version FROM funFactsTable WHERE id = :id")
    suspend fun getVersionFunFactById(id:Int): Int

    @Query("SELECT favourite FROM funFactsTable WHERE id = :id")
    suspend fun isFavouriteFunFactById(id: Int): Boolean

//settingsTable:
    @Insert
    suspend fun insertSettings(settings: List<Setting>)

    @Query("UPDATE settingsTable SET value = :value WHERE setting = :setting")
    suspend fun update(setting: String, value: String)

    @Query("DELETE FROM settingsTable")
    suspend fun dropSettingsTable()

    @Query("SELECT value FROM settingsTable WHERE setting = :setting")
    suspend fun getSettingValue(setting: String) : String

    @Query("SELECT setting FROM settingsTable")
    fun getSettingsNames() : Flow<List<String>>

    @Query("SELECT COUNT(*) FROM settingsTable")
    suspend fun getSettingsCount():Int

    @Query("SELECT EXISTS(SELECT 1 FROM settingsTable WHERE id = :id)")
    suspend fun settingExistsById(id: Int): Boolean

//notificationsTable:
    @Insert
    suspend fun insertNotifications(notification: Notification) : Long

    @Update
    suspend fun update(notification: Notification)

    @Query("DELETE FROM notificationsTable WHERE id = :id")
    suspend fun deleteFromNotificationsTable(id: Int)

    @Query("SELECT * FROM notificationsTable ORDER BY hour ASC, minute ASC")
    fun getNotifications():Flow<List<Notification>>

    @Query("DELETE FROM notificationsTable")
    suspend fun dropNotificationsTable()

//PythonAnalysisTable
    @Insert
    suspend fun insertModelResult(result: ModelResult) : Long

    @Update
    suspend fun update(result: ModelResult)

    @Query("DELETE FROM modelResultsTable WHERE id = :id")
    suspend fun deleteFromModelResultsTable(id: Int)

    @Query("SELECT * FROM ModelResultsTable")
    fun getAllModelResults():Flow<List<ModelResult>>

    @Query("SELECT * FROM modelResultsTable WHERE id_FK = :id_FK and `table` = :table")
    fun getModelResultsAbout(id_FK: Int, table: Boolean) : Flow<List<ModelResult>>

    @Query("SELECT DISTINCT id_FK FROM ModelResultsTable WHERE `table` = :table")
    fun getFkFrom(table: Boolean) : Flow<List<Int>>

    @Query("DELETE FROM modelResultsTable")
    suspend fun dropModelResultsTable()
}