package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for such cases when an entity for given ID doesn't exist.
 *
 * @author Tibor Zauko 433531
 */
public class NoSuchEntityException extends Exception {

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String msg) {
        super(msg);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
