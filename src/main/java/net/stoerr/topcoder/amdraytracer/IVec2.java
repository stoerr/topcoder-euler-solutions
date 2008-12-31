/* created by hps on 18.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.topcoder.amdraytracer;

/**
 * 2-dimensional integer vector
 * 
 * @author hps
 * @since 18.09.2008
 */
public final class IVec2 {

    public final int x;
    public final int y;

    public IVec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2{" + x + ", " + y + "}";
    }
    
    @Override
    public boolean equals(Object arg0) {
        if (null != arg0 && arg0 instanceof IVec2) {
            IVec2 o = (IVec2) arg0;
            return x == o.x  && y == o.y;
        } 
        return false;
    }
    
    @Override
    public int hashCode() {
        return (x%2533963*31 + y%5948383*59);
    }
}
