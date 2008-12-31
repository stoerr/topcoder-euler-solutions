package net.stoerr.euler.help;

/**
 * Low footprint reference for call by reference emulation etc.
 */
public final class Ref<T> {
    public T v;

    @Override
    public String toString() {
        return String.valueOf(v);
    }
}
