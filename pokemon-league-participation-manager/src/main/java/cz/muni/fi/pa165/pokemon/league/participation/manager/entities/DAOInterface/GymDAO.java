package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.IDException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ValidationException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;

import java.util.List;

/**
 * Interface of Data Access Objects for Gym class
 *
 * @author Michal Mokros 456442
 */
public interface GymDAO {

    /**
     * Stores a gym to database, Id is automatically generated and stored.
     *
     * @param gym to be created.
     * @throws IllegalArgumentException when gym is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case gym is not valid (ex. Trainer doesn't exist).
     * @throws IDException if gym has it's Id already set.
     */
    void createGym(Gym gym) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Updates gym in database.
     *
     * @param gym to be updated.
     * @throws IllegalArgumentException when gym is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case gym is not valid.
     * @throws IDException if gym has no Id set or Id doesn't exist.
     */
    void updateGym(Gym gym) throws ServiceFailureException, ValidationException, IDException;

    /**
     * Deletes gym from the database.
     *
     * @param gym to be deleted.
     * @throws IllegalArgumentException when gym is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws IDException if gym has no Id set or such Id doesn't exist.
     */
    void deleteGym(Gym gym) throws ServiceFailureException, IDException;

    /**
     * Returns the gym with given Id.
     *
     * @param id primary key of the requested gym.
     * @return requested Gym, null in case no such gym exists.
     * @throws IllegalArgumentException when Id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    Gym findGym(Long id) throws ServiceFailureException;

    /**
     * Returns list of all gyms.
     *
     * @return list of all gyms.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Gym> getAllGyms() throws ServiceFailureException;
}
