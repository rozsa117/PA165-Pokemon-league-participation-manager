package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Facade interface for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
public interface TrainerFacade {

    /**
     * Create a new trainer
     *
     * @param trainer Trainer to be created
     * @return new trainer id
     * @throws NoAdministratorException there must be at least one administrator left
     */
    Long createTrainer(@Valid TrainerCreateDTO trainer) throws NoAdministratorException;

    /**
     * Change trainer name and surname
     *
     * @param trainerRename trainer to be updated
     */
    void renameTrainer(@Valid TrainerRenameDTO trainerRename);

    /**
     * Get list of all trainers
     *
     * @return list of all trainers
     */
    List<TrainerDTO> getAllTrainers();

    /**
     * Find a Trainer by Id
     *
     * @param trainerId Id of a Trainer to be found
     * @return Trainer found Trainer, null if not found
     */
    TrainerDTO getTrainerWithId(@NotNull Long trainerId);

    /**
     * Autenticate trainer with password
     *
     * @param trainer Trainer to be autenticated
     * @return true only if password matches stored hash
     */
    boolean authenticate(@Valid TrainerAuthenticateDTO trainer);

    /**
     * Change Trainer password
     *
     * @param trainerChangePassword trainer DTO with old and new password
     * @return true if password successfully changed, false if original password 
     * authentication failed
     */
    boolean changePassword(@Valid TrainerChangePasswordDTO trainerChangePassword);

    /**
     * Is Gym Leader?
     *
     * @param trainerId Trained id
     * @return true only if the Trainer is a Gym Leader
     */
    boolean isGymLeader(@NotNull Long trainerId);

    /**
     * Set admin flag
     * 
     * @param trainerId Trainer to be set
     * @param admin Admin flag
     * @throws NoAdministratorException there must be at least one administrator left
     */
    void setAdmin (@NotNull Long trainerId, boolean admin) throws NoAdministratorException;

    /**
     * Retrieve id of a trainer with given username.
     * 
     * @param username Username of the trainer.
     * @return The trainer having the username, null if such trainer doesn't exist.
     */
    TrainerDTO findTrainerByUsername(String username);

}
