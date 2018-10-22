/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
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
 * Unit test to badge data access object.
 *
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@RunWith(SpringRunner.class)
public class BadgeDAOTest {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Inject
    private GymDAO gymDao;
    
    @Inject
    private TrainerDAO trainerDao;
    
    @Inject
    private BadgeDAO badgeDao;
    
    private Badge todaysBadge;
    private Badge finalBadge;
    
    private Trainer trainerAsh;
    private Trainer trainerBrock;
    
    private Gym gymInBrno;
    private Gym finalGym;
    
    public BadgeDAOTest() {
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
        
        trainerBrock = new TrainerBuilder()
                .born(LocalDate.of(1995, Month.JANUARY, 18))
                .isAdmin(false)
                .name("Brock")
                .surname("Sleepyeye")
                .userName("rock")
                .passwordHash("pswdb")
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

        todaysBadge = new BadgeBuilder()
                .date(LocalDate.now())
                .trainer(trainerBrock)
                .gym(gymInBrno)
                .status(ChallengeStatus.WON)
                .build();
        todaysBadge = mock(Badge.class);
        todaysBadge = new BadgeBuilder()
                .date(LocalDate.of(20015, Month.MARCH, 7))
                .trainer(trainerBrock)
                .gym(finalGym)
                .status(ChallengeStatus.LOST)
                .build();
        
        trainerDao.createTrainer(trainerAsh);
        trainerDao.createTrainer(trainerBrock);
        gymDao.createGym(gymInBrno);
        gymDao.createGym(finalGym);
        badgeDao.createBadge(todaysBadge);
        badgeDao.createBadge(finalBadge);
    }
    
    @After
    public void tearDown() {
    }
 
    @Test
    public void createNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.createBadge(null));
    }
    
    @Test
    public void updateNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.updateBadge(null));
    }

    @Test
    public void deleteNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.deleteBadge(null));
    }
     
    @Test
    public void findNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.findBadgeById(null));
    }
    
    @Test
    public void createBadge() {
        EntityManager em = emf.createEntityManager();
        Badge badge = mock(Badge.class);
        badgeDao.createBadge(badge);
        assertThat(em.find(Badge.class, badge)).isEqualToComparingFieldByField(badge);
    }
    
    @Test
    public void testMultipleCreation() {
        EntityManager em = emf.createEntityManager();
        assertThat(todaysBadge.getId()).isNotNull();
        assertThat(em.find(Badge.class, todaysBadge)).isEqualToComparingFieldByField(todaysBadge);
        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> badgeDao.createBadge(finalBadge));
    }
    
    @Test
    public void findByIdExistingBadge() {
        assertThat(badgeDao.findBadgeById(todaysBadge.getId())).isEqualToComparingFieldByField(todaysBadge);
    }
    
    @Test
    public void findByIDNotExistingBadge() {
        assertThat(badgeDao.findBadgeById(Long.MIN_VALUE)).isNull();
    }
    
    @Test
    public void updateBadge() {
        EntityManager em = emf.createEntityManager();
        Badge badge = em.find(Badge.class, todaysBadge);
        assertThat(badge).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    
        badge.setDate(LocalDate.MIN);
        badgeDao.updateBadge(badge);
        assertThat(badge).isNotSameAs(todaysBadge);
        
        badge = em.find(Badge.class, todaysBadge);
        assertThat(badge).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    }
    
    @Test
    public void deleteBadge() {
        EntityManager em = emf.createEntityManager();
        assertThat(em.find(Badge.class, todaysBadge)).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
        
        badgeDao.deleteBadge(todaysBadge);
        
        assertThat(em.find(Badge.class, todaysBadge)).isNull();
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    }
    
    @Test
    public void getAllBadges() {
        assertThat(badgeDao.getAllBadges()).usingFieldByFieldElementComparator().containsOnly(todaysBadge, finalBadge);
    }
}
