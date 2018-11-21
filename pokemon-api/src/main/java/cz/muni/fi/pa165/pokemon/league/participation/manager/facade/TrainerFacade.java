package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import java.util.List;

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
    public Long createTrainer(TrainerCreateDTO trainer) throws NoAdministratorException;

    /**
     * Change trainer name and surname
     *
     * @param trainerRename trainer to be updated
     */
    public void renameTrainer(TrainerRenameDTO trainerRename);

    /**
     * Get list of all trainers
     *
     * @return list of all trainers
     */
    public List<TrainerDTO> getAllTrainers();

    /**
     * Find a Trainer by Id
     *
     * @param trainerId Id of a Trainer to be found
     * @return Trainer found Trainer, null if not found
     */
    public TrainerDTO getTrainerWithId(Long trainerId);

    /**
     * Autenticate trainer with password
     *
     * @param trainer Trainer to be autenticated
     * @return true only if password matches stored hash
     */
    public Boolean authenticate(TrainerAuthenticateDTO trainer);

    /**
     * Change Trainer password
     *
     * @param trainerChangePassword trainer DTO with old and new password
     * @return true if password successfully changed, false if original password 
     * authentication failed
     */
    public boolean changePassword(TrainerChangePasswordDTO trainerChangePassword);

    /**
     * Is Gym Leader?
     *
     * @param trainerId Trained id
     * @return true only if the Trainer is a Gym Leader
     */
    public Boolean isGymLeader(Long trainerId);

    /**
     * Set admin flag
     * 
     * @param trainerId Trainer to be set
     * @param admin Admin flag
     * @throws NoAdministratorException there must be at least one administrator left
     */
    public void setAdmin (Long trainerId, boolean admin) throws NoAdministratorException;

}
