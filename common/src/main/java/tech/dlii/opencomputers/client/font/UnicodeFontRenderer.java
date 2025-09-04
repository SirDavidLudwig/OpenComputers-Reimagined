package tech.dlii.opencomputers.client.font;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Borrowed implementation from:
 * https://github.com/North-Western-Development/oc2r/blob/1.20.1/src/main/java/li/cil/oc2/common/vm/terminal/fonts/UnicodeFontRenderer.java
 */
public class UnicodeFontRenderer {
    public final Font font;
    private final Map<Integer, Glyph> glyphCache = new HashMap<>();
    private final FontRenderContext frc = new FontRenderContext(null, true, false);
    private final boolean isItalic;

    public UnicodeFontRenderer(Font font, boolean isItalic) {
        this.font = font;
        this.isItalic = isItalic;

        String initialSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+-=_.,:;<>?;':\"\\|`~[]{}1234567890△▽ ";
        int[] characters = initialSet.codePoints().toArray();
        for (final int character : characters) {
            getGlyph(character);
        }
    }

    public Glyph getGlyph(int character) {
        return glyphCache.computeIfAbsent(character, this::rasterizeGlyph);
    }

    private Glyph rasterizeGlyph(int character) {
        GlyphVector gv = font.createGlyphVector(frc, Character.toChars(character));
        BufferedImage img = new BufferedImage((isItalic) ? 44 : 20, 32, BufferedImage.TYPE_INT_ARGB); // size can be dynamic
        Graphics2D g = img.createGraphics();

        g.setFont(font);
        g.setColor(Color.WHITE);

        FontMetrics metrics = g.getFontMetrics();
        int ascent = metrics.getAscent();

        g.drawGlyphVector(gv, 0, ascent - 1);
        g.dispose();

        Glyph glyph = new Glyph(img, (isItalic) ? 44 : 20, 32, (int) gv.getGlyphMetrics(0).getAdvance());

        Fonts.FontAtlas.addGlyph(glyph);
        return glyph;
    }
}
