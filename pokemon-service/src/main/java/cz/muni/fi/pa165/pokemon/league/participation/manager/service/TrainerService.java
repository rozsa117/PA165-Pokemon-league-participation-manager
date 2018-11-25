package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
@Service
public interface TrainerService {

    /**
     * Create new trainer
     *
     * @param trainer Trainer
     * @param password Unencrypted password
     * @return new trainer id
     * @throws NoAdministratorException when there is not a single administrator
     */
    public Trainer createTrainer(Trainer trainer, String password) throws NoAdministratorException;

    /**
     * Renames trainer
     *
     * @param trainer Trainer
     * @param newName New name
     * @param newSurname New Surname
     */
    public void renameTrainer(Trainer trainer, String newName, String newSurname);

    /**
     * Get list of all trainers
     * 
     * @return list of all trainers
     */
    public List<Trainer> getAllTrainers();

    /**
     * Find trainer by Id.
     * 
     * @param id The id of the trainer to find.
     * @return The found trainer.
     */
    public Trainer getTrainerWithId(Long id);

    /**
     * Autenticate trainer with password
     * 
     * @param trainer Trainer
     * @param password Unencrypted password
     * @return true only if password matches stored hash
     */
    public boolean authenticate(Trainer trainer, String password);

    /**
     * Is Gym Leader?
     * 
     * @param trainer Trainer
     * @return true only if the Trainer is a Gym Leader
     */
    public boolean isGymLeader(Trainer trainer);
    
    /**
     * Set admin flag
     * 
     * @param trainer Trainer to be updated
     * @param admin New admin status
     * @throws NoAdministratorException when there is not a single admin after the
     * modification
     */
    public void setAdmin(Trainer trainer, boolean admin) throws NoAdministratorException;
    
    /**
     * Change trainer password
     * 
     * @param trainer Trainer to be updated
     * @param oldPassword original password
     * @param newPassword new password
     * @return true if password was successfully updated, false if authentication
     * of old password failed
     */
    public boolean changePassword(Trainer trainer, String oldPassword, String newPassword);
}
