package com.yachiqo.spotgo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R

@OptIn(ExperimentalTextApi::class)
private val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans, weight = FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.plus_jakarta_sans, weight = FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.plus_jakarta_sans, weight = FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(R.font.plus_jakarta_sans, weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700))),
)

private fun spotGoStyle(size: Int, lineHeight: Int, weight: FontWeight, tracking: Float = 0f) =
    TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = tracking.sp,
    )

val Typography = Typography(
    headlineMedium = spotGoStyle(28, 35, FontWeight.Bold),
    titleLarge = spotGoStyle(22, 30, FontWeight.Bold),
    titleMedium = spotGoStyle(16, 24, FontWeight.SemiBold),
    bodyLarge = spotGoStyle(16, 24, FontWeight.Medium),
    bodyMedium = spotGoStyle(14, 21, FontWeight.Medium, 0.25f),
    bodySmall = spotGoStyle(12, 18, FontWeight.Normal),
    labelLarge = spotGoStyle(14, 21, FontWeight.Medium, 0.1f),
    labelMedium = spotGoStyle(12, 16, FontWeight.Medium, 0.1f),
    labelSmall = spotGoStyle(11, 16, FontWeight.Medium, 0.1f),
)
