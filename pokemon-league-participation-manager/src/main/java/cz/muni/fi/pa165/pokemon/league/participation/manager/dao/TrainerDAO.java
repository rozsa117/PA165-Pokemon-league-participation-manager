package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import java.util.List;

/**
 * Interface for data access object fot trainer entity class.
 * 
 * @author Tamás Rózsa 445653
 */
public interface TrainerDAO {
    
    /**
     * Stores a trainer to database. Id is automatically generated and stored.
     * 
     * @param trainer trainer to be created.
     * @throws IllegalArgumentException when trainer is null.
     */
    void createTrainer(Trainer trainer);

    /**
     * Updates trainer in database.
     * 
     * @param trainer treiner to be updated.
     * @throws IllegalArgumentException when triner is null.
     */
    void updateTrainer(Trainer trainer);

    /**
     * Deletes a trainer from database
     * 
     * @param trainer trainer to be deleted
     * @throws IllegalArgumentException when triner is null.
     */
    void deleteTrainer(Trainer trainer);
    
    /**
     * Returns the trainer with given id.
     * 
     * @param id primary key of the requested trainer.
     * @return requested trainer, null in case no such trainer exists.
     * @throws IllegalArgumentException when id is null.
     */
    Trainer findTrainerById(Long id);
    
    /**
     * Returns list of all trainers.
     * 
     * @return lsit of all trainers.
     */
    List<Trainer> getAllTrainers();
    
}