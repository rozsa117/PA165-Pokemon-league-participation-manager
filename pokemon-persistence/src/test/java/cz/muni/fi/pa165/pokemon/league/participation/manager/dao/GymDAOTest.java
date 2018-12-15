package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge;
import java.time.LocalDate;
import java.time.Month;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private GymDAO gymDao;
    @Inject
    private TrainerDAO trainerDAO;

    private Gym gymInBrno;
    private Gym finalGym;
    private Trainer trainerAsh;
    private Trainer trainerBrock;
    private Trainer trainerRoxanne;
    private Badge ashBadgeFromBrno;
        
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
                .born(LocalDate.of(1995, Month.JANUARY, 19))
                .isAdmin(false)
                .name("Brock")
                .surname("Boulder")
                .userName("onixDude")
                .passwordHash("nohash")
                .build();
        
        trainerRoxanne = new TrainerBuilder()
                .born(LocalDate.of(2000, Month.MARCH, 7))
                .isAdmin(false)
                .name("Roxanne")
                .surname("Briney")
                .userName("roxnose")
                .passwordHash("funny")
                .build();
        
        gymInBrno = new GymBuilder()
                .type(PokemonType.DRAGON)
                .location("Brno")
                .gymLeader(trainerBrock)
                .build();
        
        finalGym = new GymBuilder()
                .type(PokemonType.ICE)
                .location("Rustboro City")
                .gymLeader(trainerRoxanne)
                .build();
        
        ashBadgeFromBrno = new BadgeBuilder()
                .date(LocalDate.now())
                .gym(gymInBrno)
                .trainer(trainerAsh)
                .status(ChallengeStatus.WON)
                .build();
        
        trainerDAO.createTrainer(trainerAsh);
        trainerDAO.createTrainer(trainerBrock);
        trainerDAO.createTrainer(trainerRoxanne);
        gymDao.createGym(gymInBrno);
        gymDao.createGym(finalGym);
        em.persist(ashBadgeFromBrno);
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
        Gym gym = new GymBuilder()
                .type(PokemonType.FIRE)
                .gymLeader(trainerAsh)
                .location("Pallet Town")
                .build();
        gymDao.createGym(gym);
        assertThat(gymDao.findGymById(gym.getId())).isEqualToComparingFieldByField(gym);
        assertThat(gymDao.getAllGyms())
                .isNotNull()
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(gym, gymInBrno, finalGym);
    }
    
    @Test(expected = PersistenceException.class)
    public void testCreateWithSetId() {
        Gym gym = new GymBuilder()
                .id(10L)
                .gymLeader(trainerAsh)
                .location("Pallet Town")
                .type(PokemonType.FIRE)
                .build();
        gymDao.createGym(gym);
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
        Gym gym = gymDao.findGymById(gymInBrno.getId());
        assertThat(gym).isEqualToComparingFieldByField(gymInBrno);
        assertThat(gymDao.findGymById(finalGym.getId())).isEqualToComparingFieldByField(finalGym);
        
        em.detach(gym);
        gym.setLocation("London");
        gymDao.updateGym(gym);
        
        assertThat(gymDao.findGymById(gymInBrno.getId())).isEqualToComparingFieldByField(gym);
    }
    
    @Test
    public void deleteGym() {
        assertThat(gymDao.findGymById(gymInBrno.getId())).isEqualToComparingFieldByField(gymInBrno);
        assertThat(gymDao.findGymById(finalGym.getId())).isEqualToComparingFieldByField(finalGym);
        
        gymDao.deleteGym(gymInBrno);
        
        assertThat(gymDao.findGymById(gymInBrno.getId())).isNull();
        assertThat(gymDao.findGymById(finalGym.getId())).isEqualToComparingFieldByField(finalGym);
    }
    
    @Test
    public void getAllGyms() {
        assertThat(gymDao.getAllGyms()).usingFieldByFieldElementComparator().containsOnly(gymInBrno, finalGym);
    }
    
    @Test
    public void getAllGymsAndBadgesOfTrainer() {
        assertThat(gymDao.getAllGymsAndBadgesOfTrainer(trainerAsh))
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(new GymAndBadge(gymInBrno, ashBadgeFromBrno), new GymAndBadge(finalGym, null));
    }
}
