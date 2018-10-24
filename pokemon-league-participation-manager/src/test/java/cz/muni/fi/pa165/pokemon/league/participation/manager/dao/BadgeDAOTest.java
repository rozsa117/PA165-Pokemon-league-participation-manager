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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    
    @PersistenceContext
    private EntityManager em;
    
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
    private Trainer trainerWallace;
    private Trainer trainerWattson;
    
    private Gym gymInBrno;
    private Gym sootopolisGym;
    private Gym mauvilleGym;
    
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
        
        trainerWallace = new TrainerBuilder()
                .born(LocalDate.of(2001, Month.DECEMBER, 23))
                .isAdmin(false)
                .name("Wallace")
                .surname("Champion")
                .userName("wheresWally")
                .passwordHash("notHashed")
                .build();
        
        trainerWattson = new TrainerBuilder()
                .born(LocalDate.of(1969, Month.JULY, 20))
                .isAdmin(false)
                .name("Wattson")
                .surname("Watt")
                .userName("manELectric")
                .passwordHash("biglaughs")
                .build();
        
        gymInBrno = new GymBuilder()
                .type(PokemonType.ROCK)
                .location("Brno")
                .gymLeader(trainerBrock)
                .build();
        
        sootopolisGym = new GymBuilder()
                .type(PokemonType.WATER)
                .location("Sootopolis City")
                .gymLeader(trainerWallace)
                .build();
        
        mauvilleGym = new GymBuilder()
                .type(PokemonType.ELECTRIC)
                .location("Mauville City")
                .gymLeader(trainerWattson)
                .build();

        todaysBadge = new BadgeBuilder()
                .date(LocalDate.now())
                .trainer(trainerAsh)
                .gym(gymInBrno)
                .status(ChallengeStatus.WON)
                .build();
        
        finalBadge = new BadgeBuilder()
                .date(LocalDate.of(2015, Month.MARCH, 7))
                .trainer(trainerAsh)
                .gym(sootopolisGym)
                .status(ChallengeStatus.LOST)
                .build();
        
        trainerDao.createTrainer(trainerAsh);
        trainerDao.createTrainer(trainerBrock);
        trainerDao.createTrainer(trainerWallace);
        trainerDao.createTrainer(trainerWattson);
        gymDao.createGym(gymInBrno);
        gymDao.createGym(sootopolisGym);
        gymDao.createGym(mauvilleGym);
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
        Badge badge = new BadgeBuilder()
                .date(LocalDate.now())
                .trainer(trainerAsh)
                .gym(mauvilleGym)
                .status(ChallengeStatus.WAITING_TO_ACCEPT)
                .build();
        badgeDao.createBadge(badge);
        assertThat(badgeDao.findBadgeById(badge.getId())).isEqualToComparingFieldByField(badge);
    }
    
    @Test(expected = PersistenceException.class)
    public void testCreateWithSetId() {
        Badge badge = new BadgeBuilder()
                .id(10L)
                .gym(sootopolisGym)
                .trainer(trainerAsh)
                .date(LocalDate.now().minusYears(2))
                .build();
        badgeDao.createBadge(badge);
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
        Badge badge = badgeDao.findBadgeById(todaysBadge.getId());
        assertThat(badge).isEqualToComparingFieldByField(todaysBadge);
        assertThat(badgeDao.findBadgeById(finalBadge.getId())).isEqualToComparingFieldByField(finalBadge);
        em.detach(badge);
        badge.setDate(badge.getDate().minusDays(14));
        
        badgeDao.updateBadge(badge);
                
        badge = badgeDao.findBadgeById(todaysBadge.getId());
        assertThat(badgeDao.findBadgeById(todaysBadge.getId())).isEqualToComparingFieldByField(badge);
    }
    
    @Test
    public void deleteBadge() {
        assertThat(badgeDao.findBadgeById(todaysBadge.getId())).isEqualToComparingFieldByField(todaysBadge);
        assertThat(badgeDao.findBadgeById(finalBadge.getId())).isEqualToComparingFieldByField(finalBadge);
        
        badgeDao.deleteBadge(todaysBadge);
        
        assertThat(badgeDao.findBadgeById(todaysBadge.getId())).isNull();
        assertThat(badgeDao.findBadgeById(finalBadge.getId())).isEqualToComparingFieldByField(finalBadge);
    }
    
    @Test
    public void getAllBadges() {
        assertThat(badgeDao.getAllBadges()).usingFieldByFieldElementComparator().containsOnly(todaysBadge, finalBadge);
    }
}
