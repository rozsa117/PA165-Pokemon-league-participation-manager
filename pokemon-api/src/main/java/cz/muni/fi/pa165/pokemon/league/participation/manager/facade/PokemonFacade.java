package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.EvolvePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GiftPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.RenamePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.LevelUpPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ReleasePokemonDTO;
import java.util.List;

/**
 * Interface of Pokemon facade.
 * 
 * @author Tibor Zauko 433531
 */
public interface PokemonFacade {
    
    Long createPokemon(PokemonCreateDTO pokemon);
    
    PokemonDTO findPokemonById(Long id);
    
    List<PokemonDTO> getPokemonOfTrainer(Long trainerId);
    
    void releasePokemon(ReleasePokemonDTO id);
    
    void renamePokemon(RenamePokemonDTO newNickname);
    
    void levelUpPokemon(LevelUpPokemonDTO levelIncrease);
    
    void evolvePokemon(EvolvePokemonDTO chosenEvolution);
    
    void giftPokemon(GiftPokemonDTO gift);
}
