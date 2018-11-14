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
    
    /**
     * Create a Pokemon.
     * 
     * @param pokemon Specification of Pokemon to create.
     * @return ID of the created Pokemon.
     */
    Long createPokemon(PokemonCreateDTO pokemon);
    
    /**
     * Finds Pokemon with the specified ID.
     * 
     * @param id ID of the Pokemon to find.
     * @return Found Pokemon, null if no such Pokemon exists.
     */
    PokemonDTO findPokemonById(Long id);
    
    /**
     * Return all Pokemon of a trainer.
     * 
     * @param trainerId ID of the trainer whose Pokemon shall be returned.
     * @return List of all of the trainer's Pokemon.
     */
    List<PokemonDTO> getPokemonOfTrainer(Long trainerId);
    
    /**
     * Release a Pokemon (remove it).
     * 
     * @param releasedPokemon Specification of Pokemon to release. 
     */
    void releasePokemon(ReleasePokemonDTO releasedPokemon);
    
    /**
     * Rename a Pokemon (change it's nickname).
     * 
     * @param newNickname Specification of the new nickname.
     */
    void renamePokemon(RenamePokemonDTO newNickname);
    
    /**
     * Increase the level of a Pokemon to given level.
     * 
     * @param newLevel Specification of the new level. 
     */
    void levelUpPokemon(LevelUpPokemonDTO newLevel);
    
    /**
     * Change the Pokemon's species to one of it's current species' evolutions.
     * 
     * @param chosenEvolution Specification of the chosen evolution.
     */
    void evolvePokemon(EvolvePokemonDTO chosenEvolution);
    
    /**
     * Gift Pokemon to another trainer (make the Pokemon belong to the other trainer).
     * 
     * @param gift Specification of gifted Pokemon and which trainer it is gifted to.
     */
    void giftPokemon(GiftPokemonDTO gift);
}
