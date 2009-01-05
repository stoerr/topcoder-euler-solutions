package net.stoerr.euler.help;

public class FuncUtil {

    public static <T1, T2, Val> Val call(final Func<Pair<T1, T2>, Val> f, final T1 x, final T2 y) {
        return f.call(new Pair<T1, T2>(x, y));
    }

    public static <Arg1, Arg2, Val> Func<Arg2, Val> bind1st(final Func2<Arg1, Arg2, Val> f, final Arg1 arg1) {
        return new Func<Arg2, Val>() {
            public Val call(final Arg2 arg) {
                return f.call(arg1, arg);
            }
        };
    }

    public static <Arg1, Arg2, Val> Func<Arg1, Val> bind2nd(final Func2<Arg1, Arg2, Val> f, final Arg2 arg2) {
        return new Func<Arg1, Val>() {
            public Val call(final Arg1 arg) {
                return f.call(arg, arg2);
            }
        };
    }

}
