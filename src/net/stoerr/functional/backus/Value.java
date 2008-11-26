package net.stoerr.functional.backus;

import java.util.List;

/**
 * A value, {@link ImmediateValue} or {@link LazyValue}.
 * The actual value might be a {@link String}, a {@link Number} or a {@link List}.
 * @author hps
 * @since 26.11.2008
 */
public interface Value {

    Object get();
    
}
