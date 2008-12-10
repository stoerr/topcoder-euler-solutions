package net.stoerr.euler.help;

/**
 * Poor mans closure: something to be executed on the args - with sideeffects.
 */
public interface Block<T> {
    
    void exec(T arg);

}
