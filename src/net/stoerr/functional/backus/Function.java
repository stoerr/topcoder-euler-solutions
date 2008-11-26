package net.stoerr.functional.backus;

/**
 * A function in the sense of backus, calculating a value.
 * @author hps
 * @since 26.11.2008
 */
public interface Function {

    Value call(Value arg);
    
}
