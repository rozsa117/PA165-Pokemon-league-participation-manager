package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * An exception for situations when altering an evolution chain would create
 * a circular evolution chain.
 * 
 * @author Tibor Zauko 433531
 */
public class CircularEvolutionChain extends Exception {

    /**
     * Creates a new instance of <code>CircularEvolutionChain</code> without
     * detail message.
     */
    public CircularEvolutionChain() {
    }

    /**
     * Constructs an instance of <code>CircularEvolutionChain</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CircularEvolutionChain(String msg) {
        super(msg);
    }
}
