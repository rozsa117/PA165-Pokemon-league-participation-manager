package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

/**
 * Unit tests to Trainer data access object
 *
 * @author Michal Mokros 456442
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@Transactional
@RunWith(MockitoJUnitRunner.class)
public class TrainerDAOTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private TrainerDAO trainerDAO;

    @Mock
    private Trainer ashTrainer;

    @Mock
    private Trainer brockTrainer;

    @Before
    public void setUp() {
        ashTrainer.setBorn(LocalDate.of(1997, 9, 21));
        ashTrainer.setName("Ash");
        ashTrainer.setSurname("Ketchup");
        ashTrainer.setUserName("Ash1997");
        ashTrainer.setPasswordHash("12345678");
        ashTrainer.setAdmin(false);

        brockTrainer.setBorn(LocalDate.of(1996, 10, 28));
        brockTrainer.setName("Brock");
        brockTrainer.setSurname("Sleepyeye");
        brockTrainer.setUserName("Brock-_-");
        brockTrainer.setPasswordHash("00001111");
        brockTrainer.setAdmin(false);

        trainerDAO.createTrainer(ashTrainer);
        trainerDAO.createTrainer(brockTrainer);
    }

    @Test
    public void createNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.createTrainer(null));
    }

    @Test
    public void createTrainerWithNonNullIdTest() {
        Trainer trainer = mock(Trainer.class);
        trainer.setId(10L);
        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> trainerDAO.createTrainer(trainer));
    }

    @Test
    public void createTrainerTest() {
        Trainer trainer = mock(Trainer.class);
        trainerDAO.createTrainer(trainer);
        assertThat(trainerDAO.findTrainerById(trainer.getId()))
                .isEqualToComparingFieldByField(trainer);
        assertThat(em.contains(trainer)).isTrue();
    }

    @Test
    public void updateNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.updateTrainer(null));
    }

    @Test
    public void updateTrainerWithNullIdTest() {
        Trainer trainer = trainerDAO.findTrainerById(ashTrainer.getId());
        trainer.setId(null);
        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> trainerDAO.updateTrainer(trainer));
    }

    @Test
    public void updateTrainerTest() {
        Trainer trainer = trainerDAO.findTrainerById(ashTrainer.getId());
        trainer.setUserName("AshKetchup1997");
        trainerDAO.updateTrainer(trainer);
        assertThat(trainerDAO.findTrainerById(trainer.getId()))
                .isEqualToComparingFieldByField(trainer);
        assertThat(em.contains(trainer));
    }

    @Test
    public void deleteNullTrainerTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.deleteTrainer(null));
    }

    @Test
    public void deleteTrainerWithNullIdTest() {
        Trainer trainer = trainerDAO.findTrainerById(ashTrainer.getId());
        trainer.setId(null);
        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> trainerDAO.deleteTrainer(trainer));
    }

    @Test
    public void deleteTrainerTest() {
        Trainer trainer = trainerDAO.findTrainerById(ashTrainer.getId());
        trainerDAO.deleteTrainer(trainer);
        assertThat(trainerDAO.findTrainerById(trainer.getId())).isNull();
        assertThat(em.contains(trainer)).isFalse();
    }

    @Test
    public void findTrainerByNullIdTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> trainerDAO.findTrainerById(null));
    }

    @Test
    public void findTrainerByIdTest() {
        assertThat(trainerDAO.findTrainerById(ashTrainer.getId()))
                .isEqualToComparingFieldByField(ashTrainer);
    }

    @Test
    public void getAllTrainersTest() {
        assertThat(trainerDAO.getAllTrainers()).usingFieldByFieldElementComparator()
                .containsOnly(ashTrainer, brockTrainer);
    }
}

