package net.stoerr.functional.backus;

import java.util.Iterator;

public class DebugFunctional {

    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(DebugFunctional.class);

    public static Function debugList(final String name) {
        final class DebugListFunction extends LazyFunction {
            @Override
            protected Object compute(final Value arg) {
                final ListObject list = arg.asList();
                final class DebugListObject extends AbstractList {
                    public Value get(int i) {
                        LOG.info(">>>" + name + ".get(" + i + ")");
                        Value res = list.get(i);
                        LOG.info("<<<" + name + ".get(" + i + ") = " + res);
                        return res;
                    }

                    public int size() {
                        LOG.info(">>>" + name + ".size()");
                        int res = list.size();
                        LOG.info("<<<" + name + ".size() = " + res);
                        return res;
                    }
                }
                ListObject deb = new DebugListObject();
                return deb;
            }
        }
        return new DebugListFunction();
    }

    /**
     * Completely evaluates lazy stuff: calls a get in every list recursively
     * until item 10.
     */
    public static final Function UNLAZY = new AbstractFunction() {
        public Value call(Value arg) {
            unlazy(arg);
            return arg;
        }

        private void unlazy(Value arg) {
            final Object object = arg.get();
            if (object instanceof ListObject) {
                ListObject list = (ListObject) object;
                int i = 0;
                while (list.has(i) && i < 10) {
                    unlazy(list.get(i++));
                }
            }
        }
    };

}
