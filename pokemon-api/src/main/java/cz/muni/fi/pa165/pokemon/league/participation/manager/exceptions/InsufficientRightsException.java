package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception thrown when entity tries to change not theirs attribute
 *
 * @author Michal Mokros 456442
 */
public class InsufficientRightsException extends Exception {

    public InsufficientRightsException() {
        super("Entity tried to access unallowed object");
    }

    public InsufficientRightsException(String message) {
        super(message);
    }

    public InsufficientRightsException(String message, Throwable cause) {
        super(message, cause);
    }
}
