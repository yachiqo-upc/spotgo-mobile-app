# Validation report - October 6, 2026

- The application and instrumented test APKs built successfully with `:app:assembleDebug` and `:app:assembleDebugAndroidTest`.
- `:app:lintDebug` completed successfully with zero errors and 14 warnings. The report is located at `app/build/reports/lint-results-debug.html`. Dependency update notices and existing template resource warnings were retained.
- Three navigation tests passed on the emulator: Driver navigation, Parking Admin navigation, returning to the login screen, and preserving the selected role and tab across activity recreation. The output is stored in `navigation-tests.txt`.
- Floating labels and placeholders were subsequently adjusted to match Figma, and a fourth test was added for login fields and password clearing. These changes compiled successfully. The fourth test and a new screenshot were not executed after the request to finish the task.
- `login-before-floating-labels.png` predates the last visual adjustment. It is not a screenshot of the delivered version. The Figma reference is stored at `../design/login-reference.png`.
- The emulator was shut down. `adb devices` showed no connected devices, and no QEMU processes remained running.

The first emulator attempt encountered a System UI freeze. After recovery, all three navigation tests passed. One lint attempt stalled in the Kotlin analyzer; a fresh run without the configuration cache completed the analysis successfully.
