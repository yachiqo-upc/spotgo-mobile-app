package com.yachiqo.spotgo

import android.graphics.Bitmap
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
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
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
        compose.onNodeWithText("My reservations").assertIsDisplayed()
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
        compose.onNode(hasText("Profile") and isSelectable()).performClick()
        compose.activityRule.scenario.recreate()
        compose.onAllNodesWithText("Profile").assertCountEquals(2)
        compose.onNode(hasText("Profile") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Briguite Carhuaz").assertIsDisplayed()
        compose.onNodeWithText("Sign out").performScrollTo().performClick()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun driverScreensAreReachableAndSignOutReturnsToLogin() {
        openDriver()
        compose.onNodeWithText("Find parking nearby").assertIsDisplayed()
        captureScreenshot("03-explore")
        compose.onNodeWithText("View parking").performScrollTo().assertIsDisplayed()
        captureScreenshot("03-explore-card")
        compose.onNodeWithText("View parking").performClick()
        compose.onNodeWithText("Zone details").assertIsDisplayed()
        captureScreenshot("04-zone")
        compose.onNodeWithText("Continue").performScrollTo().assertIsDisplayed()
        captureScreenshot("04-zone-form")
        compose.onNodeWithText("Continue").performClick()
        compose.onNodeWithText("Reservation confirmed").assertIsDisplayed()
        compose.onNodeWithText("#SG-4821").assertIsDisplayed()
        captureScreenshot("05-confirmed")
        compose.onNodeWithText("My reservations").performScrollTo().performClick()
        compose.onNodeWithText("My reservations").assertIsDisplayed()
        captureScreenshot("06-reservations")
        compose.onNode(hasText("Payments") and isSelectable()).performClick()
        compose.onNodeWithText("S/ 0.00").assertIsDisplayed()
        captureScreenshot("08-payments")
        compose.onNode(hasText("Profile") and isSelectable()).performClick()
        compose.onNodeWithText("Briguite Carhuaz").assertIsDisplayed()
        captureScreenshot("10-profile")
        compose.onNodeWithText("Sign out").performScrollTo().performClick()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun nestedDriverScreensSurviveRecreationAndBackReturnsToExplore() {
        openDriver()
        compose.onNodeWithText("View parking").performScrollTo().performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Zone details").assertIsDisplayed()
        compose.onNodeWithText("Continue").performScrollTo().performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Reservation confirmed").assertIsDisplayed()
        pressBack()
        compose.onNodeWithText("Zone details").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back to Explore").performClick()
        compose.onNodeWithText("Find parking nearby").assertIsDisplayed()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun unfinishedActionsStayWithinTheStaticScreen() {
        openDriver()
        compose.onNode(hasText("Payments") and isSelectable()).performClick()
        compose.onNodeWithText("Add payment method").performScrollTo().performClick()
        compose.onNodeWithText("This action is not available yet.").assertIsDisplayed()
        compose.onNode(hasText("Payments") and isSelectable()).assertIsSelected()
    }

    private fun openDriver() {
        compose.onNodeWithText("Driver").performScrollTo().performClick()
    }

    private fun captureScreenshot(name: String) {
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val directory = File(instrumentation.targetContext.getExternalFilesDir(null), "driver-validation")
        check(directory.exists() || directory.mkdirs())
        val width = instrumentation.targetContext.resources.configuration.screenWidthDp
        val frameCommitted = CountDownLatch(1)
        compose.runOnUiThread {
            val rootView = compose.activity.window.decorView
            rootView.viewTreeObserver.registerFrameCommitCallback { frameCommitted.countDown() }
            rootView.invalidate()
        }
        check(frameCommitted.await(5, TimeUnit.SECONDS)) { "The screenshot frame was not committed." }
        instrumentation.uiAutomation.waitForIdle(1_000, 5_000)
        val bitmap = checkNotNull(instrumentation.uiAutomation.takeScreenshot())
        File(directory, "$name-$width.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
    }
}
