/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;

/**
 * Unit test to gym data access object.
 * 
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@RunWith(MockitoJUnitRunner.class)
public class GymDAOTest {
    
    @PersistenceContext
    private EntityManagerFactory emf;
    
    @Inject
    private GymDAO gymDao;
    
    @Mock
    private Gym gymInBrno;
    private Gym finalGym;
    
    
    @BeforeMethod
    public void setUpMethod() {
        
        gymInBrno.setLocation("Brno");
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(gymInBrno.getGymLeader());
        em.persist(finalGym.getGymLeader());
        em.persist(gymInBrno);
        em.persist(finalGym);
        em.getTransaction().commit();
    }
    
    @Test
    public void createNullGym() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.createGym(null));
    }
    
    @Test
    public void updateNullGym() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.updateGym(null));
    }
    
    @Test
    public void deleteNullGym() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.deleteGym(null));
    }
    
    @Test
    public void findGymWithNullID() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gymDao.findGymById(null));
    }
    
    @Test
    public void createGym() {
        Gym gym = mock(Gym.class);
        gymDao.createGym(gym);
        assertThat(emf.createEntityManager().find(Gym.class, gym)).isEqualToComparingFieldByField(gym);
    }
    
    @Test
    public void testMultipleCreation() {
        GymDAO dao = gymDao;
        EntityManagerFactory emf2 = emf;
        assertThat(gymInBrno.getId()).isNotNull();
        assertThat(emf.createEntityManager().find(Gym.class, gymInBrno)).isEqualToComparingFieldByField(gymInBrno);
        try{
            gymDao.createGym(gymInBrno);
            Assert.fail("Created the same gym twice.");
        }
        catch(Exception ex) {
        }
    }
    
    @Test
    public void findByIDExistingGym() {
        assertThat(gymDao.findGymById(gymInBrno.getId())).isEqualToComparingFieldByField(gymInBrno);
    }
    
    @Test
    public void findByIDNotExistingGym() {
        assertThat(gymDao.findGymById(Long.MIN_VALUE)).isNull();
    }
    
    @Test
    public void updateGym() {
        EntityManager em = emf.createEntityManager();
        
        Gym foundGym = em.find(Gym.class, gymInBrno.getId());
        assertThat(foundGym).isEqualToComparingFieldByField(gymInBrno);
        assertThat(em.find(Gym.class, finalGym)).isEqualToComparingFieldByField(finalGym);
        
        gymInBrno.setLocation("London");
        gymDao.updateGym(gymInBrno);
        assertThat(foundGym).isNotSameAs(gymInBrno);
        
        foundGym = em.find(Gym.class, gymInBrno.getId());
        assertThat(foundGym).isEqualToComparingFieldByField(gymInBrno);
        assertThat(em.find(Gym.class, finalGym)).isEqualToComparingFieldByField(finalGym);
    }
    
    @Test
    public void deleteGym() {
        EntityManager em = emf.createEntityManager();
        
        assertThat(em.find(Gym.class, gymInBrno.getId())).isEqualToComparingFieldByField(gymInBrno);
        assertThat(em.find(Gym.class, finalGym.getId())).isEqualToComparingFieldByField(finalGym);
        
        gymDao.deleteGym(gymInBrno);
        
        assertThat(em.find(Gym.class, gymInBrno.getId())).isNull();
        assertThat(em.find(Gym.class, finalGym.getId())).isEqualToComparingFieldByField(finalGym);
    }
    
    @Test
    public void getAllGyms() {
        assertThat(gymDao.getAllGyms()).usingFieldByFieldElementComparator().containsOnly(gymInBrno, finalGym);
    }
}
