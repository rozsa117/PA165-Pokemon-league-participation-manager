package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for cases when a trainer requests that per Pokemon evolves into
 * a species that it can't evolve into.
 * 
 * @author Tibor Zauko 433531
 */
public class InvalidPokemonEvolution extends Exception {

    /**
     * Creates a new instance of <code>PokemonInvalidEvolution</code> without
     * detail message.
     */
    public InvalidPokemonEvolution() {
    }

    /**
     * Constructs an instance of <code>PokemonInvalidEvolution</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidPokemonEvolution(String msg) {
        super(msg);
    }
}
