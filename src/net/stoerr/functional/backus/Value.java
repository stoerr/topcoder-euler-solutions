package net.stoerr.functional.backus;

import java.util.List;

/**
 * A value, {@link ImmediateValue} or {@link LazyValue}. The actual value might
 * be a {@link String}, a {@link Number} or a {@link List}.
 * 
 * @author hps
 * @since 26.11.2008
 */
public interface Value {

    /** Get - and possibly calculate - the value. */
    Object get();

    /** Gets as ListObject. */
    ListObject asList();

    /** Gets as double value. */
    double asDouble();

    /** Gets as String value. */
    String asString();

    Value BOTTOM = new AbstractValue() {
        public Object get() {
            throw new BottomException("Got value of BOTTOM");
        }
    };
}
