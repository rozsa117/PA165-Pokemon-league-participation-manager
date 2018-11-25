package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.DataAccessException;
import java.util.List;
import javax.inject.Inject;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Implementation of GymService.
 * 
 * @author Tamás Rózsa 445653
 */
@Service
public class GymServiceImpl implements GymService {

    @Inject
    private GymDAO gymDAO;
    
    @Override
    public void createGym(Gym gym) {
        try {
            gymDAO.createGym(gym);
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot create new gym " + gym.toString(), ex);
        }
    }

    @Override
    public void updateGymLocation(Gym gym, String newLocaton) {
        try {
            gym.setLocation(newLocaton);
            gymDAO.updateGym(gym);
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot update location fot gym " + gym.toString(), ex);
        }
    }

    @Override
    public void changeGymType(Gym gym, Trainer trainer, PokemonType newType) {
        try {
            if (gym.getGymLeader().equals(trainer)) {
                gym.setType(newType);
                gymDAO.updateGym(gym);
            }
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot change gym type for gym " + gym.toString(), ex);
        }
    }

    @Override
    public void changeGymLeader(Gym gym, Trainer newGymLeader) {
        try {
            gym.setGymLeader(newGymLeader);
            gymDAO.updateGym(gym);
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot change gym leader for gym " + gym.toString(), ex);
        }
    }

    @Override
    public void removeGym(Gym gym) {
        try {
            gymDAO.deleteGym(gym);
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot remove the following gym " + gym.toString(), ex);
        }
    }

    @Override
    public Gym findGymById(Long id) {
        try {
            return gymDAO.findGymById(id);
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot find gym with id " + id, ex);
        }
    }

    @Override
    public List<Gym> getAllGyms() {
        try {
            return gymDAO.getAllGyms();
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot get all gyms.", ex);
        }
    }

    @Override
    public Trainer getGymLeader(Gym gym) {
        try {
            return gymDAO.findGymById(gym.getId()).getGymLeader();
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot get gym leader of the following gym " + gym.toString(), ex);
        }
    }

    @Override
    public List<Gym> findGymsByType(PokemonType type) {
        try {
            return gymDAO.getAllGyms().stream().filter((gym) -> gym.getType().equals(type)).collect(Collectors.toList());
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot find gyms with type " + type.toString(), ex);
        }
    }

    @Override
    public Gym findGymByLeader(Trainer trainer) {
        try {
            return gymDAO.getAllGyms().stream().filter((gym) -> gym.getGymLeader().equals(trainer)).findFirst().get();
        }
        catch(Exception ex) {
            throw new DataAccessException("Cannot fing the gym for the following trainer " + trainer.toString(), ex);
        }
    }
}
