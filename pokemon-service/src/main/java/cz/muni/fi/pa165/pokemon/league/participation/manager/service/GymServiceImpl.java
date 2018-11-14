package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.List;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Implementation of GymService.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymServiceImpl implements GymService {

    @Inject
    private GymDAO gymDAO;
    
    @Override
    public void createGym(Gym gym) {
        gymDAO.createGym(gym);
    }

    @Override
    public void updateGymLocation(Gym gym, String newLocaton) {
        gym.setLocation(newLocaton);
        gymDAO.updateGym(gym);
    }

    @Override
    public void changeGymType(Gym gym, Trainer trainer, PokemonType newType) {
        if (gym.getGymLeader().equals(trainer)) {
            gym.setType(newType);
            gymDAO.updateGym(gym);
        }
    }

    @Override
    public void changeGymLeader(Gym gym, Trainer newGymLeader) {
        gym.setGymLeader(newGymLeader);
        gymDAO.updateGym(gym);
    }

    @Override
    public void removeGym(Gym gym) {
        gymDAO.deleteGym(gym);
    }

    @Override
    public Gym findGymById(Long id) {
        return gymDAO.findGymById(id);
    }

    @Override
    public List<Gym> getAllGyms() {
        return gymDAO.getAllGyms();
    }

    @Override
    public Trainer getGymLeader(Gym gym) {
        return gymDAO.findGymById(gym.getId()).getGymLeader();
    }

    @Override
    public List<Gym> findGymsByType(PokemonType type) {
        return gymDAO.getAllGyms().stream().filter((gym) -> gym.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Gym findGymByLeader(Trainer trainer) {
        return gymDAO.getAllGyms().stream().filter((gym) -> gym.getGymLeader().equals(trainer)).findFirst().get();
    }
}
