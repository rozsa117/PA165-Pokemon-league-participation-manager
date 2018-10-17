/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

/**
 * Unit test to gym data access object.
 * 
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@Transactional
public class GymDAOTest {
    
    @PersistenceContext
    private EntityManagerFactory emf;
    
    @Inject
    private GymDAO gymDao;
    
    private Gym gym;
    private Gym mockedGym;
    
    private Trainer mockedTrainer;
    
    @BeforeMethod
    public void setUpMethod() {
        mockedTrainer = mock(Trainer.class);
        
        gym = new Gym();
        gym.setGymLeader(mockedTrainer);
        gym.setLocation("Brno");
        gym.setType(PokemonType.FIRE);
        
        mockedGym = mock(Gym.class);
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(mockedTrainer);
        em.persist(gym);
        em.persist(mockedGym);
        em.getTransaction().commit();
    }
    
    @Test
    public void createNullGym() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.createGym(null));
    }
    
    @Test
    public void updateNullGym() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.updateGym(null));
    }
    
    @Test
    public void deleteNullGym() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.deleteGym(null));
    }
    
    @Test
    public void findGymWithNullID() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.findGymById(null));
    }
    
    @Test
    public void testMultipleCreation() {
        assertThat(gym.getId()).isNotNull();
        assertThat(emf.createEntityManager().find(Gym.class, gym)).isEqualToComparingFieldByField(gym);
        try{
            gymDao.createGym(gym);
            Assert.fail("Created the same gym twice.");
        }
        catch(Exception ex) {
        }
    }
    
    @Test
    public void findByIDExistingGym() {
        assertThat(gymDao.findGymById(gym.getId())).isEqualToComparingFieldByField(gym);
    }
    
    @Test
    public void findByIDNotExistingGym() {
        assertThat(gymDao.findGymById(Long.MAX_VALUE)).isNull();
    }
    
    @Test
    public void updateGym() {
        EntityManager em = emf.createEntityManager();
        
        Gym foundGym = em.find(Gym.class, gym.getId());
        assertThat(foundGym).isEqualToComparingFieldByField(gym);
        
        gym.setLocation("London");
        gymDao.updateGym(gym);
        assertThat(foundGym).isNotSameAs(gym);
        
        foundGym = em.find(Gym.class, gym.getId());
        assertThat(foundGym).isEqualToComparingFieldByField(gym);
    }
    
    @Test
    public void deleteGym() {
        EntityManager em = emf.createEntityManager();
        
        assertThat(em.find(Gym.class, gym.getId())).isEqualToComparingFieldByField(gym);
        assertThat(em.find(Gym.class, mockedGym.getId())).isEqualToComparingFieldByField(mockedGym);
        
        gymDao.deleteGym(gym);
        
        assertThat(em.find(Gym.class, gym.getId())).isNull();
        assertThat(em.find(Gym.class, mockedGym.getId())).isEqualToComparingFieldByField(mockedGym);
    }
    
    @Test
    public void getAllGyms() {
        assertThat(gymDao.getAllGyms()).usingFieldByFieldElementComparator().containsOnly(gym, mockedGym);
    }
}
