package tech.dlii.opencomputers.client.font;

import net.minecraft.resources.ResourceLocation;
import tech.dlii.opencomputers.OpenComputers;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Borrowed implementation from:
 * https://github.com/North-Western-Development/oc2r/blob/1.20.1/src/main/java/li/cil/oc2/common/vm/terminal/fonts/FontHandling.java
 */
public class Fonts {
    public static final FontAtlas FontAtlas = new FontAtlas(1024, 1024, "font_atlas");
    // Regular
    public static final Font RegularFont = loadFont("/assets/opencomputers/fonts/monocraft-r.ttf", 32f);
    public static final UnicodeFontRenderer regularFontRenderer = new UnicodeFontRenderer(RegularFont, false);
    // Bold
    public static final Font BoldFont = loadFont("/assets/opencomputers/fonts/monocraft-b.ttf", 32f);
    public static final UnicodeFontRenderer boldFontRenderer = new UnicodeFontRenderer(BoldFont, false);
    // Italic
    public static final Font ItalicFont = loadFont("/assets/opencomputers/fonts/monocraft-i.ttf", 32f);
    public static final UnicodeFontRenderer italicFontRenderer = new UnicodeFontRenderer(ItalicFont, true);
    // Bold
    public static final Font BoldItalicFont = loadFont("/assets/opencomputers/fonts/monocraft-bi.ttf", 32f);
    public static final UnicodeFontRenderer boldItalicFontRenderer = new UnicodeFontRenderer(BoldItalicFont, true);

    public static ResourceLocation getAtlas() {
        return FontAtlas.getLocation();
    }

    public static Glyph getGlyph(int character, FontStyle style) {
        return switch (style) {
            case REGULAR -> regularFontRenderer.getGlyph(character);
            case ITALIC -> italicFontRenderer.getGlyph(character);
            case BOLD -> boldFontRenderer.getGlyph(character);
            case BOLD_ITALIC -> boldItalicFontRenderer.getGlyph(character);
        };
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = OpenComputers.class.getResourceAsStream(path)) {
            if (is == null) {
                return new Font("Arial", Font.PLAIN, (int) size);
            }
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, (int) size); // fallback
        }
    }

    public enum FontStyle {
        REGULAR,
        ITALIC,
        BOLD,
        BOLD_ITALIC
    }
}
