package com.yachiqo.spotgo.presentation.parking_admin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FeaturePlaceholder
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.ui.theme.SpotGoTheme
import kotlinx.coroutines.launch

enum class ParkingAdminDestination(@get:StringRes val label: Int, @get:DrawableRes val icon: Int) {
    DASHBOARD(R.string.admin_dashboard, R.drawable.ic_dashboard),
    LIVE_MAP(R.string.admin_occupancy, R.drawable.ic_live_map),
    ALERTS(R.string.admin_alerts, R.drawable.ic_alerts),
    MORE(R.string.admin_more, R.drawable.ic_more),
}

@Composable
fun ParkingAdminLayout(
    onBackToLogin: () -> Unit,
    content: (@Composable (ParkingAdminDestination) -> Unit)? = null,
) {
    var destination by rememberSaveable { mutableStateOf(ParkingAdminDestination.DASHBOARD) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val unavailableMessage = stringResource(R.string.admin_action_unavailable)
    val unavailableAction: () -> Unit = { scope.launch { snackbar.showSnackbar(unavailableMessage) }; Unit }
    BackHandler(onBack = onBackToLogin)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).height(64.dp),
                windowInsets = WindowInsets(0), containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                ParkingAdminDestination.entries.forEach { item ->
                    NavigationBarItem(
                        selected = item == destination,
                        enabled = item == ParkingAdminDestination.DASHBOARD || item == ParkingAdminDestination.LIVE_MAP,
                        onClick = { destination = item },
                        icon = {
                            FigmaIcon(item.icon)
                        },
                        label = { Text(stringResource(item.label), style = MaterialTheme.typography.labelSmall,
                            maxLines = 1, softWrap = false) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            if (content != null) content(destination)
            else when (destination) {
                ParkingAdminDestination.DASHBOARD -> AdminDashboardScreen(
                    onOpenOccupancy = { destination = ParkingAdminDestination.LIVE_MAP },
                    onUnavailableAction = unavailableAction,
                )
                ParkingAdminDestination.LIVE_MAP -> LiveOccupancyScreen(onUnavailableAction = unavailableAction)
                else -> FeaturePlaceholder(stringResource(destination.label))
            }
        }
    }
}

@Preview(name = "Parking Admin", widthDp = 412, heightDp = 915)
@Composable
private fun ParkingAdminLayoutPreview() {
    SpotGoTheme { ParkingAdminLayout(onBackToLogin = {}) }
}
