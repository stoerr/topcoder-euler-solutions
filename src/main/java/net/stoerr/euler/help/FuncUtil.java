package net.stoerr.euler.help;

public class FuncUtil {

    public static <T1, T2, Val> Val call(Func<Pair<T1, T2>, Val> f, T1 x, T2 y) {
        return f.call(new Pair<T1, T2>(x, y));
    }

}
