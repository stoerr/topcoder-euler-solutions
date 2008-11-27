package net.stoerr.functional.backus;

import java.util.Iterator;

/**
 * Base class of list objects.
 * 
 * @author hps
 * @since 26.11.2008
 */
public abstract class AbstractList implements ListObject {

    public Iterator<Value> asIterator() {
        return new Iterator<Value>() {
            private volatile int nextI = 0;
            public boolean hasNext() {
                return has(nextI);
            }

            public Value next() {
                return get(nextI++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean has(int i) {
        return i<size(); 
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<");
        int i;
        for (i=0; has(i) && i<10; ++i) {
            buf.append(get(i)).append(", ");
        }
        if (has(i)) {
            buf.append("...");
        } else if (i>0) {
            buf.deleteCharAt(buf.length()-1);
            buf.deleteCharAt(buf.length()-1);
        }
        buf.append(">");
        return buf.toString();
    }
    
}
