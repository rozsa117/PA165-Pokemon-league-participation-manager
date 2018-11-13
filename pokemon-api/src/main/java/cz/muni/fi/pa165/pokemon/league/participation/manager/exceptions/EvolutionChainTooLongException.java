package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * An exception for situations when an evolution chain would be too long when
 * a new member were to be added.
 * 
 * @author Tibor Zauko 433531
 */
public class EvolutionChainTooLongException extends Exception {

    public EvolutionChainTooLongException() {
    }

    public EvolutionChainTooLongException(String message) {
        super(message);
    }

    public EvolutionChainTooLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvolutionChainTooLongException(Throwable cause) {
        super(cause);
    }

    public EvolutionChainTooLongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
