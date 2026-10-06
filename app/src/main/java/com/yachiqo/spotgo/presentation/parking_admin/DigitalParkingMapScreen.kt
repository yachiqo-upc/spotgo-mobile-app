package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.parking_admin.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun DigitalParkingMapScreen(onBack: () -> Unit, onUnavailableAction: () -> Unit) {
    AdminDetailPage(stringResource(R.string.admin_map_title), onBack) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(stringResource(R.string.admin_map_heading),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp, lineHeight = 27.sp))
            Text(stringResource(R.string.admin_map_subtitle), style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        AdminFilterRow(listOf(R.string.admin_occupancy_all, R.string.admin_map_standard,
            R.string.admin_occupancy_ev, R.string.admin_map_accessible), onUnavailableAction)
        AdminFloorPlan(onSpotClick = onUnavailableAction, draft = true)
        AdminPanel(outlined = true, containerColor = Color(0xFF23202D)) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(Modifier.fillMaxWidth().heightIn(min = 28.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(R.string.admin_map_selected_spot), Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
                    AdminStatusBadge(stringResource(R.string.admin_occupancy_unavailable), AdminError,
                        Color(0xFF23202D), R.drawable.admin_map_unavailable_dot)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AdminDisplayField(stringResource(R.string.admin_map_standard), stringResource(R.string.admin_map_type), Modifier.weight(1f))
                    AdminDisplayField(stringResource(R.string.admin_map_sensor_value), stringResource(R.string.admin_map_sensor), Modifier.weight(1f))
                }
            }
        }
        Surface(shape = RoundedCornerShape(20.dp), color = Color(0xFF23202D), contentColor = MaterialTheme.colorScheme.onSurface) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.admin_map_publish_heading), color = AdminWarning, style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.admin_map_publish_note), style = MaterialTheme.typography.bodyMedium)
            }
        }
        OutlinedButton(onClick = onUnavailableAction, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)) {
            Text(stringResource(R.string.admin_map_correct), style = MaterialTheme.typography.bodyLarge)
        }
        Button(onClick = onUnavailableAction, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
            shape = RoundedCornerShape(16.dp)) {
            Text(stringResource(R.string.admin_map_publish), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun DigitalParkingMapPreview() {
    SpotGoTheme { Surface { DigitalParkingMapScreen({}, {}) } }
}
