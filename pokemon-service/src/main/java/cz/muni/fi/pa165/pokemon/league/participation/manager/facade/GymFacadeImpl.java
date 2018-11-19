package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymLeaderDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeGymTypeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.UpdateGymLocationDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createGym(GymCreateDTO gym) {
        gymService.createGym(beanMappingService.mapTo(gym, Gym.class));
    }

    @Override
    public void updateGymLocation(UpdateGymLocationDTO gym) {
        gymService.updateGymLocation(beanMappingService.mapTo(gym, Gym.class), gym.getNewLocation());
    }

    @Override
    public void changeGymType(ChangeGymTypeDTO gym) {
        gymService.changeGymType(beanMappingService.mapTo(gym, Gym.class),
                trainerService.getTrainerWithId(gym.getTrainerId()), gym.getNewGymType());
    }

    @Override
    public void changeGymLeader(ChangeGymLeaderDTO gym) {
        gymService.changeGymLeader(beanMappingService.mapTo(gym, Gym.class), 
                trainerService.getTrainerWithId(gym.getNewGymLeaderID()));
    }

    @Override
    public void removeGym(Long gymId) {
        gymService.removeGym(beanMappingService.mapTo(gymService.findGymById(gymId), Gym.class));
    }

    @Override
    public GymDTO findGymById(Long id) {
        return beanMappingService.mapTo(gymService.findGymById(id), GymDTO.class);
    }

    @Override
    public List<GymDTO> getAllGyms() {
            return beanMappingService.mapTo(gymService.getAllGyms(), GymDTO.class);
    }

    @Override
    public TrainerDTO getGymLeader(Long gymId) {
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
    public GymDTO findGymByLeader(Long trainerId) {
        return beanMappingService.mapTo(
                gymService.getAllGyms().stream()
                        .filter((gym) -> gym.getGymLeader().getId().equals(trainerId)).findFirst().get(),
                GymDTO.class);
        
    }
}
