// Build-time artwork conversion only; this script is not part of the Android app.
// Pass the path to an existing Sharp installation as the first argument.
const path = require('node:path');
const sharp = require(process.argv[2] || 'sharp');
const root = path.resolve(__dirname, '../..');

Promise.all(['map_streets', 'map_walking_route'].map(name =>
  sharp(path.join(root, 'docs/design/driver/source', `${name}.svg`), { density: 216 })
    .png()
    .toFile(path.join(root, 'app/src/main/res/drawable-nodpi', `${name}.png`))
)).catch(error => {
  console.error(error);
  process.exitCode = 1;
});
