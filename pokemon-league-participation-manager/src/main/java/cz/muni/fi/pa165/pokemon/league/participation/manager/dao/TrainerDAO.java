/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.*;
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
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void createTrainer(Trainer trainer) throws ServiceFailureException;

    /**
     * Updates trainer in database.
     * 
     * @param trainer treiner to be updated.
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void updateTrainer(Trainer trainer) throws ServiceFailureException;

    /**
     * Deletes a trainer from database
     * 
     * @param trainer trainer to be deleted
     * @throws IllegalArgumentException when triner is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void deleteTrainer(Trainer trainer) throws ServiceFailureException;
    
    /**
     * Returns the trainer with given id.
     * 
     * @param id primary key of the requested trainer.
     * @return requested trainer, null in case no such trainer exists.
     * @throws IllegalArgumentException when id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    Trainer findTrainerById(Long id) throws ServiceFailureException;
    
    /**
     * Returns list of all trainers.
     * 
     * @return lsit of all trainers.
     * @throws ServiceFailureException when db operation fails. 
     */
    List<Trainer> getAllTrainers() throws ServiceFailureException;
    
}