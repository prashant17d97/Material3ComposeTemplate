package com.prashant.material3_compose_template.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontStyle.Companion.Normal
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.prashant.material3_compose_template.R

class Type(fontFamilyName: String) {

    private val googleFontFamily = FontFamily(
        Font(
            googleFont = GoogleFont(name = fontFamilyName),
            fontProvider = GoogleFont.Provider(
                providerAuthority = "com.google.android.gms.fonts",
                providerPackage = "com.google.android.gms",
                certificates = R.array.com_google_android_gms_fonts_certs
            )
        )
    )


    // Set of Material typography styles to start with

    private val typographyNormal = Typography(
        displayLarge = TextStyle(
            fontSize = 57.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Normal
        ),
        displayMedium =TextStyle(
            fontSize = 45.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Normal
        ),
        displaySmall =TextStyle(
            fontSize = 36.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Normal
        ),
        headlineLarge =TextStyle(
            fontSize = 32.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Normal
        ),
        headlineMedium =TextStyle(
            fontSize = 28.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Normal
        ),
        headlineSmall =TextStyle(
            fontSize = 24.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Normal
        ),
        titleLarge =TextStyle(
            fontSize = 22.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Normal
        ),
        titleMedium =TextStyle(
            fontSize = 16.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Normal
        ),
        titleSmall =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Normal
        ),
        bodyLarge =TextStyle(
            fontSize = 16.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Normal
        ),
        bodyMedium =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Normal
        ),
        bodySmall =TextStyle(
            fontSize = 12.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Normal
        ),
        labelLarge =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Normal
        ),
        labelMedium =TextStyle(
            fontSize = 12.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Normal
        ),
        labelSmall =TextStyle(
            fontSize = 11.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Normal
        )
    )
    private val typographyItalic = Typography(
        displayLarge = TextStyle(
            fontSize = 57.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Italic
        ),
        displayMedium =TextStyle(
            fontSize = 45.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Italic
        ),
        displaySmall =TextStyle(
            fontSize = 36.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Italic
        ),
        headlineLarge =TextStyle(
            fontSize = 32.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Italic
        ),
        headlineMedium =TextStyle(
            fontSize = 28.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Italic
        ),
        headlineSmall =TextStyle(
            fontSize = 24.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Italic
        ),
        titleLarge =TextStyle(
            fontSize = 22.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Italic
        ),
        titleMedium =TextStyle(
            fontSize = 16.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Italic
        ),
        titleSmall =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Italic
        ),
        bodyLarge =TextStyle(
            fontSize = 16.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Italic
        ),
        bodyMedium =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Italic
        ),
        bodySmall =TextStyle(
            fontSize = 12.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Italic
        ),
        labelLarge =TextStyle(
            fontSize = 14.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = Italic
        ),
        labelMedium =TextStyle(
            fontSize = 12.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = Italic
        ),
        labelSmall =TextStyle(
            fontSize = 11.sp,
            fontFamily = googleFontFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = Italic
        )
    )

    fun Typography(fontStyle: FontStyle = Normal) =
        typographyNormal.takeIf { fontStyle == Normal } ?: typographyItalic
}