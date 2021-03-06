package net.stoerr.functional.backus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.stoerr.functional.util.F;
import net.stoerr.functional.util.Iterators;
import net.stoerr.functional.util.Iterators.DelayedList;

/**
 * Functions related to {@link ListObject}s.
 * 
 * @author hps
 * @since 26.11.2008
 */
public final class Lists {

    public static Function nth(final int l) {
        final class NthFunction extends LazyFunction {
            @Override
            protected Object compute(Value arg) {
                return arg.asList().get(l);
            }
        }
        return new NthFunction();
    }

    public static final Function FIRST = nth(0);
    public static final Function SECOND = nth(1);

    public static final Function tail(final int l) {
        final class TailFunction extends LazyFunction {
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
        }
        return new TailFunction();
    }

    public static final Function TAIL = tail(1);

    /** A immediate list value */
    public static Value V(Object... vals) {
        List<Value> values = new ArrayList<Value>();
        for (Object val : vals)
            values.add(new ImmediateValue(val));
        return new ImmediateValue(new ImmediateList(values));
    }

    /** A immediate list function */
    public static Function L(Object... vals) {
        List<Value> values = new ArrayList<Value>();
        for (Object val : vals)
            values.add(new ImmediateValue(val));
        return Combinators.constant(new ImmediateList(values));
    }

    public static final Function APPEND = new AppendFunction();

    private static final class AppendFunction extends LazyFunction {
        @Override
        protected Object compute(final Value arg) {
            Iterator<Value> argIt = arg.asList().asIterator();
            final Iterator<Iterator<Value>> superit = Iterators.map(argIt, new F<Value, Iterator<Value>>() {
                public Iterator<Value> call(Value argVal) {
                    return argVal.asList().asIterator();
                }
            });
            final Iterator<Value> combinedit = Iterators.concat(superit);
            final DelayedList<Value> col = Iterators.delayedList(combinedit);
            return new DelayedListAdapter(col);
        }
    }

    private static class DelayedListAdapter extends AbstractList {
        private DelayedList<Value> list;

        public DelayedListAdapter(DelayedList<Value> col) {
            this.list = col;
        }

        public Value get(int i) {
            return list.get(i);
        }

        @Override
        public boolean has(int i) {
            return list.has(i);
        }

        public int size() {
            return list.size(); // TODO calculate directly; this does not
            // allow streams.
        }
    }

    public static final Function TRANSPOSE = new TransposeFunction();

    private static final class TransposeFunction extends LazyFunction {
        @Override
        protected Object compute(final Value arg) {
            final ListObject list = arg.asList();
            return new LazyList() {
                @Override
                protected Value value(final int i) {
                    return new ImmediateValue(new LazyList() {
                        @Override
                        protected Value value(int j) {
                            return list.get(j).asList().get(i);
                        }

                        public int size() {
                            return list.size();
                        }
                    });
                }

                public int size() {
                    final Value first = list.get(0);
                    return first.asList().size();
                }
            };
        }
    }

    public static final Function MERGE = new MergeFunction(false);
    public static final Function MERGEUNIQUE = new MergeFunction(true);

    private static final class MergeFunction extends LazyFunction {
        private final boolean unique;

        public MergeFunction(boolean unique) {
            this.unique = unique;
        }

        @Override
        protected Object compute(final Value arg) {
            ListObject list = arg.asList();
            final Iterator<Value> combinedit = Iterators.merge(list.get(0).asList().asIterator(), list.get(1).asList()
                    .asIterator(), unique);
            final DelayedList<Value> col = Iterators.delayedList(combinedit);
            final class MergedList extends AbstractList {
                public Value get(int i) {
                    return col.get(i);
                }

                @Override
                public boolean has(int i) {
                    return col.has(i);
                }

                public int size() {
                    return col.size(); // TODO calculate directly; this does not
                    // allow streams.
                }
            }
            return new MergedList();
        }
    }

    /** Filters a list by the predicate */
    public static Function filter(final Function predicate) {
        return new LazyFunction() {
            @Override
            protected Object compute(Value arg) {
                Iterator<Value> argIt = arg.asList().asIterator();
                F<Value, Boolean> pred = new F<Value, Boolean>() {
                    public Boolean call(Value el) {
                        return predicate.call(el).asBoolean();
                    }
                };
                final Iterator<Value> filteredIt = Iterators.filter(argIt, pred);
                final DelayedList<Value> col = Iterators.delayedList(filteredIt);
                return new DelayedListAdapter(col);
            }
        };
    }

    /** Distribute left */
    public static final Function DISTL = new LazyFunction() {
        @Override
        protected Object compute(final Value arg) {
            final ListObject list = arg.asList();
            final Value val = list.get(0);

            final class DistributeLeftList extends LazyList {
                @Override
                protected Value value(final int i) {
                    return new ImmediateValue(new LazyList() {
                        @Override
                        protected Value value(int j) {
                            if (0 == j) {
                                return val;
                            } else {
                                return list.get(j).asList().get(i);
                            }
                        }

                        public int size() {
                            return list.size();
                        }
                    });
                }

                public int size() {
                    return list.get(1).asList().size();
                }
            }
            return new DistributeLeftList();
        }
    };
}
