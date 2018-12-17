package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymLeaderDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymTypeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymAndBadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.UpdateGymLocationDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Interface for gym facade.
 * 
 * @author Tamás Rózsa 445653
 */
public interface GymFacade {
    
    /**
     * Create a new Gym.
     * 
     * @param gym DTO of gym to create.
     * @throws EntityIsUsedException in case the gym already exists.
     * @throws NoSuchEntityException when selected trainer doesn't exist.
     */
    public Long createGym(@Valid GymCreateDTO gym) throws EntityIsUsedException, NoSuchEntityException;
    
    /**
     * Updates the location of an existing gym.
     * 
     * @param gym DTO of gym to be updated.
     */
    public void updateGymLocation(@Valid UpdateGymLocationDTO gym);
    
    /**
     * Changes the type of an existing gym.
     * @param gym DTO of gym to be updated.
     * @throws InsufficientRightsException when someone without rights tries to change type
     */
    public void changeGymType(@Valid ChangeGymTypeDTO gym) throws InsufficientRightsException;
    
    /**
     * Changes the leader of an existing gym.
     * @param gym DTO of gym to be updated.
     * @throws EntityIsUsedException when trainer is leader in another gym.
     * @throws NoSuchEntityException when given gym or it's selected leader doesn't exist.
     */
    public void changeGymLeader(@Valid ChangeGymLeaderDTO gym) throws EntityIsUsedException, NoSuchEntityException;
    
    /**
     * Removes an existing gym.
     * @param gymId Id of the gym to be removed.
     * @throws EntityIsUsedException when deleting the entity is not possible because it is referenced elsewhere.
     */
    public void removeGym(@NotNull Long gymId) throws EntityIsUsedException;
    
    /**
     * Finds the gym with given id.
     * 
     * @param id Id of the gym.
     * @return GymDTO with the given id, null in case such gym does not exists.
     */
    public GymDTO findGymById(@NotNull Long id);
    
    /**
     * Returns a list of all gyms.
     * 
     * @return List of DTOs of all gyms.
     */
    public List<GymDTO> getAllGyms();
    
    /**
     * Gets the leader of the gym.
     * 
     * @param gymId Id of the gym to find the trainer.
     * @return The DTO of the leader of the gym.
     */
    public TrainerDTO getGymLeader(@NotNull Long gymId);

    /**
     * Returns a list of all gyms with given type.
     * 
     * @param type The type of gym.
     * @return List of all DTOs of gyms with given type.
     */
    public List<GymDTO> findGymsByType(PokemonType type);
    
    /**
     * Returns gym with the given leader.
     * 
     * @param trainerId The id of leader of the gym.
     * @return The DTO of the gym with the given trainer, null in case no such gym exists.
     */
    public GymDTO findGymByLeader(@NotNull Long trainerId);
    

    /**
     * Returns a list containing all gyms, and where applicable a badge of the trainer from that gym.
     * 
     * For gyms from which the trainer doesn't have a badge, null is set as badge.
     *
     * @param trainerId ID of trainer whose badges shall be fetched.
     * @return List of gyms and badges.
     * @throws NoSuchEntityException when given trainer doesn't exist.
     */
    List<GymAndBadgeDTO> getAllGymsAndBadgesOfTrainer(Long trainerId)
            throws NoSuchEntityException;
}
