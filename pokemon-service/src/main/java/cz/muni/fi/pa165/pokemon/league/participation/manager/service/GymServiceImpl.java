package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.inject.Inject;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;

/**
 * Implementation of GymService.
 *
 * @author Tamás Rózsa 445653
 */
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
        try {
            gymDAO.createGym(gym);
        } catch (Exception ex) {
            throw new DataAccessException("Cannot create new gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public void updateGymLocation(Gym gym, String newLocation) {
        try {
            gym.setLocation(newLocation);
            gymDAO.updateGym(gym);
        } catch (Exception ex) {
            throw new DataAccessException("Cannot update location fot gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public boolean changeGymType(Gym gym, Trainer trainer, PokemonType newType) {
        try {
            if (gym.getGymLeader().equals(trainer)) {
                gym.setType(newType);
                gymDAO.updateGym(gym);
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new DataAccessException("Cannot change gym type for gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public void changeGymLeader(Gym gym, Trainer newGymLeader) throws EntityIsUsedException {
        if (!newGymLeader.equals(gym.getGymLeader())) {
            Gym usedGym = this.findGymByLeader(newGymLeader);
            if (usedGym != null) {
                throw new EntityIsUsedException("Gymleader already use on Gym " + usedGym.toString());
            }
        }
        try {
            gym.setGymLeader(newGymLeader);
            gymDAO.updateGym(gym);
        } catch (Exception ex) {
            throw new DataAccessException("Cannot change gym leader for gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public void removeGym(Gym gym) throws EntityIsUsedException {

        if (badgeDAO.findBadgesOfGym(gym).size() != 0) {
            throw new EntityIsUsedException("Gym is used on badge(s)");
        }

        try {
            gymDAO.deleteGym(gym);
        } catch (Exception ex) {
            throw new DataAccessException("Cannot remove the following gym " + gym.toString(), ex) {
            };
        }
    }

    @Override
    public Gym findGymById(Long id) {
        try {
            return gymDAO.findGymById(id);
        } catch (Exception ex) {
            throw new DataAccessException("Cannot find gym with id " + id, ex) {
            };
        }
    }

    @Override
    public List<Gym> getAllGyms() {
        try {
            return gymDAO.getAllGyms();
        } catch (Exception ex) {
            throw new DataAccessException("Cannot get all gyms.", ex) {
            };
        }
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
}
