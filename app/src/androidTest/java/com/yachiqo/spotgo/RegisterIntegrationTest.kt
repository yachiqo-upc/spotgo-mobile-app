package com.yachiqo.spotgo

import android.os.ParcelFileDescriptor
import android.os.SystemClock
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.AnnotatedString
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import org.junit.Rule
import org.junit.Test

class RegisterIntegrationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun registerStartsEmptyAndReturnsToLoginThroughBothControls() {
        openRegister()
        capture("02-register")
        listOf("First name", "Last name", "Phone", "Email", "Password").forEach {
            field(it).performScrollTo().assert(emptyField())
        }
        compose.onNodeWithText("I agree to the Terms and Privacy Policy")
            .performScrollTo().assertIsOff()
        capture("02-register-actions")
        compose.onNodeWithText("Sign in").performScrollTo().performClick()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
        openRegister()
        compose.onNodeWithContentDescription("Back to login").performScrollTo().performClick()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
        openRegister()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    @Test
    fun registerRestoresNonSensitiveInputsAndClearsTemporaryPassword() {
        openRegister()
        field("First name").performTextInput("Sample")
        field("Last name").performScrollTo().performTextInput("Driver")
        field("Phone").performScrollTo().performTextInput("123456789")
        field("Email").performScrollTo().performTextInput("sample@example.com")
        field("Password").performScrollTo().performTextInput("temporary-password")
        compose.onNodeWithContentDescription("Clear password").performClick()
        field("Password").assert(emptyField()).performTextInput("temporary-password")
        closeSoftKeyboard()
        compose.onNodeWithText("I agree to the Terms and Privacy Policy").performScrollTo().performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Create your Driver account").performScrollTo().assertIsDisplayed()
        field("First name").performScrollTo().assertTextContains("Sample")
        field("Last name").performScrollTo().assertTextContains("Driver")
        field("Phone").performScrollTo().assertTextContains("123456789")
        field("Email").performScrollTo().assertTextContains("sample@example.com")
        field("Password").performScrollTo().assert(emptyField())
        compose.onNodeWithText("I agree to the Terms and Privacy Policy").performScrollTo().assertIsOn()
        compose.onNodeWithText("Sign in").performScrollTo().performClick()
        openRegister()
        field("First name").assert(emptyField())
        compose.onNodeWithText("I agree to the Terms and Privacy Policy").performScrollTo().assertIsOff()
    }

    @Test
    fun pendingRegistrationPreservesTheScreenAndBothWorkspacesRemainReachable() {
        openRegister()
        compose.onNodeWithText("Create account").performScrollTo().performClick()
        compose.onNodeWithText("This action will be available in a future sprint.").assertIsDisplayed()
        field("Email").performScrollTo().assert(emptyField())
        compose.onNodeWithText("Sign in").performScrollTo().performClick()
        compose.onNodeWithText("Driver").performScrollTo().performClick()
        compose.onNodeWithText("Find parking nearby").assertIsDisplayed()
        compose.onNode(hasText("Profile") and isSelectable()).performClick()
        compose.onNodeWithText("Sign out").performScrollTo().performClick()
        compose.onNodeWithText("Parking Admin").performScrollTo().performClick()
        compose.onNodeWithText("Administrator dashboard").assertIsDisplayed()
        compose.onNode(hasText("More") and isSelectable()).performClick()
        compose.onNodeWithText("Parking infrastructure").performClick()
        compose.onNodeWithText("Floor plans, spots and sensor configuration").assertIsDisplayed()
        pressBack()
        pressBack()
        compose.onNodeWithText("Welcome back").assertIsDisplayed()
    }

    private fun openRegister() {
        compose.onNodeWithText("Create account").performScrollTo().performClick()
        compose.onNodeWithText("Create your Driver account").assertIsDisplayed()
    }

    @Test
    fun everyAdminAccountButtonReturnsToLoginAndResetsTheWorkspace() {
        listOf("Dashboard", "Occupancy", "Alerts", "Parking infrastructure").forEach { screen ->
            compose.onNodeWithText("Parking Admin").performScrollTo().performClick()
            compose.onNodeWithText("Administrator dashboard").assertIsDisplayed()
            if (screen == "Parking infrastructure") {
                compose.onNode(hasText("More") and isSelectable()).performClick()
                compose.onNodeWithText(screen).performClick()
            } else {
                compose.onNode(hasText(screen) and isSelectable()).performClick()
            }
            compose.onNodeWithContentDescription("Administrator account").performClick()
            compose.onNodeWithText("Welcome back").assertIsDisplayed()
        }
    }

    private fun field(label: String) = compose.onNode(hasSetTextAction() and hasText(label))

    private fun emptyField() = SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString(""))

    private fun capture(name: String) {
        compose.waitForIdle()
        compose.mainClock.advanceTimeBy(600)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.waitForIdleSync()
        SystemClock.sleep(750)
        val context = instrumentation.targetContext
        val directory = File(context.getExternalFilesDir(null), "register-validation").apply { mkdirs() }
        val width = context.resources.configuration.screenWidthDp
        ParcelFileDescriptor.AutoCloseInputStream(instrumentation.uiAutomation.executeShellCommand("screencap -p"))
            .use { input -> File(directory, "$name-$width.png").outputStream().use { input.copyTo(it) } }
    }
}
