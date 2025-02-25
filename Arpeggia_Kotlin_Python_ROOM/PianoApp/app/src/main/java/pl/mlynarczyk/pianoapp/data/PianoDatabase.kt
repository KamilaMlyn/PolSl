package pl.mlynarczyk.pianoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.mlynarczyk.pianoapp.data.Entities.FunFact
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Entities.Notification
import pl.mlynarczyk.pianoapp.data.Entities.Piece
import pl.mlynarczyk.pianoapp.data.Entities.Setting
import pl.mlynarczyk.pianoapp.data.Entities.WarmUp

@Database(entities = [
    Piece::class,
    WarmUp::class,
    FunFact::class,
    Setting::class,
    Notification::class,
    ModelResult::class
                     ],
    version = 24,
    exportSchema = false)
abstract class PianoDatabase : RoomDatabase() {
    abstract fun getDao(): IDao
}

object PianoDB {
    private var db: PianoDatabase ?= null

    fun getDB(context: Context) : PianoDatabase{
        if(db == null){
            db = Room
                .databaseBuilder(context, PianoDatabase::class.java, "piano-database")
                .fallbackToDestructiveMigration() //za kazdym razem przy nowej wersji bazy zniszcz cala i odbuduj od nowa
                .build()
        }
        return db!!
    }
}