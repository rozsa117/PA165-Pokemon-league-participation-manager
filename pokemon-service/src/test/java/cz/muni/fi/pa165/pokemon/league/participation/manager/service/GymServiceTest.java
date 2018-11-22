package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for gym service.
 *
 * @author Jiří Medveď 38451
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GymServiceTest {

    @Mock
    private GymDAO gymDAO;

    @Inject
    @InjectMocks
    private GymServiceImpl gymService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Gym gym;
    private Trainer trainerLeader;

    public GymServiceTest() {
    }

    @Before
    public void setUp() {

        trainerLeader = new TrainerBuilder()
                .id(100L)
                .born(LocalDate.of(1997, 8, 13))
                .isAdmin(false)
                .name("Ash")
                .surname("Ketchup")
                .userName("Ash1997")
                .passwordHash("ash123")
                .build();

        gym = new GymBuilder()
                .id(10L)
                .gymLeader(trainerLeader)
                .type(PokemonType.DARK)
                .location("Praha")
                .build();

        when(gymDAO.findGymById(gym.getId())).thenReturn(gym);

    }

    @Test
    public void testFindById() {
        assertThat(gymService.findGymById(gym.getId())).isEqualToComparingFieldByField(gym);
    }

    @Test
    public void testFindByNonExistingId() {
        assertThat(gymService.findGymById(Long.MIN_VALUE)).isNull();
    }

}
