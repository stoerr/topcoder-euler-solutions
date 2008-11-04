package net.stoerr.euler.help;

import java.util.List;

public class CollectionUtils {

    public static long multiply(List<Long> l) {
        long res=1;
        for (Long v : l) res*=v;
        return res;
    }
    
    public static long add(List<Long> l) {
        long res=0;
        for (Long v : l) res+=v;
        return res;
    }
    
}
