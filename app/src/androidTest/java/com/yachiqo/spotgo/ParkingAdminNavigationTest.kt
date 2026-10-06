package com.yachiqo.spotgo

import android.os.ParcelFileDescriptor
import android.os.SystemClock
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.pressBack
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import org.junit.Rule
import org.junit.Test

class ParkingAdminNavigationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun dashboardOpensOccupancyAndAlertsRemainStatic() {
        openAdmin()
        compose.onNode(hasText("58%") and hasText("Occupancy")).performClick()
        compose.onNodeWithText("Live occupancy").assertIsDisplayed()
        compose.onNode(hasText("Occupancy") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Alerts").performClick()
        compose.onNodeWithText("Critical").performClick()
        compose.onNodeWithText("This action is not available yet.").assertIsDisplayed()
        compose.onNodeWithText("Sensor offline").assertIsDisplayed()
        compose.onNodeWithText("High Capacity").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Javier Prado Parking · 96% occupied. Alert triggers only above 95%.").assertIsDisplayed()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun moreDisablesUnfinishedDestinationsAndDismissesToPreviousTab() {
        openAdmin()
        compose.onNodeWithText("Alerts").performClick()
        openMore()
        listOf("My parking zone", "Occupancy reports", "Guest parking", "Billing & invoices").forEach {
            compose.onNodeWithText(it).assertIsNotEnabled()
        }
        compose.onNodeWithText("Parking infrastructure").assertIsEnabled()
        compose.onNodeWithText("Close").performClick()
        waitForMoreToClose()
        compose.onNodeWithText("Operational alerts").assertIsDisplayed()
        compose.onNode(hasText("Alerts") and isSelectable()).assertIsSelected()
        openMore()
        pressBack()
        waitForMoreToClose()
        compose.onNodeWithText("Operational alerts").assertIsDisplayed()
    }

    @Test
    fun uploadAndMapReturnToTheCorrectParentAfterRecreation() {
        openInfrastructure()
        compose.onNodeWithText("Replace floor plan").performScrollTo().performClick()
        compose.onNodeWithText("Assigned parking zone").assertIsDisplayed()
        compose.onNodeWithText("Choose file").performScrollTo().performClick()
        compose.onNodeWithText("This action is not available yet.").assertIsDisplayed()
        compose.onNodeWithText("JP-L1-floor-plan.png").assertIsDisplayed()
        compose.onNode(hasText("Upload floor plan") and hasClickAction()).performScrollTo().performClick()
        compose.waitUntil(5_000) { compose.onNodeWithText("Digital parking map").isDisplayed() }
        compose.onNodeWithText("Digital parking map").assertIsDisplayed()
        compose.onNodeWithText("SEN-JP-A06").performScrollTo().assertIsDisplayed()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Digital parking map").assertIsDisplayed()
        pressBack()
        compose.onNodeWithText("Add or replace a floor plan").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("Parking infrastructure").assertIsDisplayed()
        compose.onNode(hasText("More") and isSelectable()).assertIsSelected()
        pressBack()
        compose.onNodeWithText("Administrator dashboard").assertIsDisplayed()
    }

    @Test
    fun mapActionsDoNotPublishOrChangeTheDraft() {
        openInfrastructure()
        compose.onNodeWithText("Open map editor").performScrollTo().performClick()
        compose.onNodeWithText("8 draft spots").assertIsDisplayed()
        compose.onNodeWithText("Correct map manually").performScrollTo().performClick()
        compose.onNodeWithText("This action is not available yet.").assertIsDisplayed()
        compose.onNodeWithText("Publish map configuration").performScrollTo().performClick()
        compose.onNodeWithText("SEN-JP-A06").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Spot A-06").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("Parking infrastructure").assertIsDisplayed()
        compose.onNode(hasText("Occupancy") and isSelectable()).performClick()
        compose.onNodeWithText("Live occupancy").assertIsDisplayed()
    }

    @Test
    fun moreAndInfrastructureSurviveActivityRecreation() {
        openAdmin()
        openMore()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Lima Parking Operations S.A.C.").assertIsDisplayed()
        compose.onNodeWithText("Parking infrastructure").performClick()
        waitForMoreToClose()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Floor plans, spots and sensor configuration").assertIsDisplayed()
        compose.onNode(hasText("More") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Dashboard").performClick()
        compose.onNodeWithText("Administrator dashboard").assertIsDisplayed()
    }

    @Test
    fun captureAdminScreensAndReachEveryScrollableAction() {
        openAdmin()
        capture("15-dashboard")
        compose.onNodeWithText("Reservation conflict").performScrollTo().assertIsDisplayed()
        capture("15-dashboard-details")
        compose.onNode(hasText("Occupancy") and isSelectable()).performClick()
        compose.onNodeWithText("Updated just now · 24 spots").performScrollTo().assertIsDisplayed()
        capture("16-occupancy")
        compose.onNodeWithText("Alerts").performClick()
        capture("17-alerts")
        compose.onNodeWithText("High Capacity").performScrollTo().assertIsDisplayed()
        capture("17-alerts-details")
        openMore()
        capture("15a-more")
        compose.onNodeWithText("Parking infrastructure").performClick()
        waitForMoreToClose()
        capture("24-infrastructure")
        compose.onNodeWithText("Replace floor plan").performScrollTo().performClick()
        capture("36-upload")
        compose.onNode(hasText("Upload floor plan") and hasClickAction()).performScrollTo().performClick()
        capture("25-map")
        compose.onNodeWithText("Publish map configuration").performScrollTo().assertIsDisplayed()
        capture("25-map-actions")
    }

    private fun openAdmin() {
        compose.onNodeWithText("Parking Admin").performScrollTo().performClick()
        compose.onNodeWithText("Administrator dashboard").assertIsDisplayed()
    }

    private fun openMore() {
        compose.onNode(hasText("More") and isSelectable()).performClick()
        compose.onNodeWithText("Lima Parking Operations S.A.C.").assertIsDisplayed()
    }

    private fun openInfrastructure() {
        openAdmin()
        openMore()
        compose.onNodeWithText("Parking infrastructure").performClick()
        waitForMoreToClose()
        compose.onNodeWithText("Floor plans, spots and sensor configuration").assertIsDisplayed()
    }

    private fun waitForMoreToClose() {
        compose.waitUntil(5_000) { compose.onAllNodesWithText("Lima Parking Operations S.A.C.").fetchSemanticsNodes().isEmpty() }
    }

    private fun capture(name: String) {
        compose.waitForIdle()
        compose.mainClock.advanceTimeBy(600)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.waitForIdleSync()
        // Compose semantics can settle before the software renderer presents its next frame.
        SystemClock.sleep(750)
        val context = instrumentation.targetContext
        val directory = File(context.getExternalFilesDir(null), "admin-screens").apply { mkdirs() }
        val width = context.resources.configuration.screenWidthDp
        val screenshot = instrumentation.uiAutomation.executeShellCommand("screencap -p")
        ParcelFileDescriptor.AutoCloseInputStream(screenshot).use { input ->
            File(directory, "$name-$width.png").outputStream().use { output -> input.copyTo(output) }
        }
    }
}
