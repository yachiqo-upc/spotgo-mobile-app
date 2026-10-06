package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AdminDashboardScreen(onOpenOccupancy: () -> Unit, onUnavailableAction: () -> Unit) {
    AdminPage(stringResource(R.string.admin_dashboard_title), stringResource(R.string.admin_dashboard_subtitle),
        onAccount = onUnavailableAction) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DashboardMetric(stringResource(R.string.admin_dashboard_occupancy_value), stringResource(R.string.admin_occupancy),
                MaterialTheme.colorScheme.primary, Modifier.weight(1f), onOpenOccupancy, progress = 0.58f)
            DashboardMetric(stringResource(R.string.admin_dashboard_available_value), stringResource(R.string.admin_available),
                AdminAvailable, Modifier.weight(1f), onOpenOccupancy)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DashboardMetric(stringResource(R.string.admin_dashboard_alerts_value), stringResource(R.string.admin_dashboard_open_alerts),
                AdminWarning, Modifier.weight(1f), onUnavailableAction)
            DashboardMetric(stringResource(R.string.admin_dashboard_guests_value), stringResource(R.string.admin_dashboard_guest_sessions),
                MaterialTheme.colorScheme.onSurface, Modifier.weight(1f), onUnavailableAction)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DashboardMetric(stringResource(R.string.admin_dashboard_reservations_value), stringResource(R.string.admin_dashboard_active_reservations),
                AdminWarning, Modifier.weight(1f), onUnavailableAction)
            DashboardMetric(stringResource(R.string.admin_dashboard_balances_value), stringResource(R.string.admin_dashboard_outstanding_balances),
                MaterialTheme.colorScheme.onSurface, Modifier.weight(1f), onUnavailableAction)
        }
        AdminSectionTitle(stringResource(R.string.admin_dashboard_zone_status))
        AdminPanel {
            AdminStatusRow(stringResource(R.string.admin_parking_name), stringResource(R.string.admin_dashboard_zone_summary),
                stringResource(R.string.admin_dashboard_occupancy_value), onOpenOccupancy)
            AdminStatusRow(stringResource(R.string.admin_dashboard_reserved_spots), stringResource(R.string.admin_dashboard_reserved_summary),
                stringResource(R.string.admin_dashboard_balances_value), onUnavailableAction)
            AdminStatusRow(stringResource(R.string.admin_dashboard_sensor_health), stringResource(R.string.admin_dashboard_sensor_summary),
                stringResource(R.string.admin_dashboard_sensor_value), onUnavailableAction)
        }
        AdminSectionTitle(stringResource(R.string.admin_dashboard_recent_alerts))
        AdminPanel {
            AdminStatusRow(stringResource(R.string.admin_dashboard_sensor_offline), stringResource(R.string.admin_dashboard_offline_location),
                stringResource(R.string.admin_dashboard_high_priority), onUnavailableAction)
            AdminStatusRow(stringResource(R.string.admin_dashboard_reservation_conflict), stringResource(R.string.admin_dashboard_conflict_location),
                stringResource(R.string.admin_dashboard_medium_priority), onUnavailableAction)
        }
    }
}

@Composable
private fun DashboardMetric(value: String, label: String, color: Color, modifier: Modifier,
    onClick: () -> Unit, progress: Float? = null) {
    Surface(
        modifier = modifier.heightIn(min = 84.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface, border = BorderStroke(1.dp, AdminCardOutline),
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(value, color = color, style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, lineHeight = 30.sp))
            Text(label, style = MaterialTheme.typography.bodySmall.copy(lineHeight = 19.sp))
            progress?.let {
                LinearProgressIndicator(progress = { it }, modifier = Modifier.fillMaxWidth().height(5.dp),
                    color = MaterialTheme.colorScheme.primary, trackColor = Color.Transparent,
                    gapSize = 0.dp, drawStopIndicator = {})
            }
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun AdminDashboardPreview() {
    SpotGoTheme { AdminDashboardScreen({}, {}) }
}
