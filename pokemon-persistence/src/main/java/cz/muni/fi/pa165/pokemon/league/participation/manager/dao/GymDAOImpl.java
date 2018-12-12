package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implementation of GymDAO using JPA/Hibernate and Spring stereotypes.
 *
 * @author Tibor Zauko 433531
 */
@Repository
public class GymDAOImpl implements GymDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createGym(Gym gym) {
        em.persist(gym);
    }

    @Override
    public void updateGym(Gym gym) {
        em.merge(gym);
    }

    @Override
    public void deleteGym(Gym gym) {
        em.remove(em.contains(gym) ? gym : em.merge(gym));
    }

    @Override
    public Gym findGymById(Long id) {
        return em.find(Gym.class, id);
    }

    @Override
    public List<Gym> getAllGyms() {
        return em.createQuery("select g from Gym g", Gym.class).getResultList();
    }

    @Override
    public List<GymAndBadge> getAllGymsAndBadgesOfTrainer(Trainer trainer) {
        return em.createQuery("SELECT NEW cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge(g,b) FROM Gym g LEFT JOIN g.badges b ON b.trainer = :trainer" , GymAndBadge.class)
                .setParameter("trainer", trainer)
                .getResultList();
    }

}
