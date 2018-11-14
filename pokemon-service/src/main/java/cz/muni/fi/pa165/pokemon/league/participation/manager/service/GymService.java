package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.List;

/**
 * Interface of Gym service.
 * 
 * @author Tamás Rózsa 445653
 */
public interface GymService {
    
    /**
     * Create a new Gym.
     * @param gym Gym to create.
     * @throws IllegalArgumentException in case the gym already exists.
     */
    public void createGym(Gym gym);
    
    /**
     * Updates the location of an existing gym.
     * @param gym Gym to be updated.
     * @param newLocaton new location of the gym.
     */
    public void updateGymLocation(Gym gym, String newLocaton);
    
    /**
     * Changes the type of an existing gym.
     * @param gym Gym to be updated.
     * @param newType New type of the gym.
     */
    public void changeGymType(Gym gym, PokemonType newType);
    
    /**
     * Changes the leader of an existing gym.
     * @param gym Gym to be updated.
     * @param newGymLeader New leader of the gym.
     */
    public void changeGymLeader(Gym gym, Trainer newGymLeader);
    
    /**
     * Removes an existing gym.
     * @param gym Gym to be removed.
     */
    public void removeGym(Gym gym);
    
    /**
     * Finds the gym with given id.
     * @param id Id of the gym.
     * @return Gym with the given id, null in case such gym does not exists.
     */
    public Gym findGymById(Long id);
    
    /**
     * Returns a list of all gyms.
     * @return List of all gyms.
     */
    public List<Gym> getAllGyms();
    
    /**
     * Gets the leader of the gym.
     * @param gym Gym to find the leader.
     * @return The leader of the gym.
     */
    public Trainer getGymLeader(Gym gym);

    /**
     * Returns a list of all gyms with given type.
     * @param type The type of gym.
     * @return List of all gyms with given type.
     */
    public List<Gym> findGymsByType(PokemonType type);
    
    /**
     * Returns gym with the given leader.
     * @param trainer The leader of the gym.
     * @return The gym with the given trainer, null in case no such gym exists.
     */
    public Gym findGymByLeader(Trainer trainer);
}
