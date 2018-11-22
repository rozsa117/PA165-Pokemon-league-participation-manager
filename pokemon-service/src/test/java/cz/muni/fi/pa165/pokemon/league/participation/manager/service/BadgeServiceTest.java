package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import java.util.function.Consumer;


/**
 * Tests for badge service.
 * 
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BadgeServiceTest {
    
    @Mock
    private BadgeDAO badgeDAO;
    
    @Inject
    @InjectMocks
    private BadgeServiceImpl badgeService;
    
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); 
    
    private Badge badgeWon;
    private Badge badgeRevoked;
    private Badge badge;
    
    private Trainer trainerLeader;
    private Trainer trainer;
    
    private Gym gymWithBadges;
    private Gym gymWithoutBadges;
    
    public BadgeServiceTest() {
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
        
        trainer = new TrainerBuilder()
                .id(101L)
                .born(LocalDate.of(1996, 1, 25))
                .isAdmin(false)
                .name("Brock")
                .surname("Sleepyeye")
                .userName("BrockRock")
                .passwordHash("brockpswd")
                .build();
        
        gymWithoutBadges = new GymBuilder()
                .id(10L)
                .gymLeader(trainerLeader)
                .type(PokemonType.DARK)
                .location("Praha")
                .build();
        
        gymWithBadges = new GymBuilder()
                .id(11L)
                .gymLeader(trainerLeader)
                .type(PokemonType.FIRE)
                .location("Brno")
                .build();
        
        badgeWon = new BadgeBuilder()
                .id(1L)
                .status(ChallengeStatus.WON)
                .date(LocalDate.now())
                .gym(gymWithBadges)
                .trainer(trainer)
                .build();
        
        badgeRevoked = new BadgeBuilder()
                .id(2L)
                .status(ChallengeStatus.REVOKED)
                .date(LocalDate.now().minusDays(1))
                .gym(gymWithBadges)
                .trainer(trainer)
                .build();
        
        badge = new BadgeBuilder()
                .id(3L)
                .date(LocalDate.now())
                .gym(gymWithBadges)
                .status(ChallengeStatus.LOST)
                .trainer(trainer)
                .build();
        
        when(badgeDAO.findBadgeById(badgeWon.getId())).thenReturn(badgeWon);
        when(badgeDAO.findBadgeById(badgeRevoked.getId())).thenReturn(badgeRevoked);
        when(badgeDAO.findBadgesOfTrainer(trainer)).thenReturn(Stream.of(badgeWon, badgeRevoked).collect(Collectors.toList()));
        when(badgeDAO.findBadgesOfTrainer(trainerLeader)).thenReturn(new ArrayList<>());
        when(badgeDAO.findBadgesOfGym(gymWithBadges)).thenReturn(Stream.of(badgeWon, badgeRevoked).collect(Collectors.toList()));
        when(badgeDAO.findBadgesOfGym(gymWithoutBadges)).thenReturn(new ArrayList<>());
    
        doAnswer(invocation -> {
            badgeRevoked = badge;
            return null;
        }).when(badgeDAO).createBadge(badge);
        
        doAnswer(invocation -> {
            badge.setId(null);
            return null;
        }).when(badgeDAO).deleteBadge(badge);
    }
 
    @Test
    public void testFindById() {
        assertThat(badgeService.findBadgeById(badgeWon.getId())).isEqualToComparingFieldByField(badgeWon);
    }
    
    @Test
    public void testFindByNonExistingId() {
        assertThat(badgeService.findBadgeById(Long.MIN_VALUE)).isNull();
    }
    
    @Test
    public void testFindBadgesOfTrainerWithBadges() {
        assertThat(badgeService.findBadgesOfTrainer(trainer))
                .usingFieldByFieldElementComparator().containsOnly(badgeRevoked, badgeWon);
    }
    
    @Test
    public void testFindBadgesOfTrainerWithoutBadges() {
        assertThat(badgeService.findBadgesOfTrainer(trainerLeader)).isEmpty();
    }
    
    @Test
    public void testFindBadgesOfGymWithBadges() {
        assertThat(badgeService.findBadgesOfGym(gymWithBadges))
                .usingFieldByFieldElementComparator().containsOnly(badgeRevoked, badgeWon);
    }
    
    @Test
    public void testFindBadgesOfGymWithoutBadges() {
        assertThat(badgeService.findBadgesOfGym(gymWithoutBadges)).isEmpty();
    }
    
    @Test
    public void testChangeBadgeStatus() {
        badgeService.changeBadgeStatus(badgeWon, ChallengeStatus.LOST);
        assertThat(badgeWon.getStatus()).isEqualTo(ChallengeStatus.LOST);
    }
    
    @Test
    public void testCreatebadge() {
        badgeService.createBadge(badge);
        assertThat(badgeRevoked).isEqualToComparingFieldByField(badge);
    }
    
    @Test
    public void testRemoveBadge() {
        badgeService.removeBadge(badge);
        assertThat(badge.getId()).isNull();
    }

    @Test
    public void testCreateBadgeWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.createBadge(badge));
    }
    
    @Test
    public void testUpdateadgeWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.changeBadgeStatus(badge, ChallengeStatus.WAITING_TO_ACCEPT));
    }
    
    @Test
    public void testRemoveBadgeWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.removeBadge(badge));
    }
    
    @Test
    public void testFindByIdWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.findBadgeById(badge.getId()));
    }
    
    @Test
    public void testFindBadgesOfTraierWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.findBadgesOfTrainer(trainerLeader));
    }
    
    @Test
    public void testFindBadgesOfGymWithExceptionThrown() {
        testExpectedDataAccessException((badgeService) -> badgeService.findBadgesOfGym(gymWithoutBadges));
    }
        
    private void testExpectedDataAccessException(Consumer<BadgeService> operation) {
        DataAccessException dae = new DataAccessException("throw") {};
        
        when(badgeDAO.findBadgeById(badge.getId())).thenThrow(dae);
        when(badgeDAO.findBadgesOfTrainer(trainerLeader)).thenThrow(dae);
        when(badgeDAO.findBadgesOfGym(gymWithoutBadges)).thenThrow(dae);
            
        doAnswer(invocation -> {
            throw dae;
        }).when(badgeDAO).createBadge(badge);
        
        doAnswer(invocation -> {
            throw dae;
        }).when(badgeDAO).updateBadge(badge);
        
        doAnswer(invocation -> {
            throw dae;
        }).when(badgeDAO).deleteBadge(badge);
        
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> operation.accept(badgeService));
    }
}
