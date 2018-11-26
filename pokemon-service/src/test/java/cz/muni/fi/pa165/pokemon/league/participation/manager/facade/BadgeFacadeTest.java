package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.BadgeDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.GymDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.TrainerDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BadgeServiceImpl;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerServiceImpl;
import java.time.LocalDate;
import java.time.Month;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Unit tests for BadgeFacade.
 * 
 * @author Tamás Rózsa 445653
 */
public class BadgeFacadeTest {
    
    @Mock
    private BadgeServiceImpl badgeService;
    
    @Mock
    private TrainerServiceImpl trainerService;
    
    @Mock
    private BeanMappingService beanMappingService;
    
    @InjectMocks
    private final BadgeFacadeImpl badgeFacade = new BadgeFacadeImpl();
    
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule(); 
    
    private static GymDTO gymDTO;
    private static TrainerDTO gymLeaderDTO;
    private static TrainerDTO ashDTO;
    private static BadgeDTO badgeDTO;
    
    private static Gym gymEntity;
    private static Trainer gymLeaderEntity;
    private static Trainer ashEntity;
    private static Badge badgeEntity;
    
    private static BadgeStatusChangeDTO revoke = new BadgeStatusChangeDTO();
    
    public BadgeFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
         ashDTO = new TrainerDTOBuilder()
                .id(100L)
                .born(LocalDate.of(2000, Month.APRIL, 8))
                .admin(false)
                .name("Ash")
                .surname("Ketchum")
                .userName("ketchup")
                .build();
        
         ashEntity = new TrainerBuilder()
                 .id(100L)
                .born(LocalDate.of(2000, Month.APRIL, 8))
                .name("Ash")
                .surname("Ketchum")
                .userName("ketchup")
                .passwordHash("pswda")
                .build();
         
        gymLeaderDTO = new TrainerDTOBuilder()
                .id(101L)
                .born(LocalDate.of(1995, Month.JANUARY, 19))
                .admin(false)
                .name("Brock")
                .surname("Boulder")
                .userName("onixDude")
                .build();
        
        gymLeaderEntity = new TrainerBuilder()
                .id(101L)
                .born(LocalDate.of(1995, Month.JANUARY, 19))
                .name("Brock")
                .surname("Boulder")
                .userName("onixDude")
                .passwordHash("nohash")
                .build();
        
        gymDTO = new GymDTOBuilder()
                .id(10L)
                .type(PokemonType.FIRE)
                .location("Brno")
                .gymLeader(gymLeaderDTO)
                .build();
        
        gymEntity = new GymBuilder()
                .id(10L)
                .type(PokemonType.FIRE)
                .location("Brno")
                .gymLeader(gymLeaderEntity)
                .build();
        
        badgeDTO = new BadgeDTOBuilder()
                .date(LocalDate.of(2018, Month.JANUARY, 20))
                .gym(gymDTO)
                .status(ChallengeStatus.LOST)
                .trainer(ashDTO)
                .build();
        
        badgeEntity = new BadgeBuilder()
                .date(LocalDate.of(2018, Month.JANUARY, 20))
                .gym(gymEntity)
                .status(ChallengeStatus.LOST)
                .trainer(ashEntity)
                .build();
        
        revoke.setBadgeId(badgeDTO.getId());
        revoke.setTrainerId(gymLeaderDTO.getId());
    }
    
    @Before
    public void setUp() {
        when(badgeService.findBadgeById(badgeDTO.getId())).thenReturn(badgeEntity);
        when(trainerService.getTrainerWithId(ashDTO.getId())).thenReturn(ashEntity);
        when(trainerService.getTrainerWithId(gymLeaderDTO.getId())).thenReturn(gymLeaderEntity);
    }
    
    @Test
    public void testCreateBadge() {
        BadgeCreateDTO create = new BadgeCreateDTO();
        create.setGymId(gymDTO.getId());
        create.setTrainerId(ashDTO.getId());
        create.setDate(LocalDate.of(2018, Month.JANUARY, 20));
        when(beanMappingService.mapTo(create, Badge.class)).thenReturn(badgeEntity);
        badgeFacade.createBadge(create);
        verify(badgeService, atLeastOnce()).createBadge(badgeEntity);
    }
    
    @Test
    public void testFindBadgeById() {
        when(beanMappingService.mapTo(badgeEntity, BadgeDTO.class)).thenReturn(badgeDTO);
        assertThat(badgeFacade.findBadgeById(badgeDTO.getId())).isEqualToComparingFieldByField(badgeDTO);
    }
    
    @Test
    public void testGetGymOfBadge() {
        when(beanMappingService.mapTo(gymEntity, GymDTO.class)).thenReturn(gymDTO);
        assertThat(badgeFacade.getGymOfBadge(badgeDTO.getId())).isEqualToComparingFieldByField(gymDTO);
    }
    
    @Test
    public void testGetTrainerOfBadge() {
        when(beanMappingService.mapTo(ashEntity, TrainerDTO.class)).thenReturn(ashDTO);
        assertThat(badgeFacade.getTrainerOfBadge(badgeDTO.getId())).isEqualToComparingFieldByField(ashDTO);
    }
    
    @Test
    public void testRevokeBadge() throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        badgeFacade.revokeBadge(revoke);
        verify(badgeService, atLeastOnce()).changeBadgeStatus(badgeEntity, ChallengeStatus.REVOKED);
    }
    
    @Test
    public void testLooseBadge() throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        badgeFacade.looseBadge(revoke);
        verify(badgeService, atLeastOnce()).changeBadgeStatus(badgeEntity, ChallengeStatus.LOST);
    }
    
    @Test
    public void testWinBadge() throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        badgeFacade.wonBadge(revoke);
        verify(badgeService, atLeastOnce()).changeBadgeStatus(badgeEntity, ChallengeStatus.WON);
    }
    
    @Test
    public void testInsufficientRightsException() {
        BadgeStatusChangeDTO exception = revoke;
        exception.setTrainerId(ashDTO.getId());
        assertThatExceptionOfType(InsufficientRightsException.class).isThrownBy(() -> badgeFacade.revokeBadge(exception));
    }
    
    @Test
    public void testReopenChallenge() throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        BadgeStatusChangeDTO reopen = revoke;
        revoke.setTrainerId(ashDTO.getId());
        badgeFacade.reopenChallenge(ashDTO.getId(), reopen);
        verify(badgeService, atLeastOnce()).changeBadgeStatus(badgeEntity, ChallengeStatus.WAITING_TO_ACCEPT);
    }
    
    @Test
    public void testReopenChallengeThrowstInsufficientRightsExceptionWrongTrainerId() {
        BadgeStatusChangeDTO reopen = revoke;
        assertThatExceptionOfType(InsufficientRightsException.class)
                .isThrownBy(() -> badgeFacade.reopenChallenge(ashDTO.getId(), reopen));
    }
    
    @Test
    public void testReopenChallengeThrowstInsufficientRightsExceptionBadgeIsNotLost() {
        BadgeStatusChangeDTO reopen = revoke;
        revoke.setTrainerId(ashDTO.getId());
        when(badgeService.findBadgeById(badgeDTO.getId()))
                .thenReturn(
                        new BadgeBuilder()
                            .id(1000L)
                            .date(LocalDate.now())
                            .gym(gymEntity)
                            .status(ChallengeStatus.WON)
                            .trainer(ashEntity)
                            .build()
                );
        assertThatExceptionOfType(InsufficientRightsException.class)
                .isThrownBy(() -> badgeFacade.reopenChallenge(ashDTO.getId(), reopen));
    }
}
