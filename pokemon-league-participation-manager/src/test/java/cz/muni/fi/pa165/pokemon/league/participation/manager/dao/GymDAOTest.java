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
import java.time.LocalDate;
import java.time.Month;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Unit test to gym data access object.
 * 
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@RunWith(SpringRunner.class)
public class GymDAOTest {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
        /*
    @PersistenceContext
    private EntityManager em;*/
    
    @Inject
    private GymDAO gymDao;
    @Inject
    private TrainerDAO trainerDAO;
    /*
    @Mock*/
    private Gym gymInBrno;
    private Gym finalGym;
    
    private Trainer trainer;
    private Trainer trainer2;
    
    public GymDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

        trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setSurname("Hash");
        trainer.setBorn(LocalDate.of(2000, Month.MARCH, 2));
        trainer.setPasswordHash("asd123");
        trainer.setUserName("Ashh");
        
        gymInBrno = new Gym();
        gymInBrno.setType(PokemonType.DRAGON);
        gymInBrno.setLocation("Brno");
        gymInBrno.setGymLeader(trainer);

        trainer2 = new Trainer();
        trainer2.setName("Hek");
        trainer2.setSurname("Lek");
        trainer2.setBorn(LocalDate.of(1998, Month.APRIL, 3));
        trainer2.setPasswordHash("qwe789");
        trainer.setUserName("Hekl");

        finalGym = new Gym();
        finalGym.setType(PokemonType.ICE);
        finalGym.setLocation("Praha");
        finalGym.setGymLeader(trainer2);

        trainerDAO.createTrainer(trainer);
        trainerDAO.createTrainer(trainer2);
        gymDao.createGym(gymInBrno);
        gymDao.createGym(finalGym);
        
        /*
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(trainer);
        em.persist(trainer2);
        em.persist(gymInBrno);
        em.persist(finalGym);
        em.getTransaction().commit()
                
        /*
        trainer1 = new Trainer();
        trainer1.setBorn(LocalDate.of(2000, Month.MARCH, 3));
        trainer1.setName("someone");
        trainer1.setPasswordHash("asd");
        trainer1.setSurname("something");
        trainer1.setUserName("somsome");
        trainer1.setAdmin(false);
        /*
        trainer1 = mock(Trainer.class);
        trainer2 = mock(Trainer.class);
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(trainer1);
       // em.persist(trainer2);
        em.getTransaction().commit();
        
        gymInBrno = new Gym();
        gymInBrno.setType(PokemonType.FIRE);
        gymInBrno.setLocation("Brno");
        gymInBrno.setGymLeader(trainer1);
        
        finalGym = new Gym();
        finalGym.setType(PokemonType.BUG);
        finalGym.setLocation("Praha");
        gymInBrno.setGymLeader(trainer1);
        
        em.getTransaction().begin();
        em.persist(gymInBrno);
        em.persist(finalGym);
        em.getTransaction().commit();*/
    }
    
    @After
    public void tearDown() {
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
        //GymDAO dao = gymDao;
        
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
