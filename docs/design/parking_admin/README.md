# Parking Admin artwork

The screens follow the Mobile App (Parking Admin) page in the [SpotGo Figma file](https://www.figma.com/design/sQ2XbvLctkCFweIj0w0SjQ/SpotGo-Design?node-id=1-5).

| Screen | Figma node |
| --- | --- |
| 15 - Administrator dashboard | `185:9361` |
| 16 - Live occupancy | `185:9419` |
| 17 - Operational alerts | `185:9513` |
| 15a - Administrator More | `185:10098` |
| 24 - Parking infrastructure | `185:9794` |
| 36 - Upload floor plan | `185:10062` |
| 25 - Digital parking map | `185:9833` |

The screens use native Material 3 Expressive surfaces, search, filters, list items, buttons, text fields, app bars, and a modal bottom sheet. Both maps share Compose floor-plan geometry. Display data, counts, statuses, filters, file details, update times, and the selected draft spot are static.

Dashboard, Occupancy, and Alerts are connected through the navigation bar. More opens a bottom sheet, with Parking infrastructure as its only enabled feature destination. Close, outside dismissal, and system back dismiss the sheet. Infrastructure opens Upload floor plan or Digital parking map. Upload opens the sample map view without reading or uploading a file. Map back navigation returns to the view that opened it. Infrastructure back navigation restores the previous main tab; system back from a main tab returns to login. Navigation state survives activity recreation.

Choose file, filters, search, inventory actions, account actions, manual map correction, and publishing show a local unavailable-action message. The upload and map text fields are read-only. These screens do not select files, upload images, validate or publish maps, query sensors, or change parking-space data.

The status-dot SVGs are original Figma assets and are imported using Android's `Svg2Vector` with `../ImportFigmaIcons.java`. The back-arrow asset matches the Driver artwork. Dots occupy their original 8 dp badge or 5 dp map slots. The circulation SVG in `source/` is rendered to a transparent PNG at three times its original 326 × 26 resolution to retain its dashed markers. Android displays it at the design's explicit size, scaled with the floor plan. The occupancy and digital-map circulation exports are byte-identical and share the same Android drawable.

To regenerate the circulation image with an existing Node.js and Sharp installation, run from the repository root:

```text
node docs/design/RenderAdminMapAssets.cjs /absolute/path/to/node_modules/sharp
```

Sharp is an artwork-conversion tool only, not an Android application dependency. There are no domain entities, sensor connections, map services, or backend integrations.

Validation evidence and device captures are documented in [the Parking Admin validation report](../../validation/parking-admin-screens.md).
