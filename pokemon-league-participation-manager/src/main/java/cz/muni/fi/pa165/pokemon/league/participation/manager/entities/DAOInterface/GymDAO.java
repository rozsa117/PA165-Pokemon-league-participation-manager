package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
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
     */
    void createGym(Gym gym) throws ServiceFailureException;

    /**
     * Updates gym in database.
     *
     * @param gym to be updated.
     * @throws IllegalArgumentException when gym is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void updateGym(Gym gym) throws ServiceFailureException;

    /**
     * Deletes gym from the database.
     *
     * @param gym to be deleted.
     * @throws IllegalArgumentException when gym is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void deleteGym(Gym gym) throws ServiceFailureException;

    /**
     * Returns the gym with given Id.
     *
     * @param id primary key of the requested gym.
     * @return requested Gym, null in case no such gym exists.
     * @throws IllegalArgumentException when Id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    Gym findGymById(Long id) throws ServiceFailureException;

    /**
     * Returns list of all gyms.
     *
     * @return list of all gyms.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Gym> getAllGyms() throws ServiceFailureException;
}
