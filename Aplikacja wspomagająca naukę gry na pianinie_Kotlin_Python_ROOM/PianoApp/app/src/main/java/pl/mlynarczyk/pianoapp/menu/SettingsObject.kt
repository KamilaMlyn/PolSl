package pl.mlynarczyk.pianoapp.menu

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.flow.firstOrNull
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Repo
import java.io.IOException
import java.time.LocalDate
import java.util.Properties


enum class SettingsEnum(val settingName: String, val value1: String, val value2: String?=null){
    DARKTHEME ("darkTheme","True","False"),
    USERNAME("name","Pianisto"),
    PATHTOSAVEDFILES("pathToSavedFiles","")
}

object SettingsObject {
    var darkTheme: Boolean = SettingsEnum.DARKTHEME.value2.toBoolean()
    var userName: String = SettingsEnum.USERNAME.value1
    var pathToSavedFiles : String = SettingsEnum.PATHTOSAVEDFILES.value1

    fun changeTheme(): Boolean {

        darkTheme = darkTheme.not()
        return darkTheme
    }

    suspend fun saveSettings() {
        Repo.get().update(SettingsEnum.DARKTHEME.settingName, darkTheme.toString())
        Repo.get().update(SettingsEnum.USERNAME.settingName, userName)

    }

    suspend fun checkBasicSettings(){
        darkTheme = Repo.get().getSettingValue(SettingsEnum.DARKTHEME.settingName).toBoolean()
        val userNameFromDB = Repo.get().getSettingValue(SettingsEnum.USERNAME.settingName)
        userName = if(userNameFromDB != null){
            userNameFromDB
        }else{
            SettingsEnum.USERNAME.value1
        }
        pathToSavedFiles = Repo.get().getSettingValue(SettingsEnum.PATHTOSAVEDFILES.settingName)
    }
    suspend fun makeBackup(context: Context, uri: Uri){
        val path = DocumentFile.fromTreeUri(context,uri)
        val date = LocalDate.now()
        val backupFile = path?.createFile("text/x-java-properties", "Arpeggia_backupFile_$date.properties")
            ?: throw IOException("Nie można utworzyć pliku backupu")
        val analysis = Repo.get().getAllModelResults().firstOrNull()
        val properties = makeProperties(analysis)
        context.contentResolver.openOutputStream(backupFile.uri).use { outputStream ->
            properties.store(outputStream, "Arpeggia Settings Backup: $date")
        }

    }

    private fun makeProperties(analysys: List<ModelResult>?): Properties {
        val properties = Properties()
        properties["darkTheme"] = darkTheme.toString()
        properties["userName"] = userName
        properties["pathToSavedFiles"] = pathToSavedFiles
        if(analysys != null) {
            for (res in analysys) {
               // properties["Analysis${res.id}_id"] = res.id
                properties["Analysis${res.id}_date"]  = res.date
                properties["Analysis${res.id}_id_FK"]  = res.id_FK.toString()
                properties["Analysis${res.id}_table"]  = res.table.toString()
                properties["Analysis${res.id}_Q1"]  = res.Q1.toString()
                properties["Analysis${res.id}_Q2"]  = res.Q2.toString()
                properties["Analysis${res.id}_Q3"]  = res.Q3.toString()
                properties["Analysis${res.id}_Q4"]  = res.Q4.toString()
            }
        }

        return properties
    }

     suspend fun restoreBackup(context: Context,uri : Uri) {
         val properties = Properties()
         context.contentResolver.openInputStream(uri).use{ inputStream ->
             properties.load(inputStream)
         }
         Repo.get().dropModelResultsTable()
         readProperties(properties)
         saveSettings()
     }

    private suspend fun readProperties(properties: Properties) {
        darkTheme = properties["darkTheme"].toString().toBoolean()
        userName = properties["userName"].toString()
        pathToSavedFiles = properties["pathToSavedFiles"].toString()
        var i = 1
        while(true){
            try {
                val res = ModelResult(
                    date = properties["Analysis${i}_date"].toString(),
                    id_FK = properties["Analysis${i}_id_FK"].toString().toInt(),
                    table = properties["Analysis${i}_table"].toString().toBoolean(),
                    Q1 = properties["Analysis${i}_Q1"].toString().toFloat(),
                    Q2 = properties["Analysis${i}_Q2"].toString().toFloat(),
                    Q3 = properties["Analysis${i}_Q3"].toString().toFloat(),
                    Q4 = properties["Analysis${i}_Q4"].toString().toFloat(),
                )
                Repo.get().insertModelResult(res)
                i++
            }catch (e: Exception){
                e.printStackTrace()
                return
            }
        }
    }
}