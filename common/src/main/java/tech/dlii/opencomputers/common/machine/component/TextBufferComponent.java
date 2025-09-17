package tech.dlii.opencomputers.common.machine.component;

import tech.dlii.opencomputers.client.font.Fonts;
import tech.dlii.opencomputers.util.ExtendedUnicodeHelper;

public class TextBufferComponent implements tech.dlii.opencomputers.api.TextBuffer {

    // Buffer Settings
    private final int maxWidth;
    private final int maxHeight;

    // Current Data
    Character[][] data;

    // Current Formatting
    int currentforegroundColor = 0xFFFFFFFF;
    int currentBackgroundColor = 0xFF000000;
    Fonts.FontStyle currentFontStyle = Fonts.FontStyle.REGULAR;

    public TextBufferComponent(int width, int height) {
        this.maxWidth = width;
        this.maxHeight = height;
        this.data = new Character[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                data[i][j] = new Character();
            }
        }
    }

    // Buffer Settings -------------------------------------------------------------------------------------------------

    @Override
    public double getAspectRatio() {
        return ((double) getWidth()) / getHeight();
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    @Override
    public int getWidth() {
        return data[0].length;
    }

    @Override
    public int getHeight() {
        return data.length;
    }

    @Override
    public boolean setResolution(int width, int height) {
        if (width < 1 || height < 1 || width > maxHeight || height > maxHeight) {
            return false;
        }
        if (width == this.getWidth() && height == this.getHeight()) {
            return false;
        }
        Character[][] newData = new Character[height][width];
        // Copy old buffer information
        for (int i = 0; i < Math.min(this.getHeight(), height); i++) {
            for (int j = 0; j < Math.min(this.getWidth(), width); j++) {
                newData[i][j] = data[i][j];
            }
        }
        // Initialize new buffer information
        for (int i = this.getHeight(); i < height; i++) {
            for (int j = this.getWidth(); j < width; j++) {
                newData[i][j] = new Character(
                        ' ',
                        currentforegroundColor,
                        currentBackgroundColor,
                        currentFontStyle
                );
            }
        }
        this.data = newData;
        return true;
    }

    // Data Accession --------------------------------------------------------------------------------------------------

    public Character get(int row, int col) {
        if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
            throw new IndexOutOfBoundsException();
        }
        return data[row][col];
    }

    // Data Mutation ---------------------------------------------------------------------------------------------------

    @Override
    public void setText(int column, int row, int[][] text) {

    }

    public boolean set(int row, int col, String value, boolean vertical) {
        int codePointLength = ExtendedUnicodeHelper.length(value);
        boolean changed = false;
        if (vertical) {
            if (col < 0 || col >= getWidth()) {
                return false;
            }
        } else {
            if (row < 0 || row >= getHeight()) {
                return false;
            }
            int j = Math.max(col, 0);
            int cj = 0;
            while (j < Math.min(col + codePointLength, getWidth()) && cj < value.length()) {
                int charCodePoint = value.codePointAt(cj);
                changed |= set(row, j, charCodePoint);
                j += 1;
                cj = value.offsetByCodePoints(cj, 1);
            }
        }
        return changed;
    }

    public boolean set(int row, int col, int codePoint) {
        return data[row][col].set(codePoint, currentforegroundColor, currentBackgroundColor, currentFontStyle);
    }

    public static class Character {
        public int codePoint = ' ';
        public int foregroundColor = 0xFF000000;
        public int backgroundColor = 0xFF000000;
        public Fonts.FontStyle fontStyle = Fonts.FontStyle.REGULAR;

        public Character() {}

        public Character(int codePoint, int foregroundColor, int backgroundColor, Fonts.FontStyle fontStyle) {
            set(codePoint, foregroundColor, backgroundColor, fontStyle);
        }

        public boolean set(int codePoint, int foregroundColor, int backgroundColor, Fonts.FontStyle fontStyle) {
            boolean changed = codePoint != this.codePoint
                    || foregroundColor != this.foregroundColor
                    || backgroundColor != this.backgroundColor
                    || fontStyle != this.fontStyle;
            this.codePoint = codePoint; // Unicode character
            this.foregroundColor = foregroundColor;
            this.backgroundColor = backgroundColor;
            this.fontStyle = fontStyle;
            return changed;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Character other) {
                return this.codePoint == other.codePoint
                        && this.foregroundColor == other.foregroundColor
                        && this.backgroundColor == other.backgroundColor
                        && this.fontStyle == other.fontStyle;
            }
            return false;
        }
    }
}
