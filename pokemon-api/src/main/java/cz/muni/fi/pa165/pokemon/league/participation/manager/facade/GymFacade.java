package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import java.util.List;

/**
 * Interface for gym facade.
 * 
 * @author Tamás Rózsa 445653
 */
public interface GymFacade {
    
        
    /**
     * Create a new Gym.
     * @param gym Gym to create.
     * @throws IllegalArgumentException in case the gym already exists.
     */
    public void createGym(GymDTO gym);
    
    /**
     * Updates the location of an existing gym.
     * @param gym Gym to be updated.
     * @param newLocaton new location of the gym.
     */
    public void updateGymLocation(GymDTO gym, String newLocaton);
    
    /**
     * Changes the type of an existing gym.
     * @param gym Gym to be updated.
     * @param newType New type of the gym.
     */
    public void changeGymType(GymDTO gym, PokemonType newType);
    
    /**
     * Changes the leader of an existing gym.
     * @param gym Gym to be updated.
     * @param newGymLeader New leader of the gym.
     */
    public void changeGymLeader(GymDTO gym, TrainerDTO newGymLeader);
    
    /**
     * Removes an existing gym.
     * @param gym Gym to be removed.
     */
    public void removeGym(GymDTO gym);
    
    /**
     * Finds the gym with given id.
     * @param id Id of the gym.
     * @return Gym with the given id, null in case such gym does not exists.
     */
    public GymDTO findGymById(Long id);
    
    /**
     * Returns a list of all gyms.
     * @return List of all gyms.
     */
    public List<GymDTO> getAllGyms();
    
    /**
     * Gets the leader of the gym.
     * @param gym Gym to find the leader.
     * @return The leader of the gym.
     */
    public TrainerDTO getGymLeader(GymDTO gym);

    /**
     * Returns a list of all gyms with given type.
     * @param type The type of gym.
     * @return List of all gyms with given type.
     */
    public List<GymDTO> findGymsByType(PokemonType type);
    
    /**
     * Returns gym with the given leader.
     * @param trainer The leader of the gym.
     * @return The gym with the given trainer, null in case no such gym exists.
     */
    public GymDTO findGymByLeader(TrainerDTO trainer);
    
}
