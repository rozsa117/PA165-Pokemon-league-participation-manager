package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for such cases when the Pokemon's level would not increase when it
 * should have.
 * 
 * @author Tibor Zauko 433531
 */
public class LevelNotIncreasedException extends Exception {

    public LevelNotIncreasedException() {
    }

    public LevelNotIncreasedException(String msg) {
        super(msg);
    }

    public LevelNotIncreasedException(String message, Throwable cause) {
        super(message, cause);
    }
}
