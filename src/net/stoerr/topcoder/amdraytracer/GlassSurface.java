package net.stoerr.topcoder.amdraytracer;

/**
 * A glass surface. <br>
 * 
 * x2=sqrt(n*n+x1*x1-1)
 */
public final class GlassSurface {
    
    public final double n;

    /**
     * @param relrefraction n1/n2
     */
    public GlassSurface(double relrefraction) {
        this.n = relrefraction;
    }
    
    

}
