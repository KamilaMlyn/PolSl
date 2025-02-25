package pl.mlynarczyk.pianoapp.python

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import pl.mlynarczyk.pianoapp.data.Entities.ModelResult
import pl.mlynarczyk.pianoapp.data.Repo
import java.time.LocalDate

object PythonRepo{
    private var androidPlatform : AndroidPlatform? = null
    private var python : Python? = null
    private var mfccCounter : Int = 0

    fun start(context: Context) {
        if (python == null) {
            androidPlatform = AndroidPlatform(context)
            Python.start(androidPlatform!!)
            python = Python.getInstance()
        }
    }

     suspend fun get_emotions(path: String, modelPath: String, date: LocalDate,table: Boolean, id: Int) : List <Float> {
        if(python == null) {
            throw IllegalStateException("Python not set")
        }

        val result  = python!!.getModule("prepare_data").callAttr("get_emotions",path,modelPath).toList() as ArrayList
        val resultPair = result[0] as Pair<*,*>
        val emotionsString = resultPair.second.toString().trimIndent()
        val valuesRegex = Regex("""\d\.\d+""")
        val emotions = valuesRegex.findAll(emotionsString).map{
            it.value.toFloat()
        }.toList()

        val modelResult = ModelResult(null,id, date.toString(),table, emotions[0],emotions[1],emotions[2],emotions[3])
        Repo.get().insertModelResult(modelResult)
        return emotions
    }

}