package net.stoerr.functional.util;

/**
 * A sideeffect free function.
 * @author hps
 * @since 27.11.2008
 */
public interface F<Arg, Val> {

    Val call(Arg arg);
    
}
