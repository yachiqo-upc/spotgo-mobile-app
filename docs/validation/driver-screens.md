# Driver screen validation

Validated on October 6, 2026, on `feature/driver-initial-screens`.

## Scope

The six Driver screens use Kotlin, Jetpack Compose, Material 3 Expressive, bundled artwork, and fixed display data. Explore opens zone details, Continue opens the confirmation screen, My reservations opens the reservations tab, and Sign out returns to login. Bottom navigation and system back handling are local UI navigation. Other actions show an unavailable-action message.

There are no domain entities, backend connections, map SDKs, generated bookings, or payment integrations. Application dependencies and the Parking Admin layout were not changed. The separate `feature/parking-admin-initial-screens` branch remains at the shared starting commit.

## Checks

| Check | Result |
| --- | --- |
| Debug APK and instrumented test APK | Built successfully |
| Unit tests | 1 passed, 0 failures |
| Android lint | 0 errors, 16 warnings |
| Full instrumented suite at 412 dp | 8 passed, 0 failures |
| Six-screen navigation test at 320 dp | 1 passed, 0 failures |
| English source, resource text, and documentation review | Passed; geographical proper names retained |
| Git whitespace check | Passed |

The emulator used Android API 37 (`sdk_gphone16k_x86_64`) at density 480. The regular viewport was 1236 × 2940 pixels (412 dp wide), and the compact viewport was 960 × 2400 pixels (320 dp wide). The original viewport was restored after testing.

The instrumented suite checks login placeholders and password clearing, both role layouts, Driver tabs, all six Driver screens, activity recreation, nested back navigation, signing out, and the local message for unfinished actions. The compact run repeats the complete six-screen route.

Lint warnings concern dependency update suggestions, existing unused template colors, launcher-icon metadata and location, a redundant activity label, and the detailed Figma QR vector. They do not prevent the build. These checks verify the static UI and navigation on the tested emulator; they do not validate real authentication, reservations, payments, or every Android device.

## Visual review

The final review corrected muted text on panels, the walking route's dash pattern, compact map marker bounds, compact amenity wrapping, and navigation and reservation-filter labels. Screenshots include both the initial and scrolled content where needed.

| Screen | 412 dp | 320 dp |
| --- | --- | --- |
| Explore parking | [Screen](driver-screens/03-explore-412.png), [parking card](driver-screens/03-explore-card-412.png) | [Screen](driver-screens/03-explore-320.png), [parking card](driver-screens/03-explore-card-320.png) |
| Zone details & reserve | [Screen](driver-screens/04-zone-412.png), [form](driver-screens/04-zone-form-412.png) | [Screen](driver-screens/04-zone-320.png), [form](driver-screens/04-zone-form-320.png) |
| Reservation confirmed | [Screen](driver-screens/05-confirmed-412.png) | [Screen](driver-screens/05-confirmed-320.png) |
| Reservations | [Screen](driver-screens/06-reservations-412.png) | [Screen](driver-screens/06-reservations-320.png) |
| Payments | [Screen](driver-screens/08-payments-412.png) | [Screen](driver-screens/08-payments-320.png) |
| Profile | [Screen](driver-screens/10-profile-412.png) | [Screen](driver-screens/10-profile-320.png) |

## Reproduction

```powershell
./gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug
./gradlew.bat :app:connectedDebugAndroidTest
```

The screenshot test writes PNG files to the application's external files directory under `driver-validation`. It waits for a committed frame and an idle accessibility event stream before capturing the emulator display. Native ADB screenshots were also used to verify stable screens independently of the test capture mechanism.

The six screen commits are followed by one review-and-fix commit. Merging into `develop` is left to the project maintainer.
