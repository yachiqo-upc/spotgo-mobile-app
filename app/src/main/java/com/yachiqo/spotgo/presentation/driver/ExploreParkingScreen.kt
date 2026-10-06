package com.yachiqo.spotgo.presentation.driver

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.presentation.driver.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreParkingScreen(onOpenZone: () -> Unit, onProfile: () -> Unit, onUnavailableAction: () -> Unit) {
    DriverPage(stringResource(R.string.explore_title), stringResource(R.string.driver_location), onProfile) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(query = "", onQueryChange = {}, onSearch = {},
                    expanded = false, onExpandedChange = { if (it) onUnavailableAction() },
                    placeholder = { Text(stringResource(R.string.explore_search)) },
                    leadingIcon = { FigmaIcon(R.drawable.ic_explore) })
            },
            expanded = false, onExpandedChange = {}, modifier = Modifier.fillMaxWidth(),
            windowInsets = WindowInsets(0), colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        ) {}
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).height(40.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp), verticalAlignment = Alignment.CenterVertically) {
            listOf(R.string.driver_available, R.string.driver_covered, R.string.driver_ev, R.string.explore_saved)
                .forEachIndexed { index, label ->
                    FilterChip(selected = index == 0, onClick = onUnavailableAction,
                        label = { Text(stringResource(label), style = MaterialTheme.typography.bodyMedium) })
                }
        }
        ExploreMap(onOpenZone, onUnavailableAction)
    }
}

@Composable
private fun ExploreMap(onOpenZone: () -> Unit, onUnavailableAction: () -> Unit) {
    Surface(shape = RoundedCornerShape(24.dp), color = DriverMapBackground,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), modifier = Modifier.fillMaxWidth()) {
        BoxWithConstraints(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp))) {
            val scale = maxWidth / 376.dp
            Column {
                Box(Modifier.fillMaxWidth().height(349.dp * scale)) {
                    ParkingMapBase()
                    Image(painterResource(R.drawable.map_walking_route), null,
                        Modifier.offset(106.dp * scale, 218.dp * scale).size(110.dp * scale, 93.dp * scale))
                    Image(painterResource(R.drawable.map_location_halo), null,
                        Modifier.offset(92.dp * scale, 295.dp * scale).size(30.dp * scale))
                    Image(painterResource(R.drawable.map_location_dot), null,
                        Modifier.offset(101.dp * scale, 304.dp * scale).size(12.dp * scale))
                    ParkingMarker(stringResource(R.string.explore_marker_six), false, onOpenZone,
                        Modifier.offset(23.dp * scale, 87.dp * scale))
                    ParkingMarker(stringResource(R.string.explore_marker_seven), false, onOpenZone,
                        Modifier.offset(180.dp * scale, 60.dp * scale))
                    ParkingMarker(stringResource(R.string.explore_marker_eight), true, onOpenZone,
                        Modifier.offset(165.dp * scale, 179.dp * scale))
                    ParkingMarker(stringResource(R.string.explore_marker_five), false, onOpenZone,
                        Modifier.offset(20.dp * scale, 221.dp * scale))
                    ParkingMarker(stringResource(R.string.explore_marker_ten), false, onOpenZone,
                        Modifier.offset(282.dp * scale, 292.dp * scale))
                    Text(stringResource(R.string.explore_walking_distance),
                        Modifier.offset(17.dp * scale, 332.dp * scale), style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Surface(Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer) {
                        Row(Modifier.padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(stringResource(R.string.explore_last_updated), Modifier.weight(1f),
                                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            TextButton(onClick = onUnavailableAction, contentPadding = PaddingValues(horizontal = 10.dp)) {
                                Text(stringResource(R.string.explore_refresh), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
                Surface(Modifier.fillMaxWidth().padding(horizontal = 13.dp).padding(bottom = 14.dp),
                    shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text(stringResource(R.string.driver_parking_name), style = MaterialTheme.typography.titleMedium)
                                Text(stringResource(R.string.explore_parking_distance), style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text(stringResource(R.string.driver_hourly_price), color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp))
                                Text(stringResource(R.string.driver_per_hour), style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            StatusBadge(stringResource(R.string.explore_spots))
                            FilterChip(false, onUnavailableAction, { Text(stringResource(R.string.driver_covered)) })
                            FilterChip(false, onUnavailableAction, { Text(stringResource(R.string.driver_ev)) })
                            Spacer(Modifier.weight(1f))
                            FavoriteButton(false, onUnavailableAction)
                        }
                        Button(onOpenZone, Modifier.fillMaxWidth().heightIn(min = 40.dp), shape = RoundedCornerShape(16.dp)) {
                            Text(stringResource(R.string.explore_view_parking))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ParkingMarker(price: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    Surface(onClick = onClick, modifier = modifier.size(82.dp, 36.dp), shape = RoundedCornerShape(12.dp),
        color = if (selected) DriverAvailable else MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(1.dp, if (selected) DriverAvailable else MaterialTheme.colorScheme.outline)) {
        Row(Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.explore_parking_marker), style = MaterialTheme.typography.titleMedium,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else DriverAvailable)
            Text(price, style = MaterialTheme.typography.labelSmall,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun ExploreParkingPreview() {
    SpotGoTheme { ExploreParkingScreen({}, {}, {}) }
}
