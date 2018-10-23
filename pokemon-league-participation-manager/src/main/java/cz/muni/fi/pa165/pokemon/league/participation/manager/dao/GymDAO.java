package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

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
     */
    void createGym(Gym gym);

    /**
     * Updates gym in database.
     *
     * @param gym to be updated.
     * @throws IllegalArgumentException when gym is null.
     */
    void updateGym(Gym gym);

    /**
     * Deletes gym from the database.
     *
     * @param gym to be deleted.
     * @throws IllegalArgumentException when gym is null.
     */
    void deleteGym(Gym gym);

    /**
     * Returns the gym with given Id.
     *
     * @param id primary key of the requested gym.
     * @return requested Gym, null in case no such gym exists.
     * @throws IllegalArgumentException when Id is null.
     */
    Gym findGymById(Long id);

    /**
     * Returns list of all gyms.
     *
     * @return list of all gyms.
     */
    List<Gym> getAllGyms();
}
