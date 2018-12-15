package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.DataAccessException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.DAOExceptionWrapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Inject
    private BadgeDAO badgeDAO;

    @Override
    public void createGym(Gym gym) throws EntityIsUsedException {
        Gym usedGym = this.findGymByLeader(gym.getGymLeader());
        if (usedGym != null) {
            throw new EntityIsUsedException("Trainer already used in gym " + usedGym.toString());
        }
        DAOExceptionWrapper.withoutResult(() -> gymDAO.createGym(gym), "Cannot create new gym " + gym.toString());
    }

    @Override
    public void updateGymLocation(Gym gym, String newLocation) {
        DAOExceptionWrapper.withoutResult(() -> {
            gym.setLocation(newLocation);
            gymDAO.updateGym(gym);
        }, "Cannot update location fot gym " + gym.toString());
    }

    @Override
    public void changeGymType(Gym gym, Trainer trainer, PokemonType newType)
            throws InsufficientRightsException {
        if (!gym.getGymLeader().equals(trainer)) {
            throw new InsufficientRightsException("Only Gym Leader may change Gym Type");
        }
        DAOExceptionWrapper.withoutResult(() -> {
            gym.setType(newType);
            gymDAO.updateGym(gym);
        }, "Cannot change gym type for gym " + gym.toString());
    }

    @Override
    public void changeGymLeader(Gym gym, Trainer newGymLeader) throws EntityIsUsedException {
        if (!newGymLeader.equals(gym.getGymLeader())) {
            Gym usedGym = this.findGymByLeader(newGymLeader);
            if (usedGym != null) {
                throw new EntityIsUsedException("Gym leader already used on Gym " + usedGym.toString());
            }
        }
        DAOExceptionWrapper.withoutResult(() -> {
            gym.setGymLeader(newGymLeader);
            gym.setType(null);
            gymDAO.updateGym(gym);
        }, "Cannot change gym leader for gym " + gym.toString());
    }

    @Override
    public void removeGym(Gym gym) throws EntityIsUsedException {
        if (!badgeDAO.findBadgesOfGym(gym).isEmpty()) {
            throw new EntityIsUsedException("Gym is used on badge(s)");
        }
        DAOExceptionWrapper.withoutResult(() -> gymDAO.deleteGym(gym), "Cannot remove the following gym " + gym.toString());
    }

    @Override
    public Gym findGymById(Long id) {
        return DAOExceptionWrapper.withResult(() -> gymDAO.findGymById(id), "Cannot find gym with id " + id);
    }

    @Override
    public List<Gym> getAllGyms() {
        return DAOExceptionWrapper.withResult(() -> gymDAO.getAllGyms(), "Cannot get all gyms.");
    }

    @Override
    public Trainer getGymLeader(Gym gym) {
        try {
            return gymDAO.findGymById(gym.getId()).getGymLeader();
        } catch (Exception ex) {
            throw new DataAccessException("Cannot get gym leader of the following gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public List<Gym> findGymsByType(PokemonType type) {
        try {
            return gymDAO.getAllGyms().stream().filter((gym) -> gym.getType().equals(type)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new DataAccessException("Cannot find gyms with type " + type.toString(), ex) {
            };
        }
    }

    @Override
    public Gym findGymByLeader(Trainer trainer) {
        try {
            return gymDAO.getAllGyms().stream().filter((gym) -> gym.getGymLeader().equals(trainer)).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        } catch (Exception ex) {
            throw new DataAccessException("Cannot fing the gym for the following trainer " + trainer.toString(), ex) {
            };
        }
    }

    @Override
    public List<GymAndBadge> getAllGymsAndBadgesOfTrainer(Trainer trainer) {
        return DAOExceptionWrapper.withResult(() -> gymDAO.getAllGymsAndBadgesOfTrainer(trainer), "getAllGymsAndBadgesOfTrainer failed");
    }
}
