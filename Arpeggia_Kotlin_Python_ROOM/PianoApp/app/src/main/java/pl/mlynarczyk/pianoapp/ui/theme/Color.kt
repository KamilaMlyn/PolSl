package pl.mlynarczyk.pianoapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val Violet10 = Color(0xff14141f)
val Violet20 = Color(0xff29293d)
val Violet30 = Color(0xff3c3c5d)
val Violet40 = Color(0xff50507c)
val Violet80 = Color(0xffc1c1d7)
val Violet90 = Color(0xffe0e0eb)
val Violet95 = Color(0xfff0f0f5)

val Blue10 = Color(0xff001933)
val Blue90 = Color(0xffe6f2ff)

val Red10 = Color(0xFF410001)
val Red20 = Color(0xFF680003)
val Red30 = Color(0xFF930006)
val Red40 = Color(0xFFBA1B1B)
val Red80 = Color(0xFFFFB4A9)
val Red90 = Color(0xFFFFDAD4)

val Grey10 = Color(0xff1a1a1a)
val Grey20 = Color(0xFF333333)
val Grey30 = Color(0xFF4d4d4d)
val Grey40 = Color(0xFF666666)
val Grey50 = Color(0xFF808080)
val Grey80 = Color(0xFFcccccc)
val Grey90 = Color(0xFFe6e6e6)
val Grey95 = Color(0xfff2f2f2)
val Grey99 = Color(0xFFFBFDFD)



enum class GradientsEnum(val brush: Brush){
    GreyGradient(Brush.linearGradient(listOf(Grey20, Grey90)))
}