package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception thrown in case an invalid challenge status change is called.
 *
 * @author Tamás Rózsa 445653
 */
public class InvalidChallengeStatusChangeException extends Exception {

    public InvalidChallengeStatusChangeException() {
        super("Invalid status change");
    }

    public InvalidChallengeStatusChangeException(String message) {
        super(message);
    }

    public InvalidChallengeStatusChangeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
