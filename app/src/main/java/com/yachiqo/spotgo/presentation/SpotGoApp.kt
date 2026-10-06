package com.yachiqo.spotgo.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yachiqo.spotgo.presentation.driver.DriverLayout
import com.yachiqo.spotgo.presentation.login.LoginScreen
import com.yachiqo.spotgo.presentation.login.RegisterScreen
import com.yachiqo.spotgo.presentation.parking_admin.ParkingAdminLayout

// UI destinations only. There is no session, user entity, or authentication yet.
private enum class AppDestination { LOGIN, REGISTER, DRIVER, PARKING_ADMIN }

@Composable
fun SpotGoApp() {
    var destination by rememberSaveable { mutableStateOf(AppDestination.LOGIN) }
    val backToLogin = { destination = AppDestination.LOGIN }

    BackHandler(enabled = destination != AppDestination.LOGIN, onBack = backToLogin)

    Surface(modifier = Modifier.fillMaxSize()) {
        when (destination) {
            AppDestination.LOGIN -> LoginScreen(
                onOpenDriver = { destination = AppDestination.DRIVER },
                onOpenParkingAdmin = { destination = AppDestination.PARKING_ADMIN },
                onCreateAccount = { destination = AppDestination.REGISTER },
            )
            AppDestination.REGISTER -> RegisterScreen(onBackToLogin = backToLogin)
            AppDestination.DRIVER -> DriverLayout(onBackToLogin = backToLogin)
            AppDestination.PARKING_ADMIN -> ParkingAdminLayout(onBackToLogin = backToLogin)
        }
    }
}
