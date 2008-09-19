/**
 * 
 */
package net.stoerr.topcoder.amdraytracer;

/**
 * Parallelogram with a surface mapping to a grid;
 * 
 * @author hps
 */
public class MappedParallelogram extends Parallelogram {

    public final int xscl;

    public final int yscl;

    public MappedParallelogram(Vec3 origin, Vec3 xdir, Vec3 ydir, int xscl,
            int yscl) {
        super(origin, xdir, ydir);
        this.xscl = xscl;
        this.yscl = yscl;
    }

    /** integer coordinates of a hitpoint */
    public IVec2 icoordinates(Vec3 hitpoint) {
        Vec2 c = coordinates(hitpoint);
        int x = (int) Math.floor(c.x * xscl);
        if (0 > x)
            x = 0;
        else if (x >= xscl)
            x = xscl - 1;
        int y = (int) Math.floor(c.y * yscl);
        if (0 > y)
            y = 0;
        else if (y >= yscl)
            y = yscl - 1;
        return new IVec2(x, y);
    }

    public Vec3 gridpoint(IVec2 c) {
        return gridpoint(c.x, c.y);
    }

    /** middle of the square representing the point */
    public Vec3 gridpoint(int x, int y) {
        return origin.add(xdir.scale((x + 0.5d) / xscl)).add(
                ydir.scale((y + 0.5d) / yscl));
    }

}
