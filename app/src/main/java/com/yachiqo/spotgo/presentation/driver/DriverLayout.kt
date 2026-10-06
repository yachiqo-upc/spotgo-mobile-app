package com.yachiqo.spotgo.presentation.driver

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yachiqo.spotgo.R
import com.yachiqo.spotgo.presentation.common.FeaturePlaceholder
import com.yachiqo.spotgo.presentation.common.FigmaIcon
import com.yachiqo.spotgo.ui.theme.SpotGoTheme
import kotlinx.coroutines.launch

// Presentation destinations, not domain entities.
enum class DriverDestination(@get:StringRes val label: Int, @get:DrawableRes val icon: Int) {
    EXPLORE(R.string.driver_explore, R.drawable.ic_explore),
    RESERVATIONS(R.string.driver_reservations, R.drawable.ic_reservations),
    PAYMENTS(R.string.driver_payments, R.drawable.ic_payments),
    PROFILE(R.string.driver_profile, R.drawable.ic_profile),
}

private enum class DriverFlow { MAIN, ZONE_DETAILS, CONFIRMED }

@Composable
fun DriverLayout(
    onBackToLogin: () -> Unit,
    content: (@Composable (DriverDestination) -> Unit)? = null,
) {
    var destination by rememberSaveable { mutableStateOf(DriverDestination.EXPLORE) }
    var flow by rememberSaveable { mutableStateOf(DriverFlow.MAIN) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val unavailableMessage = stringResource(R.string.driver_action_unavailable)
    val unavailableAction: () -> Unit = { scope.launch { snackbar.showSnackbar(unavailableMessage) }; Unit }
    BackHandler(enabled = flow != DriverFlow.MAIN) {
        flow = if (flow == DriverFlow.CONFIRMED) DriverFlow.ZONE_DETAILS else DriverFlow.MAIN
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            if (flow != DriverFlow.ZONE_DETAILS) {
            NavigationBar(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).height(64.dp),
                windowInsets = WindowInsets(0), containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                DriverDestination.entries.forEach { item ->
                    NavigationBarItem(
                        selected = item == if (flow == DriverFlow.CONFIRMED) DriverDestination.RESERVATIONS else destination,
                        onClick = { destination = item; flow = DriverFlow.MAIN },
                        icon = {
                            FigmaIcon(item.icon)
                        },
                        label = { Text(stringResource(item.label), style = MaterialTheme.typography.labelMedium) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                    )
                }
            }
            }
        },
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            if (flow == DriverFlow.ZONE_DETAILS) ZoneDetailsScreen(
                onBack = { flow = DriverFlow.MAIN }, onContinue = { flow = DriverFlow.CONFIRMED },
                onUnavailableAction = unavailableAction,
            )
            else if (flow == DriverFlow.CONFIRMED) ReservationConfirmedScreen(
                onMyReservations = { destination = DriverDestination.RESERVATIONS; flow = DriverFlow.MAIN },
                onUnavailableAction = unavailableAction,
            )
            else if (content != null) content(destination)
            else when (destination) {
                DriverDestination.EXPLORE -> ExploreParkingScreen(
                    onOpenZone = { flow = DriverFlow.ZONE_DETAILS },
                    onProfile = { destination = DriverDestination.PROFILE },
                    onUnavailableAction = unavailableAction,
                )
                DriverDestination.RESERVATIONS -> ReservationsScreen(
                    onProfile = { destination = DriverDestination.PROFILE },
                    onUnavailableAction = unavailableAction,
                )
                else -> FeaturePlaceholder(stringResource(destination.label))
            }
        }
    }
}

@Preview(name = "Driver", widthDp = 412, heightDp = 915)
@Composable
private fun DriverLayoutPreview() {
    SpotGoTheme { DriverLayout(onBackToLogin = {}) }
}
