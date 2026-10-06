package com.yachiqo.spotgo.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun SpotGoWordmark(modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            append("Spot")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) { append("Go") }
        },
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 24.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold,
        ),
    )
}
