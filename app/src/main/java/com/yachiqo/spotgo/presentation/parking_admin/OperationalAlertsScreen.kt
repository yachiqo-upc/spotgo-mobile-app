package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.parking_admin.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun OperationalAlertsScreen(onUnavailableAction: () -> Unit) {
    AdminPage(stringResource(R.string.admin_alerts_title), stringResource(R.string.admin_alerts_subtitle),
        onAccount = onUnavailableAction, contentSpacing = 12.dp, contentTopPadding = 14.dp) {
        AdminFilterRow(listOf(R.string.admin_occupancy_all, R.string.admin_alerts_critical,
            R.string.admin_alerts_sensor, R.string.admin_alerts_payment), onUnavailableAction)
        AlertCard(R.string.admin_alerts_sensor_offline, R.string.admin_alerts_sensor_body, R.string.admin_alerts_two_minutes,
            R.string.admin_alerts_high, AdminError, Color(0xFF3C222B), R.drawable.admin_alert_high_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_overstay, R.string.admin_alerts_overstay_body, R.string.admin_alerts_eight_minutes,
            R.string.admin_alerts_medium, AdminWarning, Color(0xFF3B3021), R.drawable.admin_alert_medium_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_payment_issue, R.string.admin_alerts_payment_body, R.string.admin_alerts_twenty_one_minutes,
            R.string.admin_alerts_medium, AdminWarning, Color(0xFF3B3021), R.drawable.admin_alert_medium_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_spot_unavailable, R.string.admin_alerts_unavailable_body, R.string.admin_alerts_thirty_six_minutes,
            R.string.admin_alerts_info, MaterialTheme.colorScheme.primary, Color(0xFF23202D),
            R.drawable.admin_alert_info_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_unauthorized, R.string.admin_alerts_unauthorized_body, R.string.admin_alerts_three_minutes,
            R.string.admin_alerts_high, AdminError, Color(0xFF3C222B), R.drawable.admin_alert_high_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_conflict, R.string.admin_alerts_conflict_body, R.string.admin_alerts_six_minutes,
            R.string.admin_alerts_high, AdminError, Color(0xFF3C222B), R.drawable.admin_alert_high_dot, onUnavailableAction)
        AlertCard(R.string.admin_alerts_capacity, R.string.admin_alerts_capacity_body, R.string.admin_alerts_one_minute,
            R.string.admin_alerts_high, AdminError, Color(0xFF3C222B), R.drawable.admin_alert_high_dot, onUnavailableAction)
    }
}

@Composable
private fun AlertCard(title: Int, body: Int, time: Int, priority: Int, color: Color,
    badgeBackground: Color, dot: Int, onClick: () -> Unit) {
    Surface(Modifier.fillMaxWidth().heightIn(min = 126.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface, border = BorderStroke(1.dp, AdminCardOutline)) {
        Column(Modifier.padding(horizontal = 14.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row(Modifier.fillMaxWidth().heightIn(min = 28.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(title), Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
                AdminStatusBadge(stringResource(priority), color, badgeBackground, dot, minLabelWidth = 62.dp)
            }
            Text(stringResource(body), color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium)
            Text(stringResource(time), color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun OperationalAlertsPreview() {
    SpotGoTheme { Surface { OperationalAlertsScreen({}) } }
}
