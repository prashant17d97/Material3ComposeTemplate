package com.prashant.material3_compose_template.interfaces

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Normal


interface UiConfiguration {
    fun changeConfiguration()
    fun changeConfigurationTemporary(
        darkTheme: Boolean?=null,
        fontFamily: String="",
        fontStyle: FontStyle = Normal
    )

    fun showLoader(boolean: Boolean)
}