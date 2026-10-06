# Register and mobile integration validation

Date: October 6, 2026. Branch: `develop`.

Register follows Figma node `73:2758` (02 - Register) on the Mobile App (Driver) page. The screen uses the existing theme, wordmark, icon artwork, and native Material 3 Expressive controls. First name, last name, phone, email, and password start empty. Generic placeholders replace the personal-data examples in Figma, and the terms checkbox starts unchecked.

Login opens Register through Create account. Register returns to Login through its back icon, Sign in, or system back. Create account shows a local pending-action message. Non-sensitive form values and the checkbox survive activity recreation; the password is temporary and is cleared on recreation. Leaving and reopening Register starts a fresh empty form.

The Parking Admin account icon now returns to Login from Dashboard, Occupancy, Alerts, and Parking infrastructure. Reopening Parking Admin starts at Dashboard, matching the role-exit behavior of Driver's Sign out action.

## Results

| Check | Result |
| --- | --- |
| Debug APK and instrumented-test APK | Build successful |
| Unit tests | 1 passed, 0 failures |
| Android lint | 0 errors, 17 warnings |
| Complete instrumented suite at 412 dp | 18 passed, 0 failures |
| Register integration tests at 320 dp | 4 passed, 0 failures |

The complete suite covers Login, all six Driver screens, all seven Parking Admin views, Register, local pending actions, scrolling, nested back navigation, role exit, and activity recreation. The four additional compact-width checks exercise empty registration fields, secure password clearing, restored form state, both role workspaces after returning from Register, and every Parking Admin account button.

The unit test is the existing arithmetic example. Interface and navigation behavior are verified by instrumented tests. Lint warnings concern existing dependency updates, template resources, manifest labeling, and artwork; a warning-free build is not claimed. No dependencies or permissions were added.

Testing used Android API 37 at density 480. The full suite ran at 1236 × 2940 pixels (412 dp width); the new integration tests also ran at 960 × 2400 pixels (320 dp width). An initial execution on the warm emulator stalled during test-renderer synchronization and was stopped. A clean emulator boot completed the entire suite successfully. These results do not establish compatibility with every supported Android version or device.

Passing outputs are stored in [the complete-suite log](mobile-integration-tests-412.txt) and [the compact integration log](mobile-integration-tests-320.txt).

Build and analysis commands:

```powershell
./gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug --max-workers=2 --no-configuration-cache --offline --console=plain
```

UI test commands after installing both debug APKs on the emulator:

```powershell
adb shell am instrument -w -r com.yachiqo.spotgo.test/androidx.test.runner.AndroidJUnitRunner
adb shell am instrument -w -r -e class com.yachiqo.spotgo.RegisterIntegrationTest com.yachiqo.spotgo.test/androidx.test.runner.AndroidJUnitRunner
```

## Captures

The retained native device captures show the 412 dp configuration, including status icons and the navigation gesture bar. Compact-width behavior was checked by the four instrumented tests; its capture files were unavailable after the emulator closed. On compact displays, the registration form scrolls to reach its lower controls.

| View | 412 dp |
| --- | --- |
| Register | [Capture](register-screens/02-register-412.png) |
| Register actions | [Capture](register-screens/02-register-actions-412.png) |

The implementation remains static: it does not create accounts, authenticate users, access a backend, or introduce domain entities. Form input and navigation are local UI behavior. The emulator was shut down after validation.
