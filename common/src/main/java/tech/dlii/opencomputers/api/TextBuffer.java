package tech.dlii.opencomputers.api;

public interface TextBuffer {

    /**
     * Get the aspect ratio of the buffer.
     * <br>
     * Note that this is in fact {@code width / height}.
     *
     * @see #setAspectRatio(double, double)
     */
    double getAspectRatio();

    /**
     * Get the current horizontal resolution.
     *
     * @see #setResolution(int, int)
     */
    int getWidth();

    /**
     * Get the current vertical resolution.
     *
     * @see #setResolution(int, int)
     */
    int getHeight();

    /**
     * Set the buffer's active resolution.
     *
     * @param width  the horizontal resolution.
     * @param height the vertical resolution.
     * @return {@code true} if the resolution changed.
     */
    boolean setResolution(int width, int height);

    int getRenderWidth();
    int getRenderHeight();



    void setText(int row, int column, int[][] text);
}
