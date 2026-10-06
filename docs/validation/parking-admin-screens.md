# Parking Admin screen validation

Date: October 6, 2026. Branch: `feature/parking-admin-initial-screens`.

The seven Parking Admin views follow the Mobile App (Parking Admin) designs in Figma. Each view has a separate feature commit. A final adjustment commit includes navigation fixes, tests, documentation, and device captures.

## Results

| Check | Result |
| --- | --- |
| Debug APK and instrumented-test APK | Build successful |
| Unit tests | 1 passed, 0 failures |
| Android lint | 0 errors, 16 warnings |
| Full instrumented suite at 412 dp | 11 passed, 0 failures |
| Parking Admin suite at 320 dp | 6 passed, 0 failures |

The builds and lint used the project's configured JDK and existing dependencies. Lint warnings remain in the project; this report does not claim a warning-free build. Unit-test coverage currently consists of the existing arithmetic example. Navigation coverage comes from the instrumented UI tests.

Instrumented tests ran on an Android API 37 emulator at density 480. The 412 dp configuration used a 1236 × 2940 pixel display; the 320 dp configuration used a 960 × 2400 pixel display. These checks do not establish compatibility across every supported Android version or device.

Passing instrumentation output is recorded in [the 412 dp log](parking-admin-tests-412.txt) and [the 320 dp log](parking-admin-tests-320.txt).

## Interaction coverage

- Login role selection and navigation between Dashboard, Occupancy, and Alerts.
- Dashboard occupancy shortcut and scrollable content.
- More sheet dismissal through Close and system back, with restoration of the previous tab.
- Disabled unfinished destinations in More; Parking infrastructure remains enabled.
- Infrastructure navigation to Upload floor plan and Digital parking map.
- Map back navigation to the view that opened it, including after activity recreation.
- More and infrastructure state restoration after activity recreation.
- Static filters and actions displaying a local unavailable-action message.
- Access to controls below the initial viewport through scrolling at both widths.

Validation found that the Choose file message could cover the Upload floor plan button on the smaller display. The layout now reserves the measured snackbar height while the message is visible. The regression test presses Upload floor plan while that message is displayed and verifies that the digital map opens. Final tests passed with this adjustment.

Dashboard and occupancy previews now include the theme surface. Occupancy uses the shared search and filter components. Literal percentages are marked as unformatted string resources.

## Scope

Parking information, alerts, file details, spot types, and sensor identifiers are static. Upload floor plan opens the sample map view. File selection, uploading, sensor queries, map editing, publishing, authentication, and backend integration are not implemented in these screens. Read-only fields and local messages preserve this scope.

The captures below are native device screenshots, including system status icons and the navigation gesture bar. Content below the fold is shown in additional captures. The emulator was shut down after the review, and no emulator process or connected emulator remained.

## Captures

| View | 412 dp | 320 dp |
| --- | --- | --- |
| 15 - Admin Dashboard | [Capture](parking-admin-screens/15-dashboard-412.png) | [Capture](parking-admin-screens/15-dashboard-320.png) |
| Dashboard details | [Capture](parking-admin-screens/15-dashboard-details-412.png) | [Capture](parking-admin-screens/15-dashboard-details-320.png) |
| 16 - Live occupancy | [Capture](parking-admin-screens/16-occupancy-412.png) | [Capture](parking-admin-screens/16-occupancy-320.png) |
| 17 - Operational Alerts | [Capture](parking-admin-screens/17-alerts-412.png) | [Capture](parking-admin-screens/17-alerts-320.png) |
| Alert details | [Capture](parking-admin-screens/17-alerts-details-412.png) | [Capture](parking-admin-screens/17-alerts-details-320.png) |
| 15a - Administrator More | [Capture](parking-admin-screens/15a-more-412.png) | [Capture](parking-admin-screens/15a-more-320.png) |
| 24 - Parking Infrastructure | [Capture](parking-admin-screens/24-infrastructure-412.png) | [Capture](parking-admin-screens/24-infrastructure-320.png) |
| 36 - Upload floor plan | [Capture](parking-admin-screens/36-upload-412.png) | [Capture](parking-admin-screens/36-upload-320.png) |
| 25 - Digital parking map | [Capture](parking-admin-screens/25-map-412.png) | [Capture](parking-admin-screens/25-map-320.png) |
| Map actions | [Capture](parking-admin-screens/25-map-actions-412.png) | [Capture](parking-admin-screens/25-map-actions-320.png) |
