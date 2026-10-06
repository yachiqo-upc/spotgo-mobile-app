// Artwork conversion only; this script is not part of the Android application.
// Pass an existing Sharp installation as the first argument.
const path = require('node:path');
const sharp = require(process.argv[2] || 'sharp');
const root = path.resolve(__dirname, '../..');

sharp(path.join(root, 'docs/design/parking_admin/source/admin_one_way_circulation.svg'), { density: 216 })
  .png()
  .toFile(path.join(root, 'app/src/main/res/drawable-nodpi/admin_one_way_circulation.png'))
  .catch(error => {
    console.error(error);
    process.exitCode = 1;
  });
