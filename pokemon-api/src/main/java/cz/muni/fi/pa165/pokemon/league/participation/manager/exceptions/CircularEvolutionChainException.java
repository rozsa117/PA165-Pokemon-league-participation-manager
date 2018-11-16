package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * An exception for situations when altering an evolution chain would create
 * a circular evolution chain.
 * 
 * @author Tibor Zauko 433531
 */
public class CircularEvolutionChainException extends Exception {

    /**
     * Creates a new instance of CircularEvolutionChainException without
     * detail message.
     */
    public CircularEvolutionChainException() {
    }

    /**
     * Constructs an instance of CircularEvolutionChainException with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CircularEvolutionChainException(String msg) {
        super(msg);
    }

    public CircularEvolutionChainException(String message, Throwable cause) {
        super(message, cause);
    }
}
