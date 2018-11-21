package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for cases when a trainer requests that per Pokemon evolves into
 * a species that it can't evolve into.
 * 
 * @author Tibor Zauko 433531
 */
public class InvalidPokemonEvolutionException extends Exception {

    public InvalidPokemonEvolutionException() {
    }

    public InvalidPokemonEvolutionException(String msg) {
        super(msg);
    }

    public InvalidPokemonEvolutionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
