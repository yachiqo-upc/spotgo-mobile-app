package com.yachiqo.spotgo.presentation.parking_admin

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FeaturePlaceholder
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.presentation.common.RoleScaffold
import com.yachiqo.spotgo.ui.theme.SpotGoTheme

enum class ParkingAdminDestination(@get:StringRes val label: Int, @get:DrawableRes val icon: Int) {
    DASHBOARD(R.string.admin_dashboard, R.drawable.ic_dashboard),
    LIVE_MAP(R.string.admin_live_map, R.drawable.ic_live_map),
    ALERTS(R.string.admin_alerts, R.drawable.ic_alerts),
    MORE(R.string.admin_more, R.drawable.ic_more),
}

@Composable
fun ParkingAdminLayout(
    onBackToLogin: () -> Unit,
    content: @Composable (ParkingAdminDestination) -> Unit = { destination ->
        FeaturePlaceholder(stringResource(destination.label))
    },
) {
    var destination by rememberSaveable { mutableStateOf(ParkingAdminDestination.DASHBOARD) }

    RoleScaffold(
        roleLabel = stringResource(R.string.role_parking_admin),
        onBackToLogin = onBackToLogin,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                ParkingAdminDestination.entries.forEach { item ->
                    NavigationBarItem(
                        selected = item == destination,
                        onClick = { destination = item },
                        icon = {
                            FigmaIcon(item.icon)
                        },
                        label = { Text(stringResource(item.label), style = MaterialTheme.typography.labelMedium) },
                    )
                }
            }
        },
        content = { content(destination) },
    )
}

@Preview(name = "PARKING_ADMIN · Base", widthDp = 412, heightDp = 915)
@Composable
private fun ParkingAdminLayoutPreview() {
    SpotGoTheme { ParkingAdminLayout(onBackToLogin = {}) }
}
