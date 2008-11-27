package net.stoerr.functional.backus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.stoerr.functional.util.F;
import net.stoerr.functional.util.Iterators;

/**
 * Functions related to {@link ListObject}s.
 * 
 * @author hps
 * @since 26.11.2008
 */
public class Lists {

    public static Function nth(final int l) {
        return new LazyFunction() {
            @Override
            protected Object compute(Value arg) {
                return arg.asList().get(l);
            }
        };
    }

    public static final Function FIRST = nth(0);
    public static final Function SECOND = nth(1);

    public static final Function tail(final int l) {
        return new LazyFunction() {
            @Override
            protected Object compute(final Value arg) {
                return new AbstractList() {
                    public Value get(int i) {
                        return arg.asList().get(i + l);
                    }

                    public int size() {
                        int siz = arg.asList().size() - l;
                        if (0 > siz)
                            throw new BottomException(l + "Tail of " + (siz + l));
                        return siz;
                    }
                };
            }
        };
    }

    public static final Function TAIL = tail(1);

    /** A immediate list value */
    public static Value V(Object... vals) {
        List<Value> values = new ArrayList<Value>();
        for (Object val : vals) values.add(new ImmediateValue(val));
        return new ImmediateValue(new ImmediateList(values));
    }
    
    /** A immediate list function */
    public static Function L(Object... vals) {
        List<Value> values = new ArrayList<Value>();
        for (Object val : vals) values.add(new ImmediateValue(val));
        return Combinators.constant(new ImmediateList(values));
    }    

    public static final Function APPEND = new LazyFunction() {
        @Override
        protected Object compute(final Value arg) {
            Iterator<Value> argIt = arg.asList().asIterator();
            final Iterator<Iterator<Value>> superit = Iterators.map(argIt, new F<Value, Iterator<Value>>() {
                public Iterator<Value> call(Value argVal) {
                    return argVal.asList().asIterator();
                }
            });
            final Iterator<Value> combinedit = Iterators.concat(superit);
            final List<Value> col = Iterators.lazyCollection(combinedit);
            return new AbstractList() {
                public Value get(int i) {
                    return col.get(i);
                }

                public int size() {
                    return col.size(); // TODO calculate directly; this does not
                    // allow streams.
                }
            };
        }
    };
}
