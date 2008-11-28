package net.stoerr.functional.backus;

/**
 * Defines the various convenience methods of {@link Value}
 * 
 * @author hps
 * @since 26.11.2008
 */
public abstract class AbstractValue implements Value {

    public final ListObject asList() {
        return (ListObject) get();
    }

    public double asDouble() {
        return ((Number) get()).doubleValue();
    }

    public String asString() {
        return (String) get();
    }
    
    public boolean asBoolean() {
        return (Boolean) get();
    }

}
