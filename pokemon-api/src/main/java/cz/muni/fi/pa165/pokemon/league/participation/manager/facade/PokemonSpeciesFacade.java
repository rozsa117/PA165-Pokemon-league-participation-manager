package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
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
     */
    Long createPokemonSpecies(PokemonSpeciesCreateDTO species);
    
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
     */
    void changeTyping(ChangeTypingDTO newTyping);
    
    /**
     * Change preevolution of a species.
     * 
     * @param newPreevolution Specification of new preevolution.
     */
    void changePreevolution(ChangePreevolutionDTO newPreevolution);
    
    /**
     * Remove a Pokemon species.
     * 
     * @param speciesId ID of the species to remove.
     */
    void removePokemonSpecies(Long speciesId);
    
}
