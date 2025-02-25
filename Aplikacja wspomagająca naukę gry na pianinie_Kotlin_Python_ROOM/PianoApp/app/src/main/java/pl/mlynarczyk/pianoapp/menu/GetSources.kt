package pl.mlynarczyk.pianoapp.menu

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import pl.mlynarczyk.pianoapp.data.FirebaseStorageObject

@SuppressLint("DiscouragedApi") //przez getIdentifier()
suspend fun getIdOrUrl(path: String, fileName: String, ext: String, context: Context) : Pair<Int,String?> {

    var fileUrl: String? = null
    var fileId = 0
    try {
       /* fileId = context.resources.getIdentifier(
            fileName,
            "raw",
            context.packageName
        )*/ //pobranie id zasobu

        /*if (fileId != 0) { //jesli nuty sa w plikach aplikacji
            return Pair(fileId, null)
        } else*/ if (isInternetAvailable(context)) {
                fileUrl = FirebaseStorageObject.getStorageRef()
                    .child("$path/$fileName.$ext").downloadUrl.await().toString()

        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
    //return Pair(0,null)
    return Pair(0, fileUrl)
}

suspend fun downloadFromFbStorage() : Boolean{
    delay(3000)
    return true
}