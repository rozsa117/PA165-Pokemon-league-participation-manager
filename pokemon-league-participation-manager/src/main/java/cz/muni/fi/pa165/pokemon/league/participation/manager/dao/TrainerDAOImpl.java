/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
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
    public void createTrainer(@Valid Trainer trainer) {
        em.persist(trainer);
    }

    @Override
    public void updateTrainer(@Valid Trainer trainer) {
        em.merge(trainer);
    }

    @Override
    public void deleteTrainer(@Valid Trainer trainer) {
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
