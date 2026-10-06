package com.yachiqo.spotgo.presentation.driver

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.driver.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun ReservationConfirmedScreen(onMyReservations: () -> Unit, onUnavailableAction: () -> Unit) {
    DriverPage(stringResource(R.string.confirmation_title)) {
        Row(Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(Modifier.size(44.dp), shape = RoundedCornerShape(22.dp), color = DriverAvailable) {
                Box(contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.confirmation_check), color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 25.sp))
                }
            }
            Text(stringResource(R.string.confirmation_success), style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
        }
        DriverPanel(outlined = true) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row(Modifier.fillMaxWidth().heightIn(min = 56.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(stringResource(R.string.confirmation_booking_code), style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(stringResource(R.string.confirmation_booking_value), style = MaterialTheme.typography.titleMedium.copy(fontSize = 19.sp),
                            color = MaterialTheme.colorScheme.primary)
                    }
                    StatusBadge(stringResource(R.string.confirmation_paid))
                }
                ReceiptRow(stringResource(R.string.confirmation_parking_zone), stringResource(R.string.driver_parking_name))
                ReceiptRow(stringResource(R.string.confirmation_spot), stringResource(R.string.confirmation_spot_value))
                ReceiptRow(stringResource(R.string.zone_date), stringResource(R.string.zone_today))
                ReceiptRow(stringResource(R.string.confirmation_time), stringResource(R.string.confirmation_time_value))
                ReceiptRow(stringResource(R.string.zone_vehicle), stringResource(R.string.confirmation_vehicle_value))
                ReceiptRow(stringResource(R.string.confirmation_amount_paid), stringResource(R.string.zone_total_value))
            }
        }
        Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(20.dp)) {
            Row(Modifier.fillMaxWidth().heightIn(min = 128.dp).padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Surface(Modifier.size(96.dp), color = Color.White, shape = RoundedCornerShape(12.dp)) {
                    Image(painterResource(R.drawable.booking_qr), stringResource(R.string.confirmation_qr_description), Modifier.size(96.dp))
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(stringResource(R.string.confirmation_entry_title), style = MaterialTheme.typography.bodySmall)
                    Text(stringResource(R.string.confirmation_entry_hint), style = MaterialTheme.typography.bodySmall)
                    Text(stringResource(R.string.confirmation_entry_booking), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        DriverPrimaryButton(stringResource(R.string.confirmation_open_navigation), onUnavailableAction)
        DriverOutlineButton(stringResource(R.string.confirmation_my_reservations), onMyReservations)
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().heightIn(min = 31.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(label, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End)
    }
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun ReservationConfirmedPreview() {
    SpotGoTheme { ReservationConfirmedScreen({}, {}) }
}
