package net.stoerr.functional.backus;

import java.util.Iterator;

/**
 * An object that actually represents a list.
 * @author hps
 * @since 26.11.2008
 */
public interface ListObject {

    /** Possibly lazily calculated value at position i. */
    Value get(int i);
    
    /** Whether there is a value at position i */
    boolean has(int i);
    
    /** Not necessarily finite - might throw up. */
    int size();
    
    Iterator<Value> asIterator();
    
}
