package net.stoerr.euler.help;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public static <T> Map<T,Integer> count(List<T> l) {
        Map<T, Integer> res = new HashMap<T,Integer>();
        for (T n : l) {
            Integer cnt = res.get(n);
            if (null == cnt) cnt = 0;
            cnt++;
            res.put(n, cnt);
        }
        return res;
    }
}
