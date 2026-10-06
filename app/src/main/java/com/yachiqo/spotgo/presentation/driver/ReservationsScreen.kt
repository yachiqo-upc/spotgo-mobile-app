package com.yachiqo.spotgo.presentation.driver

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.driver.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(onProfile: () -> Unit, onUnavailableAction: () -> Unit) {
    DriverPage(stringResource(R.string.reservations_title), stringResource(R.string.reservations_subtitle), onProfile) {
        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
            listOf(R.string.reservations_upcoming, R.string.reservations_completed, R.string.reservations_cancelled)
                .forEachIndexed { index, label ->
                    SegmentedButton(selected = index == 0, onClick = onUnavailableAction,
                        icon = {}, shape = SegmentedButtonDefaults.itemShape(index, 3)) {
                        Text(stringResource(label), style = MaterialTheme.typography.bodySmall,
                            maxLines = 1, softWrap = false)
                    }
                }
        }
        ReservationCard(
            title = stringResource(R.string.driver_parking_name), location = stringResource(R.string.driver_location),
            date = stringResource(R.string.zone_today), time = stringResource(R.string.confirmation_time_value),
            price = stringResource(R.string.zone_total_value), status = stringResource(R.string.reservations_confirmed),
            confirmed = true, onViewDetails = onUnavailableAction,
        )
        ReservationCard(
            title = stringResource(R.string.reservations_larco), location = stringResource(R.string.reservations_miraflores),
            date = stringResource(R.string.reservations_tomorrow), time = stringResource(R.string.reservations_evening_time),
            price = stringResource(R.string.reservations_evening_price), status = stringResource(R.string.reservations_upcoming),
            confirmed = false, onViewDetails = onUnavailableAction,
        )
        Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 13.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(stringResource(R.string.reservations_before_you_go), style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.reservations_navigation_hint), style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun ReservationCard(title: String, location: String, date: String, time: String, price: String,
    status: String, confirmed: Boolean, onViewDetails: () -> Unit) {
    DriverPanel(outlined = true) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth().heightIn(min = 46.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(title, style = MaterialTheme.typography.titleMedium)
                    Text(location, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                StatusBadge(status, positive = confirmed)
            }
            Row(Modifier.fillMaxWidth().heightIn(min = 48.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(date, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    Text(time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(price, style = MaterialTheme.typography.labelLarge)
                    Text(stringResource(R.string.confirmation_vehicle_value), style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = onViewDetails, shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)) {
                    Text(stringResource(R.string.reservations_view_details))
                }
            }
        }
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun ReservationsPreview() {
    SpotGoTheme { ReservationsScreen({}, {}) }
}
