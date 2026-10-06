# Driver artwork

The Driver screens follow the Mobile App (Driver) page in the [SpotGo Figma file](https://www.figma.com/design/sQ2XbvLctkCFweIj0w0SjQ/SpotGo-Design?node-id=1-4).

| Screen | Figma node |
| --- | --- |
| 03 - Explore parking | `73:2778` |
| 04 - Zone Details & reserve | `73:2825` |
| 05 - Reservation confirmed | `73:2868` |
| 06 - Reservations | `73:2910` |
| 08 - Payments | `73:2995` |
| 10 - Profile | `73:3050` |

SVG files retain the exported Figma artwork. Simple icons and the sample booking QR are imported with Android's `Svg2Vector` using `../ImportFigmaIcons.java`. The street and walking-route SVGs are stored in `source/` and rendered to transparent PNGs at three times their original resolution. This preserves the street geometry and dashed walking route that Android's vector importer does not reproduce correctly. Android uses the packaged `drawable-nodpi` PNGs at explicit Compose sizes.

To regenerate these two images using an existing Node.js and Sharp installation, run from the repository root:

```text
node docs/design/RenderDriverMapAssets.cjs /absolute/path/to/node_modules/sharp
```

Sharp is used only for artwork conversion and is not an application dependency. The map is a local illustration assembled in Compose, with native labels and parking markers. It does not use a map service or location permissions. The booking QR is a fixed visual sample and does not represent a generated reservation.
