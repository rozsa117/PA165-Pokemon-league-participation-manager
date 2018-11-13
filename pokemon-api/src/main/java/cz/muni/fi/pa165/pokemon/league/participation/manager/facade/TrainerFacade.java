package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import java.util.List;

/**
 * Facade interface for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
public interface TrainerFacade {

    /**
     * Create new trainer
     *
     * @param trainer
     * @return new trainer id
     */
    public Long createTrainer(TrainerCreateDTO trainer);

    /**
     * Renames trainer
     *
     * @param trainerRename DTO with new name and surname
     */
    public void renameTrainer(TrainerRenameDTO trainerRename);

    /**
     * Add new pokemon to trainer
     * @param trainerId Trainer Id
     * @param pokemonId Pokemon Id
     */
    public void addPokemon(Long trainerId, Long pokemonId);

    /**
     * Remove pokemon from trainer
     * @param trainerId Trainer Id
     * @param pokemonId Pokemon Id
     */
    public void removePokemon(Long trainerId, Long pokemonId);

    /**
     * Get list of all trainers
     * @return list of all trainers
     */
    public List<TrainerDTO> getAllTrainers();

    /**
     * Find trainer by Id
     * @param trainerId
     * @return Trainer
     */
    public TrainerDTO getTrainerWithId(Long trainerId);

    /**
     * Autenticate trainer with password
     * @param trainer Trainer
     * @return true only if password matches stored hash
     */
    public Boolean authenticate(TrainerAuthenticateDTO trainer);
    
    /**
     *
     * @param trainerChangePassword trainer DTO with old and new password
     */
    public void changePassword(TrainerChangePasswordDTO trainerChangePassword);

    /**
     * Is Gym Leader?
     * @param trainerId trained id
     * @return true only if the Trainer is a Gym Leader
     */
    public Boolean isGymLeader(Long trainerId);
    
    /**
     * Get all own pokemons
     * @param trainerId Trainer id
     * @return
     */
    public List<PokemonDTO> getOwnPokemons(Long trainerId);
    
}
