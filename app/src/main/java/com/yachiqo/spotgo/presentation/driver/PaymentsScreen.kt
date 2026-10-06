package com.yachiqo.spotgo.presentation.driver

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.driver.components.*
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

@Composable
fun PaymentsScreen(onProfile: () -> Unit, onUnavailableAction: () -> Unit) {
    DriverPage(stringResource(R.string.driver_payments), stringResource(R.string.payments_subtitle), onProfile) {
        DriverPanel {
            Row(Modifier.fillMaxWidth().heightIn(min = 104.dp).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(stringResource(R.string.payments_balance), style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary)
                    Text(stringResource(R.string.payments_balance_value), style = MaterialTheme.typography.headlineMedium)
                }
                StatusBadge(stringResource(R.string.payments_up_to_date))
            }
        }
        DriverSectionTitle(stringResource(R.string.payments_methods))
        DriverPanel(outlined = true) {
            ListItem(
                headlineContent = { Text(stringResource(R.string.payments_card), style = MaterialTheme.typography.titleMedium) },
                supportingContent = { Text(stringResource(R.string.payments_card_details), style = MaterialTheme.typography.bodyMedium) },
                trailingContent = { Text(stringResource(R.string.payments_chevron)) },
                modifier = Modifier.heightIn(min = 80.dp).clickable(onClick = onUnavailableAction),
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            )
        }
        DriverOutlineButton(stringResource(R.string.payments_add_method), onUnavailableAction)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { DriverSectionTitle(stringResource(R.string.payments_transactions)) }
            TextButton(onClick = onUnavailableAction) {
                Text(stringResource(R.string.payments_see_all), style = MaterialTheme.typography.bodySmall)
            }
        }
        DriverPanel {
            TransactionRow(stringResource(R.string.driver_parking_name), stringResource(R.string.payments_transaction_today),
                stringResource(R.string.zone_total_value), onUnavailableAction)
            TransactionRow(stringResource(R.string.reservations_larco), stringResource(R.string.payments_transaction_larco),
                stringResource(R.string.payments_transaction_larco_price), onUnavailableAction)
            TransactionRow(stringResource(R.string.payments_san_borja), stringResource(R.string.payments_transaction_san_borja),
                stringResource(R.string.payments_transaction_san_borja_price), onUnavailableAction)
        }
    }
}

@Composable
private fun TransactionRow(title: String, subtitle: String, amount: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
        supportingContent = { Text(subtitle, style = MaterialTheme.typography.bodyMedium) },
        trailingContent = { Text(amount, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier.heightIn(min = 72.dp).clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    )
}

@Preview(widthDp = 412, heightDp = 915)
@Composable
private fun PaymentsPreview() {
    SpotGoTheme { PaymentsScreen({}, {}) }
}
