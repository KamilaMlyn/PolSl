package pl.mlynarczyk.pianoapp.ui.theme

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.mlynarczyk.pianoapp.menu.SettingsObject


private val DarkColorScheme = darkColorScheme(
    primary = Violet80,
        onPrimary = Violet20,
    primaryContainer = Violet30,
        onPrimaryContainer = Violet90,
    inversePrimary = Violet40,

    secondary = Violet80,
        onSecondary = Violet20,
    secondaryContainer = Violet30,
        onSecondaryContainer = Violet90,

    tertiary = Violet80,
        onTertiary = Violet20,
    tertiaryContainer = Violet30,
        onTertiaryContainer = Violet90,

    error = Red80,
        onError = Red20,
    errorContainer = Red30,
        onErrorContainer = Red90,

    background = Grey10, //ok - tlo aplikacji
        onBackground = Grey90, //ok

    surface = Violet30,
        onSurface = Violet80,
    inverseSurface = Violet90,
        inverseOnSurface = Violet10,
    surfaceVariant = Violet30, //tlo card
        onSurfaceVariant = Violet80, //w card

    outline = Violet80
)

private val LightColorScheme = lightColorScheme(
    primary = Violet40,
        onPrimary = Violet95,
    primaryContainer = Violet90,
        onPrimaryContainer = Violet10,
    inversePrimary = Violet80,

    secondary = Violet40,
        onSecondary = Violet95,
    secondaryContainer = Violet90,
        onSecondaryContainer = Violet10,

    tertiary = Violet40,
        onTertiary = Violet95,
    tertiaryContainer = Violet90,
        onTertiaryContainer = Violet10,

    error = Red40,
        onError = Color.White,
    errorContainer = Red90,
        onErrorContainer = Red10,

    background = Grey90,
        onBackground = Grey10,

    surface = Violet90,
        onSurface = Violet30,
    inverseSurface = Violet20,
        inverseOnSurface = Violet95,
    surfaceVariant = Violet90,
        onSurfaceVariant = Violet30,

    outline = Grey50
)


@Composable
fun PianoAppTheme(
    darkTheme: Boolean = SettingsObject.darkTheme,//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme =when { //wybor trybu kolorow
   dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> { //dynamiczne theme
       val context = LocalContext.current
       if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
   }
   darkTheme -> DarkColorScheme
   else -> LightColorScheme
}*/
    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}