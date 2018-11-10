package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
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
     */
    public Trainer createTrainer(Trainer trainer, String password);

    /**
     * Renames trainer
     *
     * @param trainer Trainer
     * @param newName New name
     * @param newSurname New Surname
     */
    public void renameTrainer(Trainer trainer, String newName, String newSurname);

    /**
     * Add new pokemon to trainer
     * @param trainer Trainer 
     * @param pokemon Pokemon 
     */
    public void addPokemon(Trainer trainer, Pokemon pokemon);

    /**
     * Remove pokemon from trainer
     * @param trainer Trainer 
     * @param pokemon Pokemon 
     */
    public void removePokemon(Trainer trainer, Pokemon pokemon);

    /**
     * Get list of all trainers
     * @return list of all trainers
     */
    public List<Trainer> getAllTrainers();

    /**
     * Find trainer by Id
     * @param id
     * @return Trainer
     */
    public Trainer getTrainerWithId(Long id);

    /**
     * Autenticate trainer with password
     * @param trainer Trainer
     * @param password Unencrypted password
     * @return true only if password matches stored hash
     */
    public Boolean authenticate(Trainer trainer, String password);

    /**
     * Is Gym Leader?
     * @param trainer Trainer
     * @return true only if the Trainer is a Gym Leader
     */
    public Boolean isGymLeader(Trainer trainer);
}
