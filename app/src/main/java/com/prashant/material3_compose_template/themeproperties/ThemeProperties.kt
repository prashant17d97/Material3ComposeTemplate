package com.prashant.material3_compose_template.themeproperties

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Normal

data class ThemeProperties(
    val isDarkTheme: Boolean = true,
    val fontFamily: String = "Ubuntu",
    val fontStyle: FontStyle = Normal
)
