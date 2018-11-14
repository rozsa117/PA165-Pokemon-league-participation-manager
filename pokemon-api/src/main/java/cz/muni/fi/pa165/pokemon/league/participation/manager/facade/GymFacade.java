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
     * @param gym DTO of gym to create.
     */
    public void createGym(GymCreateDTO gym);
    
    /**
     * Updates the location of an existing gym.
     * @param gym DTO of gym to be updated.
     * @param newLocaton new location of the gym.
     */
    public void updateGymLocation(UpdateGymLocationDTO gym);
    
    /**
     * Changes the type of an existing gym.
     * @param gym DTO of gym to be updated.
     * @param newType New type of the gym.
     */
    public void changeGymType(ChangeGymTypeDTO gym);
    
    /**
     * Changes the leader of an existing gym.
     * @param gym DTO of gym to be updated.
     * @param newGymLeader New leader of the gym.
     */
    public void changeGymLeader(ChangeGymLeaderDTO gym);
    
    /**
     * Removes an existing gym.
     * @param gym DTO of gym to be removed.
     */
    public void removeGym(Long gymId);
    
    /**
     * Finds the gym with given id.
     * @param id Id of the gym.
     * @return GymDTO with the given id, null in case such gym does not exists.
     */
    public GymDTO findGymById(Long id);
    
    /**
     * Returns a list of all gyms.
     * @return List of DTOs of all gyms.
     */
    public List<GymDTO> getAllGyms();
    
    /**
     * Gets the leader of the gym.
     * @param gym DTO of the gym to find the leader.
     * @return The DTO of the leader of the gym.
     */
    public TrainerDTO getGymLeader(Long gymId);

    /**
     * Returns a list of all gyms with given type.
     * @param type The type of gym.
     * @return List of all DTOs of gyms with given type.
     */
    public List<GymDTO> findGymsByType(PokemonType type);
    
    /**
     * Returns gym with the given leader.
     * @param trainer The leader of the gym.
     * @return The DTO of the gym with the given trainer, null in case no such gym exists.
     */
    public GymDTO findGymByLeader(Long trainerId);
    
}
