package net.stoerr.topcoder.amdraytracer;

public class GCtest {

    private static final int RUNS = 32;

    public static void main(String[] args) {
        new GCtest().run2();
        new GCtest().run1();
    }

    private static final class V3 {
        double x;
        double y;
        double z;
        V3(double x,double y,double z) {
            this.x=x; this.y=y; this.z=z;
        }
    }
    
    
    private void run1() {
        long begin = System.currentTimeMillis();
        test(new V3(RUNS,3,8));
        long end = System.currentTimeMillis();
        System.err.println(0.001*(end-begin));
        System.err.println(cnt*1.0/(end-begin));
    }
    
    private void run2() {
        long begin = System.currentTimeMillis();
        test2(RUNS,3,8);
        long end = System.currentTimeMillis();
        System.err.println(0.001*(end-begin));
        System.err.println(cnt*1.0/(end-begin));
    }
    
    int cnt =0;
    
    void test(V3 v) {
        cnt++;
        if (v.x > 0) {
            test(new V3(v.x-1, v.y+1, v.z+3));
            test(new V3(v.x-1, v.y+3, v.z+6));
        }
    }
    
    void test2(double x, double y, double z) {
        cnt++;
        if (x > 0) {
            test2(x-1, y+1, z+3);
            test2(x-1, y+3, z+6);
        }
    }
    
}
