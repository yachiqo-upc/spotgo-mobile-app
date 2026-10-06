import com.android.ide.common.vectordrawable.Svg2Vector;
import java.nio.file.Files;
import java.nio.file.Path;

/** Uses Android's own SVG importer; the Figma artwork is not redrawn. */
class ImportFigmaIcons {
    public static void main(String[] args) throws Exception {
        var source = Path.of(args.length == 0 ? "docs/design" : args[0]);
        var target = Path.of("app/src/main/res/drawable");
        Files.createDirectories(target);
        try (var files = Files.list(source)) {
            for (var svg : files.filter(path -> path.toString().endsWith(".svg")).toList()) {
                var name = svg.getFileName().toString().replace(".svg", ".xml");
                try (var output = Files.newOutputStream(target.resolve(name))) {
                    var warnings = Svg2Vector.parseSvgToXml(svg, output);
                    if (warnings != null && !warnings.isBlank()) {
                        throw new IllegalStateException(svg + ": " + warnings);
                    }
                }
                System.out.println("Imported " + name);
            }
        }
    }
}
