package com.yachiqo.spotgo.presentation.driver

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

// Presentation destinations, not domain entities.
enum class DriverDestination(@get:StringRes val label: Int, @get:DrawableRes val icon: Int) {
    EXPLORE(R.string.driver_explore, R.drawable.ic_explore),
    RESERVATIONS(R.string.driver_reservations, R.drawable.ic_reservations),
    PAYMENTS(R.string.driver_payments, R.drawable.ic_payments),
    PROFILE(R.string.driver_profile, R.drawable.ic_profile),
}

@Composable
fun DriverLayout(
    onBackToLogin: () -> Unit,
    content: @Composable (DriverDestination) -> Unit = { destination ->
        FeaturePlaceholder(stringResource(destination.label))
    },
) {
    var destination by rememberSaveable { mutableStateOf(DriverDestination.EXPLORE) }

    RoleScaffold(
        roleLabel = stringResource(R.string.role_driver),
        onBackToLogin = onBackToLogin,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                DriverDestination.entries.forEach { item ->
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

@Preview(name = "DRIVER · Base", widthDp = 412, heightDp = 915)
@Composable
private fun DriverLayoutPreview() {
    SpotGoTheme { DriverLayout(onBackToLogin = {}) }
}
