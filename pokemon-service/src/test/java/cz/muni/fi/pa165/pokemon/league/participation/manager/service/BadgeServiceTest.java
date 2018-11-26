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
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.BeforeClass;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


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
    
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule(); 
    
    private static Badge badgeWon;
    private static Badge badgeRevoked;
    private static Badge badge;
    
    private static Trainer trainerLeader;
    private static Trainer trainer;
    
    private static Gym gymWithBadges;
    private static Gym gymWithoutBadges;
    
    public BadgeServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() { 
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

        
    }
    
    @Before
    public void setUp() {
               
        badgeRevoked = new BadgeBuilder()
                .id(2L)
                .date(LocalDate.of(2018, Month.MARCH, 1))
                .gym(gymWithBadges)
                .status(ChallengeStatus.REVOKED)
                .trainer(trainer)
                .build();
        
        badge = new BadgeBuilder()
                .id(3L)
                .date(LocalDate.of(2018, Month.MARCH, 1))
                .gym(gymWithBadges)
                .status(ChallengeStatus.LOST)
                .trainer(trainer)
                .build();
        
        when(badgeDAO.findBadgeById(badgeWon.getId())).thenReturn(badgeWon);
        when(badgeDAO.findBadgeById(badgeRevoked.getId())).thenReturn(badgeRevoked);
        when(badgeDAO.findBadgesOfTrainer(trainer)).thenReturn(Arrays.asList(new Badge[] {badgeWon, badgeRevoked}));
        when(badgeDAO.findBadgesOfTrainer(trainerLeader)).thenReturn(new ArrayList<>());
        when(badgeDAO.findBadgesOfGym(gymWithBadges)).thenReturn(Arrays.asList(new Badge[] {badgeWon, badgeRevoked}));
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
    public void testChangeBadgeStatus() throws InvalidChallengeStatusChangeException {
        badgeService.changeBadgeStatus(badgeWon, ChallengeStatus.REVOKED);
        assertThat(badgeWon.getStatus()).isEqualTo(ChallengeStatus.REVOKED);
    }
    
    @Test
    public void testCreateBadge() throws EntityIsUsedException {
        badgeService.createBadge(badge);
        assertThat(badgeRevoked).isEqualToComparingFieldByField(badge);
    }
    
    @Test
    public void testCreateBadgeWithGymLeaderChallenger() {
        Badge badBadge = new BadgeBuilder()
                .date(LocalDate.now())
                .gym(gymWithBadges)
                .status(ChallengeStatus.WAITING_TO_ACCEPT)
                .trainer(gymWithBadges.getGymLeader())
                .build();
        assertThatExceptionOfType(EntityIsUsedException.class)
                .isThrownBy(() -> badgeService.createBadge(badBadge));
    }
    
    @Test
    public void testRemoveBadge() {
        badgeService.removeBadge(badge);
        assertThat(badge.getId()).isNull();
    }

    @Test
    public void testCreateBadgeWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> bs.createBadge(badge));
    }
    
    @Test
    public void testUpdateadgeWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> {
            try {
                badge.setStatus(ChallengeStatus.LOST);
                bs.changeBadgeStatus(badge, ChallengeStatus.WAITING_TO_ACCEPT);
            } catch (InvalidChallengeStatusChangeException ex) {
                throw new RuntimeException("This exception should not happen because of using mock.");
            }
        });
    }
    
    @Test
    public void testRemoveBadgeWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> bs.removeBadge(badge));
    }
    
    @Test
    public void testFindByIdWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> bs.findBadgeById(badge.getId()));
    }
    
    @Test
    public void testFindBadgesOfTraierWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> bs.findBadgesOfTrainer(trainerLeader));
    }
    
    @Test
    public void testFindBadgesOfGymWithExceptionThrown() {
        testExpectedDataAccessException((bs) -> bs.findBadgesOfGym(gymWithoutBadges));
    }
        
    private void testExpectedDataAccessException(ThrowingConsumer<BadgeService> operation) {
        PersistenceException pex = new PersistenceException("throw");
        
        when(badgeDAO.findBadgeById(badge.getId())).thenThrow(pex);
        when(badgeDAO.findBadgesOfTrainer(trainerLeader)).thenThrow(pex);
        when(badgeDAO.findBadgesOfGym(gymWithoutBadges)).thenThrow(pex);
            
        doThrow(pex).when(badgeDAO).createBadge(badge);
        
        doThrow(pex).when(badgeDAO).updateBadge(badge);
        
        doThrow(pex).when(badgeDAO).deleteBadge(badge);
        
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> operation.accept(badgeService));
    }
    
    @Test
    public void testChangeStatusFromWONToLOST() {
        badge.setStatus(ChallengeStatus.WON);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.LOST));
    }
    
    @Test
    public void testChangeStatusFromWONToWAITING_TO_ACCEPT() {
        badge.setStatus(ChallengeStatus.WON);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.WAITING_TO_ACCEPT));
    }
    
    @Test
    public void testChangeStatusFromWONToREVOKED() throws InvalidChallengeStatusChangeException {
        badge.setStatus(ChallengeStatus.WON);
        badgeService.changeBadgeStatus(badge, ChallengeStatus.REVOKED);
        badgeRevoked.setStatus(ChallengeStatus.REVOKED);
        verify(badgeDAO, atLeastOnce()).updateBadge(badgeRevoked);
    }
    
    @Test
    public void testChangeStatusFromREVOKEDToWON() throws InvalidChallengeStatusChangeException {
        badge.setStatus(ChallengeStatus.REVOKED);
        badgeService.changeBadgeStatus(badge, ChallengeStatus.WON);
        badgeRevoked.setStatus(ChallengeStatus.WON);
        verify(badgeDAO, atLeastOnce()).updateBadge(badgeRevoked);
    }
    
    @Test
    public void testChangeStatusFromREVOKEDToLOST() {
        badge.setStatus(ChallengeStatus.REVOKED);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.LOST));
    }
    
    @Test
    public void testChangeStatusFromREVOKEDToWAITING_TO_ACCEPT() {
        badge.setStatus(ChallengeStatus.REVOKED);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.WAITING_TO_ACCEPT));
    }
        
    @Test
    public void testChangeStatusFromLOSTToWON() {
        badge.setStatus(ChallengeStatus.LOST);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.WON));
    }
    
    @Test
    public void testChangeStatusFromLOSTToREVOKED() {
        badge.setStatus(ChallengeStatus.LOST);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.REVOKED));
    }
    
    @Test
    public void testChangeStatusFromLOSTToWAITING_TO_ACCEPT() throws InvalidChallengeStatusChangeException {
        badge.setStatus(ChallengeStatus.LOST);
        badgeService.changeBadgeStatus(badge, ChallengeStatus.WAITING_TO_ACCEPT);
        badgeRevoked.setStatus(ChallengeStatus.WAITING_TO_ACCEPT);
        verify(badgeDAO, atLeastOnce()).updateBadge(badgeRevoked);
    }
    
    @Test
    public void testChangeStatusFromWAITING_TOACCEPTToWON() throws InvalidChallengeStatusChangeException {
        badge.setStatus(ChallengeStatus.WAITING_TO_ACCEPT);
        badgeService.changeBadgeStatus(badge, ChallengeStatus.WON);
        badgeRevoked.setStatus(ChallengeStatus.WON);
        verify(badgeDAO, atLeastOnce()).updateBadge(badgeRevoked);
    }
    
    @Test
    public void testChangeStatusFromWAITING_TOACCEPTToLOST() throws InvalidChallengeStatusChangeException {
        badge.setStatus(ChallengeStatus.WAITING_TO_ACCEPT);
        badgeService.changeBadgeStatus(badge, ChallengeStatus.LOST);
        badgeRevoked.setStatus(ChallengeStatus.LOST);
        verify(badgeDAO, atLeastOnce()).updateBadge(badgeRevoked);
    }
    
    @Test
    public void testChangeStatusFromWAITING_TOACCEPTToREVOKED() {
        badge.setStatus(ChallengeStatus.WAITING_TO_ACCEPT);
        assertThatExceptionOfType(InvalidChallengeStatusChangeException.class)
                .isThrownBy(() -> badgeService.changeBadgeStatus(badge, ChallengeStatus.REVOKED));
    }

    @FunctionalInterface
    private static interface ThrowingConsumer<T> {
        
        public void accept(T t) throws Exception;
        
    }
}
