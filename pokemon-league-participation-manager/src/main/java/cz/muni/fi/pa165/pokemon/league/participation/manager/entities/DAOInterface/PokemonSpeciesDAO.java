package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
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
     * @throws ServiceFailureException when db operation fails.
     */
    void createPokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException;

    /**
     * Updates pokemonSpecies in database.
     *
     * @param pokemonSpecies treiner to be updated.
     * @throws IllegalArgumentException when pokemonSpecies is not an entity or
     * is a detached entity.
     * @throws ServiceFailureException when db operation fails.
     */
    void updatePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException;

    /**
     * Deletes a pokemonSpecies from database
     *
     * @param pokemonSpecies pokemonSpecies to be deleted
     * @throws IllegalArgumentException when pokemonSpecies is null.
     * @throws ServiceFailureException when db operation fails. exists in the
     * database.
     */
    void deletePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException;

    /**
     * Returns the pokemonSpecies with given id.
     *
     * @param id primary key of the requested pokemonSpecies.
     * @return requested pokemonSpecies, null in case no such pokemonSpecies
     * exists.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    PokemonSpecies findPokemonSpeciesById(Long id) throws ServiceFailureException;

    /**
     * Returns list of all pokemonSpeciess.
     *
     * @return lsit of all pokemonSpeciess.
     * @throws ServiceFailureException when db operation fails.
     */
    List<PokemonSpecies> getAllPokemonSpecies() throws ServiceFailureException;

}
