package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.TrainerDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for trainer service
 *
 * @author Tibor Zauko 433531
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDao;

    @Mock
    private GymDAO gymDao;

    @Inject
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Trainer admin;
    private static final Long ADMIN_ID = 1L;
    private Trainer leader;
    private static final Long LEADER_ID = 2L;
    private Trainer trainer;
    private static final Long TRAINER_ID = 3L;
    private Trainer exceptionalTrainer;
    private static final Long EXCEPTIONAL_TRAINER_ID = 4L;
    private Gym gym;

    private static final PersistenceException PE =
            new PersistenceException("This should be wrapped in DataAccessException");
    private static final String PASSWD = "dontPWNme";

    public TrainerServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        admin = new TrainerBuilder()
                .born(LocalDate.of(1990, Month.MARCH, 5))
                .isAdmin(true)
                .name("Mister")
                .surname("Goodshow")
                .userName("adminforever")
                .passwordHash("placeholder")
                .id(ADMIN_ID)
                .build();
        leader = new TrainerBuilder()
                .born(LocalDate.of(1994, Month.APRIL, 20))
                .isAdmin(false)
                .name("Brock")
                .surname("Edney")
                .userName("theRock")
                .passwordHash("placeholder")
                .id(LEADER_ID)
                .build();
        trainer = new TrainerBuilder()
                .born(LocalDate.of(1997, Month.OCTOBER, 27))
                .isAdmin(false)
                .name("Ash")
                .surname("Ketchum")
                .userName("legend")
                .passwordHash("placeholder")
                .id(TRAINER_ID)
                .build();
        exceptionalTrainer = new TrainerBuilder()
                .born(LocalDate.now())
                .isAdmin(true) /* This trainer is exceptional :D */
                .name("Cheating")
                .surname("Cheat")
                .userName("legitimatelyBred")
                .passwordHash("placeholder")
                .id(EXCEPTIONAL_TRAINER_ID)
                .build();
        gym = new GymBuilder()
                .gymLeader(leader)
                .location("Pewter City")
                .type(PokemonType.ROCK)
                .id(1L)
                .build();
        when(gymDao.getAllGyms()).thenReturn(Arrays.asList(gym));
        when(gymDao.findGymById(gym.getId())).thenReturn(gym);
        when(trainerDao.findTrainerById(ADMIN_ID)).thenReturn(admin);
        when(trainerDao.findTrainerById(LEADER_ID)).thenReturn(leader);
        when(trainerDao.findTrainerById(TRAINER_ID)).thenReturn(trainer);
        when(trainerDao.findTrainerById(EXCEPTIONAL_TRAINER_ID)).thenThrow(PE);
        when(trainerDao.getAllTrainers()).thenReturn(Arrays.asList(admin, leader, trainer));
        when(trainerDao.getAdminCount()).thenReturn(1L);
        doThrow(PE).when(trainerDao).updateTrainer(exceptionalTrainer);
        doThrow(PE).when(trainerDao).deleteTrainer(exceptionalTrainer);
        when(trainerDao.findTrainerByUsername(trainer.getUserName()))
                .thenReturn(trainer);
        doThrow(PE).when(trainerDao).findTrainerByUsername(exceptionalTrainer.getUserName());
        
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateTrainer() throws NoAdministratorException {
        // Set up create specific environment:
        admin.setId(null);
        trainer.setId(null);
        when(trainerDao.getAdminCount()).thenReturn(0l);
        doAnswer(invocation -> {
            when(trainerDao.getAdminCount()).thenReturn(1l);
            return null;
        }).when(trainerDao).createTrainer(admin);

        assertThatExceptionOfType(NoAdministratorException.class)
                .isThrownBy(() -> trainerService.createTrainer(trainer, PASSWD));
        assertThat(trainerService.createTrainer(admin, PASSWD))
                .matches((t) -> t.getPasswordHash() != null && !PASSWD.equals(t.getPasswordHash()));
        verify(trainerDao, atLeastOnce()).createTrainer(admin);
        assertThat(trainerService.createTrainer(trainer, PASSWD))
                .matches((t) -> !PASSWD.equals(t.getPasswordHash()));
        verify(trainerDao, atLeastOnce()).createTrainer(trainer);
    }

    @Test
    public void testCreateTrainerWithNoAdministrator() throws NoAdministratorException {
        // Set up create specific environment:
        trainer.setId(null);
        when(trainerDao.getAdminCount()).thenReturn(0l);

        assertThatExceptionOfType(NoAdministratorException.class)
                .isThrownBy(() -> trainerService.createTrainer(trainer, PASSWD));
    }
    
    @Test
    public void testCreateTrainerWithDAOThrownException() {
        exceptionalTrainer.setId(null);
        doThrow(PE).when(trainerDao).createTrainer(exceptionalTrainer);
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.createTrainer(exceptionalTrainer, PASSWD));
    }

    @Test
    public void testRenameTrainer() {
        trainerService.renameTrainer(trainer, "Hash", "Ketchum");
        verify(trainerDao, atLeastOnce()).updateTrainer(trainer);
    }
    
    @Test
    public void testRenameTrainerWithDAOThrownException() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.renameTrainer(exceptionalTrainer, "No Cheats", "I Promise"));
    }

    @Test
    public void testGetAllTrainers() {
        assertThat(trainerService.getAllTrainers())
                .containsExactlyInAnyOrderElementsOf(trainerDao.getAllTrainers());
    }
    
    @Test
    public void testGetAllTrainersWithDAOThrownException() {
        when(trainerDao.getAllTrainers()).thenThrow(PE);
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.getAllTrainers());
    }

    @Test
    public void testGetTrainerWithId() {
        trainerDao.getAllTrainers().forEach((t) -> {
            assertThat(trainerService.getTrainerWithId(t.getId()))
                    .isEqualTo(t);
        });
    }
    
    @Test
    public void testGetAllTrainerWithIdWithDAOThrownException() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.getTrainerWithId(exceptionalTrainer.getId()));
    }

    @Test
    public void testAuthenticate() throws NoAdministratorException {
        // "Create" the trainer so that it's password hash is computed:
        trainerService.createTrainer(trainer, PASSWD);
        
        assertThat(trainerService.authenticate(trainer, PASSWD + "orNot"))
                .isFalse();
        assertThat(trainerService.authenticate(trainer, PASSWD))
                .isTrue();
    }

    @Test
    public void testIsGymLeader() {
        assertThat(trainerService.isGymLeader(trainer))
                .isFalse();
        assertThat(trainerService.isGymLeader(leader))
                .isTrue();
    }

    @Test
    public void testSetAdmin() throws NoAdministratorException {
        // Set up mock to check whether setAdmin updates admin when updating:
        doAnswer(invocation -> {
            assertThat(trainer.isAdmin())
                    .isTrue();
            return null;
        }).when(trainerDao).updateTrainer(trainer);
        trainerService.setAdmin(trainer, true);

        // Set up mock to check whether setAdmin updates admin when updating:
        doAnswer(invocation -> {
            assertThat(trainer.isAdmin())
                    .isFalse();
            return null;
        }).when(trainerDao).updateTrainer(trainer);
        when(trainerDao.getAdminCount()).thenReturn(2l);
        trainerService.setAdmin(trainer, false);
        verify(trainerDao, atLeast(2)).updateTrainer(trainer);
    }

    @Test
    public void testSetAdminWithNoAdministrator() throws NoAdministratorException {
        assertThatExceptionOfType(NoAdministratorException.class)
                .isThrownBy(() -> trainerService.setAdmin(admin, false));
    }

    @Test
    public void testSetAdminWithDAOThrownException() throws NoAdministratorException {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.setAdmin(exceptionalTrainer, true));
    }

    @Test
    public void testChangePassword() throws NoAdministratorException {
        // Make sure the password hash is computed:
        trainerService.createTrainer(trainer, PASSWD);

        String newPasswd = "new" + PASSWD;
        final String oldPasswdHash = trainer.getPasswordHash();

        doAnswer(invocation -> {
            assertThat(oldPasswdHash)
                    .isNotEqualTo(trainer.getPasswordHash());
            return null;
        }).when(trainerDao).updateTrainer(trainer);
        assertThat(trainerService.changePassword(trainer, PASSWD, newPasswd))
                .isTrue();
        verify(trainerDao, atLeastOnce()).updateTrainer(trainer);
        assertThat(trainerService.changePassword(trainer, PASSWD, newPasswd))
                .isFalse();
    }

    @Test
    public void testChangePasswordWithDAOThrownException() throws NoAdministratorException {
        // Make sure the password hash is computed:
        trainerService.createTrainer(exceptionalTrainer, PASSWD);
        
        String newPasswd = "new" + PASSWD;
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.changePassword(exceptionalTrainer, PASSWD, newPasswd));
    }

    @Test
    public void testFindTrainerWithUsername() {
            assertThat(trainerService.findTrainerByUsername(trainer.getUserName()))
                    .isEqualTo(trainer);
    }
    
    @Test
    public void testFindTainerWithUsernameWithDAOThrownException() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> trainerService.findTrainerByUsername(exceptionalTrainer.getUserName()));
    }

}
