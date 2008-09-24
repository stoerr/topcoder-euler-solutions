package net.stoerr.topcoder.amdraytracer;

import java.awt.image.MemoryImageSource;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

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
        Map<Integer, Integer> histo = new TreeMap<Integer, Integer>();
        double max = Double.MIN_NORMAL;
        int count = 0;
        for (int x = 0; x < xs; ++x) {
            for (int y = 0; y < ys; ++y) {
                double d = a[x][y];
                if (d > max)
                    max = d;
                if (d > 0) {
                    int l = (int) Math.round(Math.log(d) * -10);
                    Integer cnt = histo.get(l);
                    if (null == cnt)
                        cnt = 0;
                    ++cnt;
                    histo.put(l, cnt);
                    ++count;
                }
            }
        }
        int cumul = 0;
        for (Entry<Integer, Integer> entry : histo.entrySet()) {
            cumul += entry.getValue();
            if (100 * cumul > 5 * count) {
                max = Math.exp(entry.getKey() / -10);
                break;
            }
        }
        // FIXME do something sensible here. proportional to avg square?
        int i = 0;
        final double f = 256 / max;
        for (int y = ys - 1; y >= 0; --y) {
            // for (int y = 0; y < ys; ++y) {
            for (int x = 0; x < xs; ++x) {
                int gray = (int) Math.floor(f * Math.sqrt(a[x][y]));
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
