package com.yachiqo.spotgo.presentation.parking_admin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R

/** Fixed presentation of the Figma floor plan, without sensors or map services. */
@Composable
internal fun AdminFloorPlan(onSpotClick: () -> Unit) {
    val description = stringResource(R.string.admin_occupancy_map_description)
    BoxWithConstraints(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))
        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(24.dp))
        .semantics { contentDescription = description }) {
        val scale = maxWidth / 376.dp
        val available = stringResource(R.string.admin_available)
        val occupied = stringResource(R.string.admin_occupancy_spot_occupied)
        val standard = stringResource(R.string.admin_occupancy_standard)
        val darkText = MaterialTheme.colorScheme.onPrimary
        val lightText = MaterialTheme.colorScheme.onSurface
        Box(Modifier.fillMaxWidth().height(370.dp * scale).background(AdminMapBackground)) {
            Text(stringResource(R.string.admin_occupancy_level),
                Modifier.offset(19.dp * scale, 14.dp * scale),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp * scale, lineHeight = 17.sp * scale))
            Text(stringResource(R.string.admin_occupancy_monitored_spots),
                Modifier.offset(217.dp * scale, 15.dp * scale),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp * scale, lineHeight = 16.sp * scale),
                color = MaterialTheme.colorScheme.onSurfaceVariant)

            ParkingSpot(stringResource(R.string.admin_occupancy_a01), standard, available, AdminAvailable, darkText,
                Modifier.offset(19.dp * scale, 48.dp * scale), scale, onSpotClick)
            ParkingSpot(stringResource(R.string.admin_occupancy_a02), standard, occupied, AdminOccupied, lightText,
                Modifier.offset(104.dp * scale, 48.dp * scale), scale, onSpotClick)
            ParkingSpot(stringResource(R.string.admin_occupancy_a03), stringResource(R.string.admin_occupancy_ev),
                stringResource(R.string.admin_occupancy_reserved), AdminWarning, darkText,
                Modifier.offset(189.dp * scale, 48.dp * scale), scale, onSpotClick)
            ParkingSpot(stringResource(R.string.admin_occupancy_a04), stringResource(R.string.admin_occupancy_accessible),
                available, AdminAvailable, darkText,
                Modifier.offset(274.dp * scale, 48.dp * scale), scale, onSpotClick)

            Box(Modifier.offset(11.dp * scale, 155.dp * scale).size(352.dp * scale, 63.dp * scale)
                .background(AdminOccupied, RoundedCornerShape(8.dp)))
            Image(painterResource(R.drawable.admin_one_way_circulation),
                stringResource(R.string.admin_occupancy_circulation),
                Modifier.offset(24.dp * scale, 173.dp * scale).size(326.dp * scale, 26.dp * scale))
            Text(stringResource(R.string.admin_occupancy_entry),
                Modifier.offset(19.dp * scale, 217.dp * scale),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp * scale, lineHeight = 14.sp * scale),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(stringResource(R.string.admin_occupancy_exit),
                Modifier.offset(316.dp * scale, 217.dp * scale),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp * scale, lineHeight = 14.sp * scale),
                color = MaterialTheme.colorScheme.onSurfaceVariant)

            ParkingSpot(stringResource(R.string.admin_occupancy_a05), standard, occupied, AdminOccupied, lightText,
                Modifier.offset(19.dp * scale, 242.dp * scale), scale, onSpotClick)
            ParkingSpot(stringResource(R.string.admin_occupancy_a06), standard, available, AdminAvailable, lightText,
                Modifier.offset(104.dp * scale, 242.dp * scale), scale, onSpotClick,
                borderColor = AdminAvailable, emphasized = true)
            ParkingSpot(stringResource(R.string.admin_occupancy_a07), standard, stringResource(R.string.admin_occupancy_unavailable),
                Color(0xFF23202D), lightText,
                Modifier.offset(189.dp * scale, 242.dp * scale), scale, onSpotClick,
                borderColor = AdminError, emphasized = true)
            ParkingSpot(stringResource(R.string.admin_occupancy_a08), standard, available, AdminAvailable, darkText,
                Modifier.offset(274.dp * scale, 242.dp * scale), scale, onSpotClick)
        }
    }
}

@Composable
private fun ParkingSpot(
    label: String,
    type: String,
    status: String,
    background: Color,
    foreground: Color,
    modifier: Modifier,
    scale: Float,
    onClick: () -> Unit,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    emphasized: Boolean = false,
) {
    Surface(onClick = onClick, modifier = modifier.size(76.dp * scale, 100.dp * scale),
        shape = RoundedCornerShape(10.dp), color = background, contentColor = foreground,
        border = BorderStroke(if (emphasized) 2.dp else 1.dp, borderColor)) {
        Box {
            Text(label, Modifier.offset(8.dp * scale, 8.dp * scale),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp * scale, lineHeight = 18.sp * scale))
            Text(type, Modifier.offset(9.dp * scale, 36.dp * scale),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp * scale, lineHeight = 24.sp * scale))
            Image(painterResource(R.drawable.admin_spot_status_dot), null,
                Modifier.offset(8.dp * scale, 78.dp * scale).size(5.dp * scale))
            Text(status, Modifier.offset(18.dp * scale, 74.dp * scale),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp * scale, lineHeight = 16.sp * scale),
                maxLines = 1, softWrap = false)
        }
    }
}
