package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;

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
     * @throws IllegalArgumentException when pokemon is null.
     */
    void createPokemon(Pokemon pokemon);

    /**
     * Update an existing Pokemon.
     *
     * @param pokemon Pokemon to update.
     * @throws IllegalArgumentException when pokemon is null.
     */
    void updatePokemon(Pokemon pokemon);

    /**
     * Remove a Pokemon.
     *
     * @param pokemon Pokemon to remove.
     * @throws IllegalArgumentException when pokemon is null.
     */
    void deletePokemon(Pokemon pokemon);

    /**
     * Find a Pokemon based on its id.
     *
     * @param id primary key of the requested Pokemon.
     * @return the requested Pokemon, null if no such Pokemon exists.
     * @throws IllegalArgumentException when id is null.
     */
    Pokemon findPokemonById(Long id);

    /**
     * Return a list of all Pokemon.
     *
     * @return list of all Pokemon. An empty list will be returned if no Pokemon exist.
     */
    List<Pokemon> getAllPokemon();

    /**
     * Returns a list of all pokemons of a trainer.
     * 
     * @param trainer The trainer to find the pokemons of.
     * @return All pokemons of the trainer.
     */
    List<Pokemon> getAllPokemonsOfTrainer(Trainer trainer);
}
