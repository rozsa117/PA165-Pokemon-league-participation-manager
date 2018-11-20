package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception thrown when entity tries to change not theirs attribute
 *
 * @author Michal Mokros 456442
 */
public class UnallowedAccessException extends Exception {

    public UnallowedAccessException() {
        super("Entity tried to access unallowed object");
    }

    public UnallowedAccessException(String message) {
        super(message);
    }

    public UnallowedAccessException(Throwable cause) {
        super(cause);
    }

    public UnallowedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
