/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.*;
import java.util.List;

/**
 *
 * @author Tamás Rózsa 445653
 */
public interface TrainerDAO {
    
    /**
     * Stores a trainer to database. Id is automatically generated and stored.
     * 
     * @param trainer trainer to be created.
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case trainer is not valid (ex. date of 
     * birth is in the future).
     * @throws IDException if trainer has its id already set.
     */
    void createTrainer(Trainer trainer) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Updates trainer in database.
     * 
     * @param trainer treiner to be updated.
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case trainer is not valid (ex. date of 
     * birth is in the future).
     * @throws IDException if the trainer has null id or no such id exists 
     * in the database.
     */
    void updateTrainer(Trainer trainer) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Deletes a trainer from database
     * 
     * @param trainer trainer to be deleted
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws IDException if the trainer has null id or no such id exists 
     * in the database.
     */
    void deleteTrainer(Trainer trainer) throws ServiceFailureException, IDException;
    
    /**
     * Returns the trainer with given id.
     * 
     * @param id primary key of the requested trainer.
     * @return requested trainer, null in case no such trainer exists.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    Trainer findTrainer(Long id) throws ServiceFailureException;
    
    /**
     * Returns list of all trainers.
     * 
     * @return lsit of all trainers.
     * @throws ServiceFailureException when db operation fails. 
     */
    List<Trainer> getAllTrainers() throws ServiceFailureException;
    
}