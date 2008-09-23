package net.stoerr.topcoder.amdraytracer;

import java.awt.image.MemoryImageSource;

public final class ArrayImageDisplayer {

    public final double[][] a;

    public final MemoryImageSource source;

    private final int[] buf;

    public final int xs;

    public final int ys;

    public ArrayImageDisplayer(int xs, int ys) {
        a = new double[xs][ys];
        buf = new int[xs * ys];
        this.xs = xs;
        this.ys = ys;
        source = new MemoryImageSource(xs, ys, buf, 0, xs);
        source.setAnimated(true);
    }

    public void refresh() {
        double max = Double.MIN_NORMAL;
        for (int x = 0; x < xs; ++x) {
            for (int y = 0; y < ys; ++y) {
                double d = a[x][y];
                if (d > max)
                    max = d;
            }
        }
        max = Math.sqrt(max);
        int i = 0;
        final double f = 256 / max;
        // for (int y = ys-1; y >= 0; --y) {
        for (int y = 0; y < ys; ++y) {
            for (int x = 0; x < xs; ++x) {
                int gray = (int) Math.floor(f * a[x][y]);
                if (0 > gray)
                    gray = 0;
                if (255 < gray)
                    gray = 255;
                int alpha = 255;
                int red = gray;
                int green = gray;
                int blue = gray;
                buf[i++] = (alpha << 24) | (red << 16) | green << 8 | blue;
            }
        }
        source.newPixels();
    }

}
