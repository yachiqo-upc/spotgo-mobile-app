package com.yachiqo.spotgo.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/** Figma exports the glyph bounds; preserve those inside the original 24 dp icon slot. */
@Composable
internal fun FigmaIcon(@DrawableRes resourceId: Int, contentDescription: String? = null) {
    val painter = painterResource(resourceId)
    val width = with(LocalDensity.current) { painter.intrinsicSize.width.toDp() }
    val height = with(LocalDensity.current) { painter.intrinsicSize.height.toDp() }
    Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
        Icon(painter, contentDescription, modifier = Modifier.size(width, height))
    }
}
