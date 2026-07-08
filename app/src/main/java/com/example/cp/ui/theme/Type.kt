package com.example.cp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.cp.R


val VazirFontFamily = FontFamily(
    Font(R.font.vazir)
)


val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        textDirection = TextDirection.Rtl // Set default direction
    ),
    displayMedium = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    displaySmall = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    headlineLarge = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    headlineMedium = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    headlineSmall = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    titleLarge = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    titleMedium = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        textDirection = TextDirection.Rtl
    ),
    titleSmall = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        textDirection = TextDirection.Rtl
    ),
    bodyLarge = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Rtl
    ),
    bodyMedium = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        textDirection = TextDirection.Rtl
    ),
    bodySmall = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        textDirection = TextDirection.Rtl
    ),
    labelLarge = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        textDirection = TextDirection.Rtl
    ),
    labelMedium = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Rtl
    ),
    labelSmall = TextStyle(
        fontFamily = VazirFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Rtl
    )
)