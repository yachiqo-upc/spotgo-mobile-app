package com.yachiqo.spotgo.presentation.driver.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R

/** Decorative local map. Street artwork comes from Figma; no map SDK or location access. */
@Composable
internal fun ParkingMapBase(modifier: Modifier = Modifier) {
    val description = stringResource(R.string.driver_map_description)
    BoxWithConstraints(modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))
        .background(DriverMapBackground).semantics { contentDescription = description }) {
        val scale = maxWidth / 376.dp
        Box(Modifier.fillMaxWidth().height(360.dp * scale)) {
            for (row in 0..5) for (column in 0..6) {
                Box(Modifier.offset((14 + column * 58).dp * scale, (14 + row * 57).dp * scale)
                    .size(43.dp * scale, 41.dp * scale)
                    .background(Color(0xFF263044).copy(alpha = 0.55f), RoundedCornerShape(5.dp)))
            }
            Box(Modifier.offset(13.dp * scale, 14.dp * scale).size(78.dp * scale, 96.dp * scale)
                .background(Color(0xFF223C3E), RoundedCornerShape(12.dp)))
            Image(painterResource(R.drawable.map_streets), null,
                Modifier.offset((-20).dp * scale, (-20).dp * scale).size(416.dp * scale, 400.dp * scale))
            MapLabel(stringResource(R.string.driver_map_district), Modifier.offset(135.dp * scale, 22.dp * scale), 12)
            MapLabel(stringResource(R.string.driver_map_green_area), Modifier.offset(23.dp * scale, 43.dp * scale), 10)
            MapLabel(stringResource(R.string.driver_map_main_avenue),
                Modifier.offset(125.dp * scale, 126.dp * scale).background(DriverMapBackground,
                    RoundedCornerShape(5.dp)).padding(horizontal = 7.dp, vertical = 3.dp), 10, bright = true)
            MapLabel(stringResource(R.string.driver_map_west_avenue),
                Modifier.offset(48.dp * scale, 273.dp * scale).rotate(-87f), 9)
            MapLabel(stringResource(R.string.driver_map_east_avenue),
                Modifier.offset(263.dp * scale, 40.dp * scale).rotate(-87f), 9)
            MapLabel(stringResource(R.string.driver_map_street),
                Modifier.offset(122.dp * scale, 264.dp * scale).background(DriverMapBackground,
                    RoundedCornerShape(4.dp)).padding(horizontal = 7.dp, vertical = 3.dp), 9)
        }
    }
}

@Composable
private fun MapLabel(text: String, modifier: Modifier, size: Int, bright: Boolean = false) {
    Text(text, modifier, style = MaterialTheme.typography.bodySmall.copy(fontSize = size.sp, lineHeight = (size + 4).sp),
        color = if (bright) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)
}
