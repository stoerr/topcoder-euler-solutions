package net.stoerr.functional.backus;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link ListObject} with immediately given values.
 * 
 * @author hps
 * @since 26.11.2008
 */
public final class ImmediateList extends AbstractList {

    private final List<Value> values;

    public ImmediateList(Object... objects) {
        values = new ArrayList<Value>();
        for (Object obj : objects) {
            values.add(new ImmediateValue(obj));
        }
    }
    
    public ImmediateList(List<Value> values) {
        this.values = values;
    }

    public Value get(int i) {
        return values.get(i);
    }

    public int size() {
        return values.size();
    }
}
