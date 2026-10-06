# Parking Admin artwork

The screens follow the Mobile App (Parking Admin) page in the [SpotGo Figma file](https://www.figma.com/design/sQ2XbvLctkCFweIj0w0SjQ/SpotGo-Design?node-id=1-5).

| Screen | Figma node |
| --- | --- |
| 15 - Administrator dashboard | `185:9361` |
| 16 - Live occupancy | `185:9419` |

The Dashboard uses native Material 3 cards, progress indicators, and list items. Live occupancy uses native search and filter components, a Compose floor plan, and fixed parking-space labels. Display data, counts, statuses, filters, and update times are static. Dashboard and Occupancy are connected through local navigation; Alerts and More remain disabled until their screens are implemented. System back returns to login, and unfinished actions show a local message.

The two status-dot SVGs are the original Figma assets and are imported using Android's `Svg2Vector` with `../ImportFigmaIcons.java`. The circulation SVG in `source/` is rendered to a transparent PNG at three times its original 326 × 26 resolution to retain its dashed markers. Android displays it at the design's explicit size, scaled with the floor plan.

To regenerate the circulation image with an existing Node.js and Sharp installation, run from the repository root:

```text
node docs/design/RenderAdminMapAssets.cjs /absolute/path/to/node_modules/sharp
```

Sharp is an artwork-conversion tool only, not an Android application dependency. There are no domain entities, sensor connections, map services, or backend integrations.

Build, test, lint, and emulator validation are deferred until the remaining Parking Admin screens are implemented.
