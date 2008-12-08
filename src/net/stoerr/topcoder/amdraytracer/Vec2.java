/* created by hps on 18.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.topcoder.amdraytracer;

/**
 * 2-dimensional vector
 * 
 * @author hps
 * @since 18.09.2008
 */
public final class Vec2 {

    public final double x;
    public final double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2{" + x + ", " + y + "}";
    }
}
