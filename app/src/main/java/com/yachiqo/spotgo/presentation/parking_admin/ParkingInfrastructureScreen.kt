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
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.parking_admin.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun ParkingInfrastructureScreen(onOpenUpload: () -> Unit, onOpenMap: () -> Unit, onUnavailableAction: () -> Unit,
    onBackToLogin: () -> Unit) {
    AdminPage(stringResource(R.string.admin_infrastructure_title), stringResource(R.string.admin_infrastructure_subtitle),
        onAccount = onBackToLogin, contentSpacing = 16.dp, contentTopPadding = 18.dp) {
        AdminSearchBar(R.string.admin_infrastructure_search, onUnavailableAction)
        AdminPanel(outlined = true) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Row(Modifier.fillMaxWidth().heightIn(min = 46.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(stringResource(R.string.admin_parking_name), style = MaterialTheme.typography.titleMedium)
                        Text(stringResource(R.string.admin_infrastructure_location),
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    AdminStatusBadge(stringResource(R.string.admin_infrastructure_synced), AdminAvailable,
                        Color(0xFF18332A), R.drawable.admin_synced_status_dot, minLabelWidth = 54.dp)
                }
                AdminFileRow(stringResource(R.string.admin_floor_plan_file), stringResource(R.string.admin_infrastructure_updated))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onOpenUpload, modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                        shape = RoundedCornerShape(16.dp), contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = Color(0xFF23202D))) {
                        Text(stringResource(R.string.admin_infrastructure_replace), style = MaterialTheme.typography.bodyLarge)
                    }
                    OutlinedButton(onClick = onOpenMap, modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                        shape = RoundedCornerShape(16.dp), contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = Color(0xFF23202D))) {
                        Text(stringResource(R.string.admin_infrastructure_open_map), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
        AdminSectionTitle(stringResource(R.string.admin_infrastructure_inventory))
        AdminPanel {
            AdminStatusRow(stringResource(R.string.admin_infrastructure_standard), stringResource(R.string.admin_infrastructure_standard_summary),
                stringResource(R.string.admin_infrastructure_standard_value), onUnavailableAction)
            AdminStatusRow(stringResource(R.string.admin_infrastructure_accessible_ev), stringResource(R.string.admin_infrastructure_accessible_summary),
                stringResource(R.string.admin_infrastructure_accessible_value), onUnavailableAction)
            AdminStatusRow(stringResource(R.string.admin_infrastructure_sensors), stringResource(R.string.admin_infrastructure_sensors_summary),
                stringResource(R.string.admin_infrastructure_sensors_value), onUnavailableAction)
        }
        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
            Text(stringResource(R.string.admin_infrastructure_note), Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun ParkingInfrastructurePreview() {
    SpotGoTheme { Surface { ParkingInfrastructureScreen({}, {}, {}, {}) } }
}
