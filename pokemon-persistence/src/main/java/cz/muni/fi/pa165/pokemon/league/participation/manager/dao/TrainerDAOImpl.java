package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Implementation of Trainer Data Acces Object interface.
 * 
 * @author Tamás Rózsa 445653
 */
@Repository
public class TrainerDAOImpl implements TrainerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createTrainer(Trainer trainer) {
        em.persist(trainer);
    }

    @Override
    public Long getAdminCount() {
        
        return em.createQuery("SELECT count(t) FROM Trainer t where t.admin = true"
                ,Long.class).getSingleResult();
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        em.merge(trainer);
    }

    @Override
    public void deleteTrainer(Trainer trainer) {
        Trainer attached = em.merge(trainer);
        em.remove(attached);
    }

    @Override
    public Trainer findTrainerById(Long id) {
        return em.find(Trainer.class, id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
            return em.createQuery("SELECT t FROM Trainer t", Trainer.class).getResultList();
    }
    
}
