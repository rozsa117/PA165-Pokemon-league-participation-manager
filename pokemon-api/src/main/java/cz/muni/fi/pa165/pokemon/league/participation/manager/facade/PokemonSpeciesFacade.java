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
    
    Long createPokemonSpecies(PokemonSpeciesCreateDTO species);
    
    PokemonSpeciesDTO findPokemonSpeciesById(Long id);
    
    List<PokemonSpeciesDTO> getAllPokemonSpecies();
    
    List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(Long speciesId);
    
    void changeTyping(ChangeTypingDTO newTyping);
    
    void changePreevolution(ChangePreevolutionDTO newPreevolution);
    
}
