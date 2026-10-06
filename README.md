# SpotGo Mobile App

SpotGo Mobile App is the Android client for SpotGo, a parking management platform for drivers and parking administrators. It is built with Kotlin and Jetpack Compose and uses Material 3 Expressive for its interface.

## User roles

The application organizes navigation into two workspaces:

| Role | Navigation destinations |
| --- | --- |
| Driver | Explore, Reservations, Payments, Profile |
| Parking Admin | Dashboard, Live map, Alerts, More |

Each workspace has its own layout and navigation state. The login screen provides access to both workspaces.

## Technology

- Kotlin
- Jetpack Compose
- Material 3 Expressive
- AndroidX Activity and Lifecycle
- Gradle with Kotlin DSL
- JUnit, AndroidX Test, and Compose UI testing

Dependency versions are maintained in `gradle/libs.versions.toml`. Material 3 Expressive APIs used by the application are experimental.

## Requirements

- Android Studio with Jetpack Compose support.
- Android SDK 37.
- JDK 25, matching `gradle/gradle-daemon-jvm.properties`.
- An Android device or emulator running Android 10 (API 29) or later.

## Getting started

1. Open the project directory in Android Studio.
2. Install Android SDK 37 through the SDK Manager if needed.
3. Sync the project with Gradle.
4. Select the `app` run configuration and an Android device.
5. Run the application.

The application ID is `com.yachiqo.spotgo`.

## Build and test

Run the following commands from the project directory on Windows:

```powershell
# Build the debug APK
./gradlew.bat :app:assembleDebug

# Run static analysis
./gradlew.bat :app:lintDebug

# Run unit tests
./gradlew.bat :app:testDebugUnitTest

# Run instrumented UI tests
./gradlew.bat :app:connectedDebugAndroidTest
```

On macOS or Linux, use `./gradlew` instead of `./gradlew.bat`.

Instrumented tests require a connected Android device or a running emulator. The UI tests cover role navigation, tab selection, returning to the login screen, activity recreation, login placeholders, and clearing the password field.

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`. Lint reports are generated under `app/build/reports/`.

## Project structure

```text
app/src/main/
|-- java/com/yachiqo/spotgo/
|   |-- MainActivity.kt
|   |-- presentation/
|   |   |-- SpotGoApp.kt
|   |   |-- login/
|   |   |-- driver/
|   |   |-- parking_admin/
|   |   `-- common/
|   `-- ui/theme/
`-- res/
    |-- drawable/
    |-- font/
    `-- values/
```

- `presentation/login`: login interface and input state.
- `presentation/driver`: Driver workspace and navigation destinations.
- `presentation/parking_admin`: Parking Admin workspace and navigation destinations.
- `presentation/common`: shared interface components.
- `ui/theme`: application colors, typography, and Material theme.
- `res`: localized string resources, icons, and fonts.

Workspace layouts apply system insets and manage their navigation bars. Screens provide their content through the layout's `content` parameter. Role and tab selections are preserved across activity recreation.

## Design

The interface follows the [SpotGo design in Figma](https://www.figma.com/design/sQ2XbvLctkCFweIj0w0SjQ/SpotGo-Design).

SpotGo uses a dark color palette with yellow accents and the Plus Jakarta Sans typeface. Fonts and icons are packaged with the application. Design exports and the font license are stored in `docs/design/`.

## Contributing

Keep source code, identifiers, comments, interface text, and documentation in English. Add user-facing text to `app/src/main/res/values/strings.xml` and reuse the application theme and shared components.

Place screens in the appropriate role package and connect them to the corresponding workspace destination. Run the relevant build, lint, and test commands before submitting changes.
