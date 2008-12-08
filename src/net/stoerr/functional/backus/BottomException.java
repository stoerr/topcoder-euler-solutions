package net.stoerr.functional.backus;

/**
 * An Exception that represents Backus' bottom value. It terminates the
 * calculation.
 * 
 * @author hps
 * @since 26.11.2008
 */
public class BottomException extends RuntimeException {
    
    public BottomException(String message) {
        super(message);
    }

    private static final long serialVersionUID = -3088690273116658375L;

}
