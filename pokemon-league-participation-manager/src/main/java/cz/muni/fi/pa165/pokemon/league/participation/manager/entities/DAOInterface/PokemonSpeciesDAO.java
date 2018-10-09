package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.*;
import java.util.List;

/**
 *
 * @author Tamás Rózsa 445653
 */
public interface PokemonSpeciesDAO {

    /**
     * Stores a pokemonSpecies to database. Id is automatically generated and
     * stored.
     *
     * @param pokemonSpecies pokemonSpecies to be created.
     * @throws IllegalArgumentException when pokemonSpecies is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case pokemonSpecies is not valid
     * @throws IDException if pokemonSpecies has its id already set.
     */
    void createPokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Updates pokemonSpecies in database.
     *
     * @param pokemonSpecies treiner to be updated.
     * @throws IllegalArgumentException when pokemonSpecies is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case pokemonSpecies is not valid 
     * @throws IDException if the pokemonSpecies has null id or no such id
     * exists in the database.
     */
    void updatePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Deletes a pokemonSpecies from database
     *
     * @param pokemonSpecies pokemonSpecies to be deleted
     * @throws IllegalArgumentException when pokemonSpecies is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws IDException if the pokemonSpecies has null id or no such id
     * exists in the database.
     */
    void deletePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException, IDException;

    /**
     * Returns the pokemonSpecies with given id.
     *
     * @param id primary key of the requested pokemonSpecies.
     * @return requested pokemonSpecies, null in case no such pokemonSpecies
     * exists.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    PokemonSpecies findPokemonSpecies(Long id) throws ServiceFailureException;

    /**
     * Returns list of all pokemonSpeciess.
     *
     * @return lsit of all pokemonSpeciess.
     * @throws ServiceFailureException when db operation fails.
     */
    List<PokemonSpecies> getAllPokemonSpeciess() throws ServiceFailureException;

}
