package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;

import javax.validation.Valid;
import java.util.List;

/**
 * Interface of data access objects for PokemonSpecies entity class.
 *
 * @author Jiří Medveď 38451
 */
public interface PokemonSpeciesDAO {

    /**
     * Stores a pokemonSpecies to database. Id is automatically generated and
     * stored.
     *
     * @param pokemonSpecies pokemonSpecies to be created.
     * @throws IllegalArgumentException when pokemonSpecies is null.
     */
    void createPokemonSpecies(@Valid PokemonSpecies pokemonSpecies);

    /**
     * Updates pokemonSpecies in database.
     *
     * @param pokemonSpecies treiner to be updated.
     * @throws IllegalArgumentException when pokemonSpecies is not an entity or
     * is a detached entity.
     */
    void updatePokemonSpecies(@Valid PokemonSpecies pokemonSpecies);

    /**
     * Deletes a pokemonSpecies from database
     *
     * @param pokemonSpecies pokemonSpecies to be deleted
     * @throws IllegalArgumentException when pokemonSpecies is null.
     * database.
     */
    void deletePokemonSpecies(@Valid PokemonSpecies pokemonSpecies);

    /**
     * Returns the pokemonSpecies with given id.
     *
     * @param id primary key of the requested pokemonSpecies.
     * @return requested pokemonSpecies, null in case no such pokemonSpecies
     * exists.
     * @throws IllegalArgumentException when id is null.
     */
    PokemonSpecies findPokemonSpeciesById(Long id);

    /**
     * Returns list of all pokemonSpeciess.
     *
     * @return list of all pokemonSpeciess.
     */
    List<PokemonSpecies> getAllPokemonSpecies();

}
