package pl.mlynarczyk.pianoapp.data.Entities

import kotlin.reflect.KClass

enum class TablesEnum(tableName: String) {
    PIECES("piecesTable"),
    FUNFACTS("funFactsTable"),
    WARMUPS("warmUpsTable");

   /* fun getEmptyList(): List<*>{
        return when(this){
            PIECES -> emptyList<Piece>()
            FUNFACTS -> emptyList<FunFact>()
            WARMUPS -> emptyList<WarmUp>()
        }
    }*/
}

