package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymLeaderDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymTypeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymAndBadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.UpdateGymLocationDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;

/**
 * Implementation of gym facade interface.
 * 
 * @author Tamás Rózsa 445653
 */
@Service
@Transactional
public class GymFacadeImpl implements GymFacade {

    @Inject
    private GymService gymService;
    
    @Inject
    private TrainerService trainerService;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createGym(GymCreateDTO gym) throws EntityIsUsedException, NoSuchEntityException {
        Trainer leader = trainerService.getTrainerWithId(gym.getGymLeaderID());
        if (leader == null) {
            throw new NoSuchEntityException("The selected trainer doesn't exist");
        }
        Gym gymEntity = beanMappingService.mapTo(gym, Gym.class);
        gymEntity.setGymLeader(leader);
        gymService.createGym(gymEntity);
        return gymEntity.getId();
    }

    @Override
    public void updateGymLocation(UpdateGymLocationDTO gym) {
        gymService.updateGymLocation(beanMappingService.mapTo(findGymById(gym.getGymID()), Gym.class), gym.getNewLocation());
    }

    @Override
    public void changeGymType(ChangeGymTypeDTO gym) throws InsufficientRightsException {
        gymService.changeGymType(gymService.findGymById(gym.getId()),
                trainerService.getTrainerWithId(gym.getTrainerId()), gym.getType());
    }

    @Override
    public void changeGymLeader(ChangeGymLeaderDTO gym) throws EntityIsUsedException, NoSuchEntityException {
        Gym gymEntity = gymService.findGymById(gym.getId());
        Trainer trainer = trainerService.getTrainerWithId(gym.getGymLeader());
        if (gymEntity == null) {
            throw new NoSuchEntityException("No gym of such ID exists");
        }
        if (trainer == null) {
            throw new NoSuchEntityException("No trainer of such ID exists");
        }
        gymService.changeGymLeader(gymEntity, trainer);
    }

    @Override
    public void removeGym(@NotNull Long gymId) throws EntityIsUsedException {
        gymService.removeGym(beanMappingService.mapTo(findGymById(gymId), Gym.class));
    }

    @Override
    public GymDTO findGymById(@NotNull Long id) {
        return beanMappingService.mapTo(gymService.findGymById(id), GymDTO.class);
    }

    @Override
    public List<GymDTO> getAllGyms() {
            return beanMappingService.mapTo(gymService.getAllGyms(), GymDTO.class);
    }

    @Override
    public TrainerDTO getGymLeader(@NotNull Long gymId) {
        return beanMappingService.mapTo(gymService.getGymLeader(gymService.findGymById(gymId)), TrainerDTO.class);
    }

    @Override
    public List<GymDTO> findGymsByType(PokemonType type) {
        return beanMappingService.mapTo(
                gymService.getAllGyms().stream()
                        .filter((gym) -> gym.getType().equals(type)).collect(Collectors.toList()),
                GymDTO.class);
    }

    @Override
    public GymDTO findGymByLeader(@NotNull Long trainerId) {
        return beanMappingService.mapTo(
                gymService.getAllGyms().stream()
                        .filter((gym) -> gym.getGymLeader().getId().equals(trainerId)).findFirst().get(),
                GymDTO.class);
    }

    @Override
    public List<GymAndBadgeDTO> getAllGymsAndBadgesOfTrainer(Long trainerId)
            throws NoSuchEntityException {
        Trainer t = trainerService.getTrainerWithId(trainerId);
        if (t == null) {
            throw new NoSuchEntityException("No trainer of given ID exists");
        }
        return beanMappingService.mapTo(gymService.getAllGymsAndBadgesOfTrainer(t), GymAndBadgeDTO.class);
    }
}
