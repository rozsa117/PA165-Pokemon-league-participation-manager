/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.time.LocalDate;
import java.time.Month;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    
    @Inject
    private GymDAO gymDao;
    @Inject
    private TrainerDAO trainerDAO;

    private Gym gymInBrno;
    private Gym finalGym;
    private Trainer trainerAsh;
    
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
        trainerAsh = new TrainerBuilder()
                .born(LocalDate.of(2000, Month.APRIL, 8))
                .isAdmin(false)
                .name("Ash")
                .surname("Ketchum")
                .userName("ketchup")
                .passwordHash("pswda")
                .build();
        
        gymInBrno = new GymBuilder()
                .type(PokemonType.DRAGON)
                .location("Brno")
                .gymLeader(trainerAsh)
                .build();
        
        finalGym = new GymBuilder()
                .type(PokemonType.ICE)
                .location("Praha")
                .gymLeader(trainerAsh)
                .build();
        
        trainerDAO.createTrainer(trainerAsh);
        gymDao.createGym(gymInBrno);
        gymDao.createGym(finalGym);
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
        assertThat(gymInBrno.getId()).isNotNull();
        assertThat(emf.createEntityManager().find(Gym.class, gymInBrno)).isEqualToComparingFieldByField(gymInBrno);
        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> gymDao.createGym(gymInBrno));
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
