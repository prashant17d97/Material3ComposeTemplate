package com.prashant.material3_compose_template.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.prashant.material3_compose_template.uiconfiguration.UIConfiguration

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color.Black,
    onBackground = Color.Black,
    surface = Color.Black,
    onSurface = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color.White,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeTemplateTheme(
    uiConfiguration: UIConfiguration,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            Log.e("Compose", "ComposeTemplateTheme: 1., DarkScheme:---->${dynamicDarkColorScheme(context)}\n LightScheme--->${dynamicLightColorScheme(
                context
            )}")
            if (uiConfiguration.isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )

        }
        uiConfiguration.isDarkTheme -> {
            DarkColorScheme
        }
        else -> {
            LightColorScheme
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        /* getting the current window by tapping into the Activity */
        val currentWindow = (view.context as? Activity)?.window
            ?: throw Exception("Not in an activity - unable to get Window reference")

        SideEffect {
            /* the default code did the same cast here - might as well use our new variable! */
            currentWindow.statusBarColor = colorScheme.primary.toArgb()
            /* accessing the insets controller to change appearance of the status bar, with 100% less deprecation warnings */
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars =
                uiConfiguration.isDarkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Type(fontFamilyName = uiConfiguration.fontFamily).Typography(uiConfiguration.fontStyle),
        shapes = Shapes,
        content = content
    )
}