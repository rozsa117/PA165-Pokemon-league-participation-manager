
package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import java.util.List;

/**
 * Interface of Pokemon service.
 *
 * @author Tibor Zauko 433531
 */
public interface PokemonService {

    /**
     * Creates a new Pokemon.
     * @param pokemon Pokemon to create.
     */
    void createPokemon(Pokemon pokemon);

    /**
     * Change the nickname of the Pokemon
     * @param pokemon   Pokemon whose nickname shall change.
     * @param newNickname Pokemon's new nickname.
     */
    void renamePokemon(Pokemon pokemon, String newNickname);

    /**
     * Increase a Pokemon's level by given amount.
     * @param pokemon Pokemon whose level shall be increased.
     * @param to Levels to increase the Pokemon's level to (must be positive,
     * must not exceed 100, must be higher than the Pokemon's current level).
     * @throws LevelNotIncreasedException when the specified target level is
     * lower or equal than the Pokemon's current level.
     */
    void increasePokemonLevel(Pokemon pokemon, int to)
            throws LevelNotIncreasedException;

    /**
     * Evolve a pokemon into it's given evolution.
     * @param pokemon Pokemon which shall be evolved.
     * @param evolveInto The desired evolution.
     * @throws InvalidPokemonEvolutionException when current species can't evolve into the desired species.
     */
    void evolvePokemon(Pokemon pokemon, PokemonSpecies evolveInto)
            throws InvalidPokemonEvolutionException;

    /**
     * Release the Pokemon into the wild (a.k.a. delete the Pokemon).
     * @param pokemon Pokemon to release.
     */
    void releasePokemon(Pokemon pokemon);

    /**
     * Find the Pokemon of the given id.
     * @param id The id of the Pokemon.
     * @return Found Pokemon, null if none found.
     */
    Pokemon findPokemonById(Long id);

    /**
     * Gift a Pokemon to another trainer.
     * @param pokemon Pokemon that is being gifted.
     * @param newTrainer The new trainer of the Pokemon.
     */
    void giftPokemon(Pokemon pokemon, Trainer newTrainer);
    
    /**
     * Finds all pokemon of a trainer;
     * @param trainer Trainer to find pokemon of.
     * @return The list of all pokemon of trainer.
     */
    public List<Pokemon> getPokemonOfTrainer(Trainer trainer);
}
