package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.EvolvePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GiftPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.RenamePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.LevelUpPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ReleasePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
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
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    Long createPokemon(PokemonCreateDTO pokemon)
            throws NoSuchEntityException;
    
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
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    List<PokemonDTO> getPokemonOfTrainer(Long trainerId)
            throws NoSuchEntityException;
    
    /**
     * Release a Pokemon (remove it).
     * 
     * @param releasedPokemon Specification of Pokemon to release. 
     * @throws InsufficientRightsException when requesting trainer does not own manipulated entity.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void releasePokemon(ReleasePokemonDTO releasedPokemon)
            throws InsufficientRightsException, NoSuchEntityException;
    
    /**
     * Rename a Pokemon (change it's nickname).
     * 
     * @param newNickname Specification of the new nickname.
     * @throws InsufficientRightsException when requesting trainer does not own manipulated entity.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void renamePokemon(RenamePokemonDTO newNickname)
            throws InsufficientRightsException, NoSuchEntityException;
    
    /**
     * Increase the level of a Pokemon to given level.
     * 
     * @param newLevel Specification of the new level. 
     * @throws LevelNotIncreasedException when the specified target level is
     * lower than the Pokemon's current level.
     * @throws InsufficientRightsException when requesting trainer does not own manipulated entity.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void levelUpPokemon(LevelUpPokemonDTO newLevel)
            throws LevelNotIncreasedException, InsufficientRightsException, NoSuchEntityException;
    
    /**
     * Change the Pokemon's species to one of it's current species' evolutions.
     * 
     * @param chosenEvolution Specification of the chosen evolution.
     * @throws InvalidPokemonEvolutionException when current species can't evolve into the desired species.
     * @throws InsufficientRightsException when requesting trainer does not own manipulated entity.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void evolvePokemon(EvolvePokemonDTO chosenEvolution)
            throws InvalidPokemonEvolutionException, InsufficientRightsException, NoSuchEntityException;
    
    /**
     * Gift Pokemon to another trainer (make the Pokemon belong to the other trainer).
     * 
     * @param gift Specification of gifted Pokemon and which trainer it is gifted to.
     * @throws InsufficientRightsException when requesting trainer does not own manipulated entity.
     * @throws NoSuchEntityException when an entity of given ID doesn't exist.
     */
    void giftPokemon(GiftPokemonDTO gift) 
            throws InsufficientRightsException, NoSuchEntityException;
}
