package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit tests to Trainer data access object
 *
 * @author Michal Mokros 456442
 */
@Transactional
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@RunWith(SpringRunner.class)
public class TrainerDAOTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private TrainerDAO trainerDAO;

    private Trainer ashTrainer;
    private Trainer brockTrainer;

    @Before
    public void setUp() {
        ashTrainer = new TrainerBuilder()
                .born(LocalDate.of(1997, 8, 13))
                .isAdmin(false)
                .name("Ash")
                .surname("Ketchup")
                .userName("Ash1997")
                .passwordHash("ash123")
                .build();

        brockTrainer = new TrainerBuilder()
                .born(LocalDate.of(1996, 1, 12))
                .isAdmin(false)
                .name("Brock")
                .surname("Sleepyeye")
                .userName("BrockRock")
                .passwordHash("brockpswd")
                .build();
    }

    @Test
    public void createNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.createTrainer(null));
    }

    @Test
    public void createTrainerTest() {
        trainerDAO.createTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId()))
                .isEqualToComparingFieldByField(ashTrainer);
    }

    @Test
    public void updateNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.updateTrainer(null));
    }

    @Test
    public void updateTrainerTest() {
        trainerDAO.createTrainer(ashTrainer);
        em.detach(ashTrainer);
        ashTrainer.setName("Ashito");
        trainerDAO.updateTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId()))
                .isEqualToComparingFieldByField(ashTrainer);
    }

    @Test
    public void deleteNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.deleteTrainer(null));
    }

    @Test
    public void deleteTrainerTest() {
        trainerDAO.createTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId()))
                .isEqualToComparingFieldByField(ashTrainer);
        trainerDAO.deleteTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId())).isNull();
    }

    @Test
    public void findTrainerByNullIdTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.findTrainerById(null));
    }

    @Test
    public void findTrainerByIdTest() {
        trainerDAO.createTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId()))
                .isEqualToComparingFieldByField(ashTrainer);
    }

    @Test
    public void getAllTrainersTest() {
        trainerDAO.createTrainer(ashTrainer);
        trainerDAO.createTrainer(brockTrainer);
        assertThat(trainerDAO.getAllTrainers()).usingFieldByFieldElementComparator()
                .containsOnly(ashTrainer, brockTrainer);
    }

    @Test
    public void getAdminCountTestNoTrainer() {
        assertThat(trainerDAO.getAdminCount()).isEqualTo(0);
    }

    @Test
    public void getAdminCountTestOneAdmin() {

        Trainer adminTrainer = new TrainerBuilder()
                .born(LocalDate.of(1996, 1, 1))
                .isAdmin(true)
                .name("Amnin")
                .surname("Admin")
                .userName("Admin")
                .passwordHash("adminpwd")
                .build();

        trainerDAO.createTrainer(ashTrainer);
        trainerDAO.createTrainer(adminTrainer);

        assertThat(trainerDAO.getAdminCount()).isEqualTo(1L);
    }

    @Test
    public void findTrainerByUserNameTest() {
        trainerDAO.createTrainer(ashTrainer);
        assertThat(trainerDAO.findTrainerByUsername(ashTrainer.getUserName()))
                .isEqualToComparingFieldByField(ashTrainer);
    }

    @Test
    public void findTrainerByNonExistentUserNameTest() {
        assertThat(trainerDAO.findTrainerByUsername("wrong_user_name"))
                .isNull();
    }
}
