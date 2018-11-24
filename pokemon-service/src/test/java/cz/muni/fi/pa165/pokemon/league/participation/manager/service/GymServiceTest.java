package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.GymDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.dao.DataAccessException;
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

    @Mock
    private BadgeDAO badgeDAO;

    @Inject
    @InjectMocks
    private GymServiceImpl gymService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //Existing gyms
    private Gym gymPraha;
    private Gym gymBrno;

    //Gym to be created
    private Gym gymOstrava;

    //Gym to throw DataAccessException
    private Gym errorneousGym;

    //Gym Leaders
    private Trainer gymLeaderPraha;
    private Trainer gymLeaderBrno;
    private Trainer gymLeaderOstrava;

    //Trainer without gym
    private Trainer otherTrainer;

    private final DataAccessException dae = new DataAccessException("throw") {
    };

    public GymServiceTest() {
    }

    @Before
    public void setUp() {

        gymLeaderPraha = new TrainerBuilder()
                .id(100L)
                .born(LocalDate.of(1997, 8, 13))
                .isAdmin(false)
                .name("Ash")
                .surname("Ketchup")
                .userName("Ash1997")
                .passwordHash("ash123")
                .build();

        gymLeaderBrno = new TrainerBuilder()
                .id(101L)
                .born(LocalDate.of(1991, 1, 15))
                .isAdmin(false)
                .name("Johny")
                .surname("Someone")
                .userName("Johny1991")
                .passwordHash("johny123")
                .build();

        gymLeaderOstrava = new TrainerBuilder()
                .id(102L)
                .born(LocalDate.of(2000, 12, 24))
                .isAdmin(false)
                .name("Pepa")
                .surname("Ostravak")
                .userName("Peppa2000")
                .passwordHash("peppa123")
                .build();

        otherTrainer = new TrainerBuilder()
                .id(103L)
                .born(LocalDate.of(1996, 1, 25))
                .isAdmin(false)
                .name("Brock")
                .surname("Sleepyeye")
                .userName("BrockRock")
                .passwordHash("brockpswd")
                .build();

        gymPraha = new GymBuilder()
                .id(10L)
                .gymLeader(gymLeaderPraha)
                .type(PokemonType.DARK)
                .location("Praha")
                .build();

        gymBrno = new GymBuilder()
                .id(11L)
                .gymLeader(gymLeaderBrno)
                .type(PokemonType.FIGHTING)
                .location("Brno")
                .build();

        gymOstrava = new GymBuilder()
                .id(11L)
                .gymLeader(gymLeaderOstrava)
                .type(PokemonType.GROUND)
                .location("Ostrava")
                .build();

        errorneousGym = new GymBuilder()
                .id(Long.MAX_VALUE)
                .gymLeader(gymLeaderOstrava)
                .type(PokemonType.BUG)
                .location("Nowhere")
                .build();

        when(gymDAO.findGymById(gymPraha.getId())).thenReturn(gymPraha);
        when(gymDAO.findGymById(gymBrno.getId())).thenReturn(gymBrno);
        when(gymDAO.getAllGyms()).thenReturn(Stream.of(gymPraha, gymBrno)
                .collect(Collectors.toList()));

    }

    @Test
    public void testFindById() {
        assertThat(gymService.findGymById(gymPraha.getId()))
                .isEqualToComparingFieldByField(gymPraha);
    }

    @Test
    public void testFindByIdWithException() {
        doThrow(dae).when(gymDAO).findGymById(errorneousGym.getId());

        assertThatExceptionOfType(DataAccessException.class)
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService.findGymById(errorneousGym.getId()));
    }

    @Test
    public void testFindByNonExistingId() {
        assertThat(gymService.findGymById(Long.MIN_VALUE)).isNull();
    }

    @Test
    public void testUpdateGymLocation() {
        String newLocation = "New Location";
        gymService.updateGymLocation(gymPraha, newLocation);

        assertThat(gymService.findGymById(gymPraha.getId()).getLocation())
                .isEqualTo(newLocation);
    }

    @Test
    public void testUpdateGymLocationWithException() {
        doThrow(dae).when(gymDAO).updateGym(errorneousGym);

        String newLocation = "New Location";

        assertThatExceptionOfType(DataAccessException.class)
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService.updateGymLocation(errorneousGym, newLocation));
    }

    @Test
    public void testChangeGymTypeByOwnTrainer() {
        PokemonType newType = PokemonType.STEEL;
        boolean result = gymService.changeGymType(gymPraha, gymPraha.getGymLeader(), newType);
        assertThat(result).isTrue();
        assertThat(gymService.findGymById(gymPraha.getId()).getType())
                .isEqualTo(newType);
    }

    @Test
    public void testChangeGymTypeByOtherTrainer() {
        PokemonType originalType = gymPraha.getType();
        PokemonType newType = PokemonType.STEEL;
        boolean result = gymService.changeGymType(gymPraha, otherTrainer, newType);
        assertThat(result).isFalse();
        assertThat(gymService.findGymById(gymPraha.getId()).getType())
                .isEqualTo(originalType);
    }

    @Test
    public void testChangeGymTypeWithException() {
        doThrow(dae).when(gymDAO).updateGym(errorneousGym);

        PokemonType newType = PokemonType.STEEL;

        assertThatExceptionOfType(DataAccessException.class)
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService.changeGymType(
                errorneousGym,
                errorneousGym.getGymLeader(),
                newType));
    }

    @Test
    public void testChangeGymLeader() throws EntityIsUsedException {
        gymService.changeGymLeader(gymPraha, otherTrainer);

        assertThat(gymService.findGymById(gymPraha.getId()).getGymLeader())
                .isEqualTo(otherTrainer);
    }

    @Test
    public void testChangeDuplicateGymLeader() {
        assertThatExceptionOfType(EntityIsUsedException.class)
                .as("Testing GymLeader change to a trainer already used on another Gym")
                .isThrownBy(() -> gymService
                .changeGymLeader(gymPraha, gymLeaderBrno));
    }

    @Test
    public void testChangeGymLeaderWithException() {
        doThrow(dae).when(gymDAO).updateGym(errorneousGym);

        assertThatExceptionOfType(DataAccessException.class)
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService
                .changeGymLeader(errorneousGym, otherTrainer));
    }

    @Test
    public void testRemoveGym() throws EntityIsUsedException {
        gymService.removeGym(gymPraha);
        verify(gymDAO, times(1)).deleteGym(gymPraha);
    }

    @Test
    public void testRemoveGymWithBadge() {

        Badge badge = new BadgeBuilder()
                .id(100L)
                .gym(gymBrno)
                .trainer(otherTrainer)
                .date(LocalDate.of(2018, Month.MARCH, 1))
                .status(ChallengeStatus.WAITING_TO_ACCEPT)
                .build();

        when(badgeDAO.findBadgesOfGym(gymBrno)).thenReturn(Stream.of(badge)
                .collect(Collectors.toList()));

        assertThatExceptionOfType(EntityIsUsedException.class)
                .as("Gym with an exisitng badges should not be possible to remove")
                .isThrownBy(() -> gymService
                .removeGym(gymBrno));
    }

    @Test
    public void testRemoveGymWithException() {
        doThrow(dae).when(gymDAO).deleteGym(errorneousGym);

        assertThatExceptionOfType(DataAccessException.class)
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService
                .removeGym(errorneousGym));
    }

    @Test
    public void testCreateGym() throws EntityIsUsedException {
        gymService.createGym(gymOstrava);
        verify(gymDAO, times(1)).createGym(gymOstrava);
    }

    @Test
    public void testCreateGymDuplicateGymLeader() {
        gymOstrava.setGymLeader(gymLeaderBrno);

        assertThatExceptionOfType(EntityIsUsedException.class)
                .as("Testing create Gym with Gym Leader that is used on another Gym")
                .isThrownBy(() -> gymService
                .createGym(gymOstrava));
    }

    @Test
    public void testCreateGymWithException() {
        doThrow(dae).when(gymDAO).createGym(errorneousGym);

        assertThatExceptionOfType(DataAccessException.class
        )
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService
                .createGym(errorneousGym));
    }

    @Test
    public void testGetAllGyms() {
        assertThat(gymService.getAllGyms()).containsExactly(gymPraha, gymBrno);
    }

    @Test
    public void testGetAllGymsWithException() {
        doThrow(dae).when(gymDAO).getAllGyms();

        assertThatExceptionOfType(DataAccessException.class
        )
                .as("Testing DAO exception")
                .isThrownBy(() -> gymService
                .getAllGyms());
    }

    @Test
    public void testGetGymLeader() {
        assertThat(gymService.getGymLeader(gymPraha))
                .isEqualToComparingFieldByField(gymPraha.getGymLeader());
    }

    @Test
    public void testGetGymLeaderWithException() {
        doThrow(dae).when(gymDAO).findGymById(errorneousGym.getId());

        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> gymService
                .getGymLeader(errorneousGym));
    }

    @Test
    public void testFindGymByTrainer() {
        assertThat(gymService.findGymByLeader(gymLeaderPraha))
                .isEqualToComparingFieldByField(gymPraha);
    }

    @Test
    public void testFindGymByTrainerWithoutGym() {
        assertThat(gymService.findGymByLeader(otherTrainer))
                .as("Testing trainer that is not a GymLeader on any gym")
                .isNull();
    }

    @Test
    public void testFindGymByType() {
        assertThat(gymService.findGymsByType(gymPraha.getType())).containsOnly(gymPraha);
    }

    @Test
    public void testFindGymByTypeWithoutGym() {
        assertThat(gymService.findGymsByType(PokemonType.PSYCHIC))
                .as("Testing PokomonType not used on any Gym")
                .isEmpty();
    }
}
