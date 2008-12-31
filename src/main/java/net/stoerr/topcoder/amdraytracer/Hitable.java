package net.stoerr.topcoder.amdraytracer;

public interface Hitable {

    Vec3 hitpoint(Ray ray);
    
}
