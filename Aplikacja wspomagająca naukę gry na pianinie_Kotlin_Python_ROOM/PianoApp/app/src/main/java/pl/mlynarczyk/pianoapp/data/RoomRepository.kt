package pl.mlynarczyk.pianoapp.data


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Entities.Notification
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.Setting
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp

class Repository : IDao {
    private var dao : IDao? = null
    private fun isDaoSet() : Boolean = dao != null
    fun setDao(dao :IDao){
        this.dao = dao
    }


//piecesTable:
    override suspend fun insertPieces(pieces: List<Piece>) = withContext(Dispatchers.IO) {
        if(isDaoSet())
            dao!!.insertPieces(pieces)
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getAllPieces(): Flow<List<Piece>> {
        if(isDaoSet())
            return dao!!.getAllPieces()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getPieceById(pieceId : Int): Piece? {
        if(isDaoSet())
            return withContext(Dispatchers.IO){
                dao!!.getPieceById(pieceId)
            }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun update(pieceEntity: Piece)= withContext(Dispatchers.IO) {
        if(isDaoSet()) {
            dao!!.update(pieceEntity)
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun dropPiecesTable() = withContext(Dispatchers.IO) {
        if(isDaoSet())
            dao!!.dropPiecesTable()
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getFavouritePieces(): Flow<List<Piece>> {

        if(isDaoSet())
            return dao!!.getFavouritePieces()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun pieceExistsById(id: Int): Boolean {
        if(isDaoSet())
            return dao!!.pieceExistsById(id)
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getVersionPieceById(id: Int): Int {
        if(isDaoSet())
            return dao!!.getVersionFunFactById(id)
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun isFavouritePieceById(id: Int): Boolean {
        if(isDaoSet())
            return dao!!.isFavouritePieceById(id)
        else
            throw IllegalStateException("Dao not set")
    }
    override suspend fun isCompletedPieceById(id: Int) : Boolean{
        if(isDaoSet())
            return dao!!.isCompletedPieceById(id)
        else
            throw IllegalStateException("Dao not set")
    }

//warmUpsTable:
    override suspend fun insertWarmUps(warmUps: List<WarmUp>) {

        if(isDaoSet())
            dao!!.insertWarmUps(warmUps)
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getAllWarmUps(): Flow<List<WarmUp>> {
        if(isDaoSet())
            return dao!!.getAllWarmUps()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun dropWarmUpTable() {
        if(isDaoSet())
            dao!!.dropWarmUpTable()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getWarmUpById(warmUpId: Int): WarmUp? {
        if(isDaoSet())
            return withContext(Dispatchers.IO){
                dao!!.getWarmUpById(warmUpId)
            }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun update(warmUp: WarmUp) {
        if(isDaoSet()) {
            dao!!.update(warmUp)
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun warmUpExistsById(id: Int): Boolean {
        if(isDaoSet())
            return withContext(Dispatchers.IO){
                dao!!.warmUpExistsById(id)
            }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getVersionWarmUpById(id: Int): Int {
        if(isDaoSet())
            return withContext(Dispatchers.IO){
                dao!!.getVersionWarmUpById(id)
            }
        else
            throw IllegalStateException("Dao not set")
    }

//funFactsTable:
    override suspend fun insertFunFacts(funFacts: List<FunFact>) {
        if (isDaoSet())
            dao!!.insertFunFacts(funFacts)
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getAllFunFacts(): Flow<List<FunFact>> {
        if(isDaoSet())
            return dao!!.getAllFunFacts()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun dropFunFactsTable() {
        if(isDaoSet())
            dao!!.dropFunFactsTable()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getFunFactById(funFactId: Int): FunFact? {
        if(isDaoSet())
            return withContext(Dispatchers.IO){
                dao!!.getFunFactById(funFactId)
            }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun update(funFact: FunFact) {
        if(isDaoSet()) {
            dao!!.update(funFact)
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getFavouriteFunFacts(): Flow<List<FunFact>> {
        if(isDaoSet())
            return dao!!.getFavouriteFunFacts()
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun funFactExistsById(id: Int): Boolean{
        if(isDaoSet())
            return dao!!.funFactExistsById(id)
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getVersionFunFactById(id:Int): Int{
        if(isDaoSet())
            return dao!!.getVersionFunFactById(id)
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun isFavouriteFunFactById(id: Int): Boolean {
        if(isDaoSet())
            return dao!!.isFavouriteFunFactById(id)
        else
            throw IllegalStateException("Dao not set")
    }

//settingsTable:
    override suspend fun update(setting: String, value: String) {
        if (isDaoSet()) {
            dao!!.update(setting, value)
        } else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun insertSettings(settings: List<Setting>) {
        if(isDaoSet()) {
            dao!!.insertSettings(settings)
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun dropSettingsTable() {
        if(isDaoSet()) {
            dao!!.dropSettingsTable()
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override suspend fun getSettingValue(setting: String) : String {
        if(isDaoSet()) {
            return dao!!.getSettingValue(setting)
        }
        else
            throw IllegalStateException("Dao not set")
    }

    override fun getSettingsNames() : Flow<List<String>> {
        if (isDaoSet()) {
            return dao!!.getSettingsNames()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun getSettingsCount(): Int {
        if (isDaoSet()) {
            return dao!!.getSettingsCount()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
    override suspend fun settingExistsById(id: Int): Boolean{
        if (isDaoSet()) {
            return dao!!.settingExistsById(id)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
//notificationsTable:

    override suspend fun insertNotifications(notification: Notification) : Long{
        if (isDaoSet()) {
            val a = dao!!.insertNotifications(notification)

            return a
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun update(notification: Notification){
        if (isDaoSet()) {
            return dao!!.update(notification)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun deleteFromNotificationsTable(id: Int){
        if (isDaoSet()) {
            return dao!!.deleteFromNotificationsTable(id)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override fun getNotifications():Flow<List<Notification>>{
        if (isDaoSet()) {
            return dao!!.getNotifications()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
    override suspend fun dropNotificationsTable(){
        if (isDaoSet()) {
            return dao!!.dropNotificationsTable()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
    
//modelResultsTable


    override suspend fun insertModelResult(result: ModelResult) : Long{
        if (isDaoSet()) {
            return dao!!.insertModelResult(result)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun update(result: ModelResult){
        if (isDaoSet()) {
            return dao!!.update(result)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun deleteFromModelResultsTable(id: Int){
        if (isDaoSet()) {
            return dao!!.deleteFromModelResultsTable(id)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override fun getAllModelResults():Flow<List<ModelResult>>{
        if (isDaoSet()) {
            return dao!!.getAllModelResults()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override fun getModelResultsAbout(id_FK: Int, table: Boolean) : Flow<List<ModelResult>>{
        if (isDaoSet()) {
            return dao!!.getModelResultsAbout(id_FK,table)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }

    override suspend fun dropModelResultsTable(){
        if (isDaoSet()) {
            return dao!!.dropModelResultsTable()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
    override fun getFkFrom(table: Boolean): Flow<List<Int>>{
        if (isDaoSet()) {
            return dao!!.getFkFrom(table)
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
    suspend fun dropAll(){
        if (isDaoSet()) {
            dao!!.dropPiecesTable()
            dao!!.dropSettingsTable()
            dao!!.dropWarmUpTable()
            dao!!.dropNotificationsTable()
            dao!!.dropModelResultsTable()
            dao!!.dropFunFactsTable()
        } else {
            throw IllegalStateException("Dao not set")
        }
    }
}

object Repo{
    private var repository : Repository? = null

    fun set(repository: Repository){
        this.repository = repository
    }

    fun get() : Repository{
        return repository ?: throw IllegalStateException("Repository not set")
    }


}