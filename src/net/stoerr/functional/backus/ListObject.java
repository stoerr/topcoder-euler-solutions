package net.stoerr.functional.backus;

/**
 * An object that actually represents a list.
 * @author hps
 * @since 26.11.2008
 */
public interface ListObject {

    /** Possibly lazily calculated value at position i. */
    Value get(int i);
    
    /** Not necessarily finite - might throw up. */
    int size();
    
}
