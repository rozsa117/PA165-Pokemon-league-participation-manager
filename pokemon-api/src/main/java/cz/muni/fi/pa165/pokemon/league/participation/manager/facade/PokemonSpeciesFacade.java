package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import java.util.List;

/**
 * Interface for PokemonSpecies facade.
 *
 * @author Tibor Zauko 433531
 */
public interface PokemonSpeciesFacade {

    /**
     * Creates Pokemon species as described in species.
     *
     * @param species Species description.
     * @return ID of the created species.
     * @throws EvolutionChainTooLongException when the species is created as
     *     part of an evolutionary chain and the chain would have more than 3 members.
     * @throws NoSuchEntityException when evolvesFromId does not exist
     */
    Long createPokemonSpecies(PokemonSpeciesCreateDTO species)
            throws EvolutionChainTooLongException, NoSuchEntityException;

    /**
     * Finds Pokemon species with the specified ID.
     *
     * @param id ID of the species to find.
     * @return Found species, null if no such species exists.
     */
    PokemonSpeciesDTO findPokemonSpeciesById(Long id);

    /**
     * Return a list of all Pokemon species.
     *
     * @return List of all Pokemon species.
     */
    List<PokemonSpeciesDTO> getAllPokemonSpecies();

    /**
     * Return a list of all Pokemon directly evolving from given species.
     *
     * @param speciesId ID of the species whose evolutions shall be returned.
     * @return List of all species evolving from given species, if speciesId is
     * null, then list of all species which don't have preevolutions (NOTE: test
     * if null case is viable).
     */
    List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(Long speciesId);

    /**
     * Change typing of a Pokemon species.
     *
     * @param newTyping Specification of new typing.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void changeTyping(ChangeTypingDTO newTyping)
            throws NoSuchEntityException;

    /**
     * Change preevolution of a species.
     *
     * @param newPreevolution Specification of new preevolution.
     * @throws EvolutionChainTooLongException when a created evolution chain would have more than 3 members.
     * @throws CircularEvolutionChainException when a created evolution chain would be circular.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void changePreevolution(ChangePreevolutionDTO newPreevolution)
            throws EvolutionChainTooLongException, CircularEvolutionChainException, NoSuchEntityException;

    /**
     * Remove a Pokemon species.
     *
     * @param speciesId ID of the species to remove.
     * @throws EntityIsUsedException when deleting the entity is not possible
     * because it is referenced elsewhere.
     * @throws NoSuchEntityException when Pokemon Species of that id does not exist
     */
    void removePokemonSpecies(Long speciesId)
            throws EntityIsUsedException, NoSuchEntityException;

}
