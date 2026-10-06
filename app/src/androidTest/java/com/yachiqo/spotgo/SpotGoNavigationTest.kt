package com.yachiqo.spotgo

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.pressBack
import org.junit.Rule
import org.junit.Test

class SpotGoNavigationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginShowsPlaceholdersAndCanClearPassword() {
        compose.onNodeWithText("you@example.com").assertIsDisplayed()
        compose.onNodeWithText("Enter your password").assertIsDisplayed()
        compose.onNode(hasSetTextAction() and hasText("Password")).performTextInput("sprint1-preview")
        compose.onNodeWithContentDescription("Clear password").performClick()
        compose.onNodeWithText("Enter your password").assertIsDisplayed()
    }

    @Test
    fun driverTabsAndSystemBackWorkWithoutCredentials() {
        compose.onNodeWithText("Driver").performScrollTo().performClick()
        compose.onNodeWithText("Reservations").performClick()
        compose.onAllNodesWithText("Reservations").assertCountEquals(2)
        compose.onNode(hasText("Reservations") and isSelectable()).assertIsSelected()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun adminTabsAndBackToLoginWorkWithoutCredentials() {
        compose.onNodeWithText("Parking Admin").performScrollTo().performClick()
        compose.onNodeWithText("Alerts").performClick()
        compose.onNodeWithText("Operational alerts").assertIsDisplayed()
        compose.onNode(hasText("Alerts") and isSelectable()).assertIsSelected()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun roleAndSelectedTabSurviveActivityRecreation() {
        compose.onNodeWithText("Driver").performScrollTo().performClick()
        compose.onNodeWithText("Profile").performClick()
        compose.activityRule.scenario.recreate()
        compose.onAllNodesWithText("Profile").assertCountEquals(2)
        compose.onNode(hasText("Profile") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Back to login").assertIsDisplayed()
    }
}
