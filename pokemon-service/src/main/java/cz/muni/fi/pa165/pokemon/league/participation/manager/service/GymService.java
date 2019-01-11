package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge;
import java.util.List;

/**
 * Interface of Gym service.
 *
 * @author Tamás Rózsa 445653
 */
public interface GymService {

    /**
     * Create a new Gym.
     *
     * @param gym Gym to create.
     * @throws EntityIsUsedException when the GymLeader is used as a GymLeader on
     * another Gym
     * @throws IllegalArgumentException in case the gym already exists.
     */
    void createGym(Gym gym) throws EntityIsUsedException;

    /**
     * Updates the location of an existing gym.
     *
     * @param gym Gym to be updated.
     * @param newLocation new location of the gym.
     */
    void updateGymLocation(Gym gym, String newLocation);

    /**
     * Changes the type of an existing gym.
     *
     * @param gym Gym to be updated.
     * @param trainer changing Trainer
     * @param newType New type of the gym.
     * @throws InsufficientRightsException when other trainer than Gym Leader tries
     * to change Gym Type
     */
    void changeGymType(Gym gym, Trainer trainer, PokemonType newType)
            throws InsufficientRightsException;

    /**
     * Changes the leader of an existing gym. Also changes its type specialization to none.
     *
     * @param gym Gym to be updated.
     * @param newGymLeader New leader of the gym.
     * @throws EntityIsUsedException when the newGymLeader is a GymLeader of
     * another Gym already
     */
    void changeGymLeader(Gym gym, Trainer newGymLeader) throws EntityIsUsedException;

    /**
     * Removes an existing gym.
     *
     * @param gym Gym to be removed.
     * @throws EntityIsUsedException when there is a badge for this Gym
     */
    void removeGym(Gym gym) throws EntityIsUsedException;

    /**
     * Finds the gym with given id.
     *
     * @param id Id of the gym.
     * @return Gym with the given id, null in case such gym does not exists.
     */
    Gym findGymById(Long id);

    /**
     * Returns a list of all gyms.
     *
     * @return List of all gyms.
     */
    List<Gym> getAllGyms();

    /**
     * Gets the leader of the gym.
     *
     * @param gym Gym to find the leader.
     * @return The leader of the gym.
     */
    Trainer getGymLeader(Gym gym);

    /**
     * Returns a list of all gyms with given type.
     *
     * @param type The type of gym.
     * @return List of all gyms with given type.
     */
    List<Gym> findGymsByType(PokemonType type);

    /**
     * Returns gym with the given leader.
     *
     * @param trainer The leader of the gym.
     * @return The gym with the given trainer, null in case no such gym exists.
     */
    Gym findGymByLeader(Trainer trainer);

    /**
     * Returns a list containing all gyms, and where applicable a badge of the trainer from that gym.
     *
     * For gyms from which the trainer doesn't have a badge, null is set as badge.
     *
     * @param trainer Trainer whose badges shall be fetched.
     * @return List of gyms and badges.
     */
    List<GymAndBadge> getAllGymsAndBadgesOfTrainer(Trainer trainer);

}
