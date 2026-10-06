package com.yachiqo.spotgo.presentation.parking_admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.parking_admin.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveOccupancyScreen(onUnavailableAction: () -> Unit, onBackToLogin: () -> Unit) {
    AdminPage(stringResource(R.string.admin_occupancy_title), stringResource(R.string.admin_occupancy_subtitle),
        onAccount = onBackToLogin, contentSpacing = 16.dp, contentTopPadding = 18.dp) {
        AdminSearchBar(R.string.admin_occupancy_search, onUnavailableAction)
        AdminFilterRow(listOf(R.string.admin_occupancy_all, R.string.admin_available, R.string.admin_occupancy_issues), onUnavailableAction)
        OccupancyMap(onUnavailableAction)
    }
}

@Composable
private fun OccupancyMap(onUnavailableAction: () -> Unit) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
        color = AdminMapBackground, contentColor = MaterialTheme.colorScheme.onSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column {
            AdminFloorPlan(onUnavailableAction)
            AdminPanel(Modifier.padding(horizontal = 13.dp).padding(top = 7.dp),
                containerColor = Color(0xFF211F2B)) {
                Column(Modifier.fillMaxWidth().heightIn(min = 142.dp).padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Row(Modifier.fillMaxWidth().heightIn(min = 38.dp),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.admin_occupancy_zone), Modifier.weight(1f),
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp, lineHeight = 25.sp))
                        Surface(color = Color(0xFF18332A), shape = RoundedCornerShape(100.dp)) {
                            Row(Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Image(painterResource(R.drawable.admin_live_status_dot), null, Modifier.size(8.dp))
                                Text(stringResource(R.string.admin_occupancy_live), Modifier.widthIn(min = 64.dp),
                                    color = AdminAvailable, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OccupancyStat(stringResource(R.string.admin_occupancy_occupied_value), stringResource(R.string.admin_occupancy_occupied),
                            MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                        OccupancyStat(stringResource(R.string.admin_occupancy_available_value), stringResource(R.string.admin_occupancy_available_label),
                            AdminAvailable, Modifier.weight(1f))
                        OccupancyStat(stringResource(R.string.admin_occupancy_updated_value), stringResource(R.string.admin_occupancy_last_update),
                            MaterialTheme.colorScheme.onSurface, Modifier.weight(1f), alignedEnd = true)
                    }
                }
            }
            Text(stringResource(R.string.admin_occupancy_updated_hint),
                Modifier.padding(start = 17.dp, top = 4.dp, bottom = 2.dp),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun OccupancyStat(value: String, label: String, color: Color, modifier: Modifier, alignedEnd: Boolean = false) {
    Column(modifier, horizontalAlignment = if (alignedEnd) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(value, style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp), color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = if (alignedEnd) TextAlign.End else TextAlign.Start)
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun LiveOccupancyPreview() {
    SpotGoTheme { Surface { LiveOccupancyScreen({}, {}) } }
}
