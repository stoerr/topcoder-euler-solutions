package net.stoerr.euler;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.*;

public class P222SpherePackaging {

    /** */
    public static void main(String[] args) {
        double height = 0;
        int radius = 50;
        height = radius;
        while (radius > 30) {
            double s = radius + radius - 1;
            double d = 100 - s;
            final double dh = sqrt(s * s - d * d);
            height += dh;
            radius = radius - 1;
        }
        height += radius;
        System.out.println(round(height*1000));
    }

}
