package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import java.util.List;

/**
 *
 * @author Jiří Medveď 38451
 */
public interface PokemonSpeciesDAO {

    /**
     * Returns the pokemonSpecies with given id.
     *
     * @param id primary key of the requested pokemonSpecies.
     * @return requested pokemonSpecies, null in case no such pokemonSpecies
     * exists.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    PokemonSpecies getPokemonSpeciesById(Long id) throws ServiceFailureException;

    /**
     * Returns list of all pokemonSpeciess.
     *
     * @return lsit of all pokemonSpeciess.
     * @throws ServiceFailureException when db operation fails.
     */
    List<PokemonSpecies> getAllPokemonSpecies() throws ServiceFailureException;

}
