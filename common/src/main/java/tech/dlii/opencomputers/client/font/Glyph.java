package tech.dlii.opencomputers.client.font;

import java.awt.image.BufferedImage;

/**
 * Borrowed implementation from:
 * https://github.com/North-Western-Development/oc2r/blob/1.20.1/src/main/java/li/cil/oc2/common/vm/terminal/fonts/Glyph.java
 */
public class Glyph {
    public final BufferedImage image;
    public final int width, height;
    public final int advance;
    public float uStart = 0, vStart = 0, uEnd = 0, vEnd = 0;

    public Glyph(BufferedImage image, int width, int height, int advance) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.advance = advance;
    }

    public void setUV(float u, float v, float u2, float v2) {
        uStart = u;
        vStart = v;
        uEnd = u2;
        vEnd = v2;
    }
}