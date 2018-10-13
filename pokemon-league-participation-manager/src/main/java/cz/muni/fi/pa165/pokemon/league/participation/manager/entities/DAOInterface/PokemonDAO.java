
package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import java.util.List;

/**
 * Interface of data access objects for Pokemon entity class.
 *
 * @author Tibor Zauko 433531
 */
public interface PokemonDAO {

    /**
     * Store a new Pokemon.
     *
     * @param pokemon Pokemon to store.
     * @throws ServiceFailureException when a database operation fails.
     * @throws IllegalArgumentException when pokemon is null.
     */
    void createPokemon(Pokemon pokemon)
            throws ServiceFailureException;

    /**
     * Update an existing Pokemon.
     *
     * @param pokemon Pokemon to update.
     * @throws ServiceFailureException when a database operation fails.
     * @throws IllegalArgumentException when pokemon is null.
     */
    void updatePokemon(Pokemon pokemon)
            throws ServiceFailureException;

    /**
     * Remove a Pokemon.
     *
     * @param pokemon Pokemon to remove.
     * @throws ServiceFailureException when a database operation fails.
     * @throws IllegalArgumentException when pokemon is null.
     */
    void deletePokemon(Pokemon pokemon)
            throws ServiceFailureException;

    /**
     * Find a Pokemon based on its id.
     *
     * @param id primary key of the requested Pokemon.
     * @return the requested Pokemon, null if no such Pokemon exists.
     * @throws ServiceFailureException when a database operation fails.
     * @throws IllegalArgumentException when id is null.
     */
    Pokemon findPokemonById(Long id)
            throws ServiceFailureException;

    /**
     * Return a list of all Pokemon.
     *
     * @return list of all Pokemon. An empty list will be returned if no Pokemon exist.
     * @throws ServiceFailureException when a database operation fails.
     */
    List<Pokemon> getAllPokemon()
            throws ServiceFailureException;

}
