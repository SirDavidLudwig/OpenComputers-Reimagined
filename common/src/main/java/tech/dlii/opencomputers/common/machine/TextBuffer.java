package tech.dlii.opencomputers.common.machine;

import tech.dlii.opencomputers.util.ExtendedUnicodeHelper;

import java.util.Arrays;

public class TextBuffer implements tech.dlii.opencomputers.api.TextBuffer {

    public final Data data;

    public TextBuffer(int width, int height) {
        this.data = new Data(width, height);
        this.data.set(0, 0, "Hello World!", false);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.data.width; i++) {
            builder.appendCodePoint(data.get(0, i));
        }
        System.out.println(builder.toString());
    }

    @Override
    public double getAspectRatio() {
        return ((double) data.width) / data.height;
    }

    @Override
    public int getWidth() {
        return data.width;
    }

    @Override
    public int getHeight() {
        return data.height;
    }

    @Override
    public boolean setResolution(int width, int height) {
        boolean changed = width != this.getWidth() || height != this.getHeight();
//        this.width = width;
//        this.height = height;
        return changed;
    }

    @Override
    public int getRenderWidth() {
        return 8;
    }

    @Override
    public int getRenderHeight() {
        return 6;
    }

    @Override
    public void setText(int column, int row, int[][] text) {

    }

    public static class Data {

        private int width;
        private int height;
        private int[][] buffer;

        public Data(int width, int height) {
            this.width = width;
            this.height = height;
            this.buffer = new int[height][width];
            for (int i = 0; i < height; i++) {
                Arrays.fill(buffer[i], 0x20); // Fill with space characters: ' '
            }
        }

        public int get(int row, int col) {
            if (col < 0 || col >= width || row < 0 || row >= height) {
                throw new IndexOutOfBoundsException();
            }
            return buffer[row][col];
        }

        public boolean set(int row, int col, String value, boolean vertical) {
            int codePointLength = ExtendedUnicodeHelper.length(value);
            boolean changed = false;
            if (vertical) {
                if (col < 0 || col >= height) {
                    return false;
                }
            } else {
                if (row < 0 || row >= height) {
                    return false;
                }
                int[] line = buffer[row];
                int j = Math.max(col, 0);
                int cj = 0;
                while (j < Math.min(col + codePointLength, width) && j < line.length && cj < value.length()) {
                    int charCodePoint = value.codePointAt(cj);
                    if (line[j] != charCodePoint) {
                        changed = true;
                    }
                    setChar(line, j, charCodePoint);
                    j += 1;
                    cj = value.offsetByCodePoints(cj, 1);
                }
            }
            return changed;
        }

        private void setChar(int[] line, int index, int character) {
            line[index] = character;
        }
    }
}
