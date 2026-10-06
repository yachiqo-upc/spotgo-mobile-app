package com.yachiqo.spotgo.presentation.driver

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.presentation.driver.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoneDetailsScreen(onBack: () -> Unit, onContinue: () -> Unit, onUnavailableAction: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(R.string.zone_title)) },
            windowInsets = WindowInsets(0), navigationIcon = {
                IconButton(onClick = onBack) { FigmaIcon(R.drawable.ic_back, stringResource(R.string.zone_back)) }
            })
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp)
            .padding(top = 14.dp, bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
            ZoneMap(onUnavailableAction)
            Row(Modifier.fillMaxWidth().heightIn(min = 42.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.driver_parking_name), Modifier.weight(1f), style = MaterialTheme.typography.titleLarge)
                StatusBadge(stringResource(R.string.driver_available))
            }
            Text(stringResource(R.string.driver_address), style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(Modifier.fillMaxWidth().heightIn(min = 78.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ZoneStat(stringResource(R.string.zone_available_count), stringResource(R.string.zone_available_spots),
                    DriverAvailable, Modifier.weight(1f))
                ZoneStat(stringResource(R.string.driver_hourly_price), stringResource(R.string.driver_per_hour),
                    MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                ZoneStat(stringResource(R.string.zone_closing_time), stringResource(R.string.zone_open_until),
                    MaterialTheme.colorScheme.onSurface, Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).height(40.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp), verticalAlignment = Alignment.CenterVertically) {
                listOf(R.string.driver_covered, R.string.driver_ev, R.string.zone_accessible).forEach { label ->
                    FilterChip(false, onUnavailableAction, { Text(stringResource(label)) })
                }
            }
            DriverSectionTitle(stringResource(R.string.zone_reserve_title))
            StaticReservationField(stringResource(R.string.zone_vehicle), stringResource(R.string.zone_vehicle_value),
                trailingIcon = { Text(stringResource(R.string.zone_dropdown), color = MaterialTheme.colorScheme.onSurfaceVariant) })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StaticReservationField(stringResource(R.string.zone_date), stringResource(R.string.zone_today), Modifier.weight(1f))
                StaticReservationField(stringResource(R.string.zone_start_time), stringResource(R.string.zone_start_time_value),
                    Modifier.weight(1f))
            }
            StaticReservationField(stringResource(R.string.zone_duration), stringResource(R.string.zone_duration_value))
            Row(Modifier.fillMaxWidth().heightIn(min = 38.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.zone_total), Modifier.weight(1f), style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(stringResource(R.string.zone_total_value), style = MaterialTheme.typography.titleLarge)
            }
            DriverPrimaryButton(stringResource(R.string.zone_continue), onContinue)
        }
    }
}

@Composable
private fun ZoneMap(onUnavailableAction: () -> Unit) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = DriverMapBackground,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        BoxWithConstraints(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))) {
            val scale = maxWidth / 376.dp
            Box(Modifier.fillMaxWidth().height(206.dp * scale)) {
                ParkingMapBase()
                Surface(Modifier.offset(152.dp * scale, 68.dp * scale).size(155.dp, 36.dp),
                    color = DriverAvailable, shape = RoundedCornerShape(12.dp)) {
                    Row(Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.explore_parking_marker), style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary)
                        Text(stringResource(R.string.zone_marker_name), style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
                FavoriteButton(false, onUnavailableAction, Modifier.align(Alignment.TopEnd).padding(top = 12.dp, end = 14.dp))
                Surface(Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(horizontal = 13.dp, vertical = 16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(14.dp)) {
                    Row(Modifier.padding(start = 10.dp).heightIn(min = 48.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(R.string.zone_map_distance), Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        TextButton(onClick = onUnavailableAction, contentPadding = PaddingValues(horizontal = 10.dp)) {
                            Text(stringResource(R.string.zone_directions), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ZoneStat(value: String, label: String, color: Color, modifier: Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(horizontal = 10.dp, vertical = 11.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(value, style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp), color = color)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun StaticReservationField(label: String, value: String, modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null) {
    OutlinedTextField(value = value, onValueChange = {}, readOnly = true, singleLine = true,
        label = { Text(label) }, trailingIcon = trailingIcon, modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer))
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun ZoneDetailsPreview() {
    SpotGoTheme { ZoneDetailsScreen({}, {}, {}) }
}
