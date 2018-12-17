package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.TrainerDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Unit and tests for TrainerFacade.
 *
 * @author Tibor Zauko 433531
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TrainerFacadeTest {

    @Mock
    private TrainerService mockTrainerService;

    @Mock
    private BeanMappingService mockMappingService;

    @Inject
    @InjectMocks
    private TrainerFacadeImpl trainerFacade;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private TrainerCreateDTO adminCreateDTO;
    private TrainerCreateDTO trainerCreateDTO;
    private TrainerRenameDTO trainerRenameDTO;
    private TrainerAuthenticateDTO trainerAuthenticateDTO;
    private TrainerChangePasswordDTO trainerChangePasswordDTO;

    private static final Long ADMIN_ID = 1L;
    private static final Long TRAINER_ID = 2L;
    private Trainer admin;
    private Trainer trainer;
    private TrainerDTO adminDTO;
    private TrainerDTO trainerDTO;

    private List<Trainer> getAllTrainersList;

    private static final String PASSWD = "plaintext";

    @Before
    public void setUp() throws NoAdministratorException {
        adminCreateDTO = new TrainerCreateDTO();
        adminCreateDTO.setAdmin(true);
        adminCreateDTO.setBorn(LocalDate.of(1990, Month.MARCH, 5));
        adminCreateDTO.setName("Mister");
        adminCreateDTO.setSurname("Goodshow");
        adminCreateDTO.setUserName("adminforever");
        adminCreateDTO.setPassword(PASSWD);

        trainerCreateDTO = new TrainerCreateDTO();
        trainerCreateDTO.setAdmin(false);
        trainerCreateDTO.setBorn(LocalDate.of(1997, Month.OCTOBER, 27));
        trainerCreateDTO.setName("Ash");
        trainerCreateDTO.setSurname("Ketchum");
        trainerCreateDTO.setUserName("legend");
        trainerCreateDTO.setPassword(PASSWD);

        trainerRenameDTO = new TrainerRenameDTO();
        trainerRenameDTO.setName("Hash");
        trainerRenameDTO.setSurname("Ketchum");
        trainerRenameDTO.setTrainerId(TRAINER_ID);

        trainerAuthenticateDTO = new TrainerAuthenticateDTO();
        trainerAuthenticateDTO.setPassword(PASSWD);
        trainerAuthenticateDTO.setUserId(TRAINER_ID);

        trainerChangePasswordDTO = new TrainerChangePasswordDTO();
        trainerChangePasswordDTO.setOldPassword(PASSWD);
        trainerChangePasswordDTO.setNewPassword(PASSWD + PASSWD);
        trainerChangePasswordDTO.setTrainerId(TRAINER_ID);

        // Set up how the trainers are expected to be mapped on create:
        admin = new TrainerBuilder()
                .born(adminCreateDTO.getBorn())
                .id(ADMIN_ID)
                .isAdmin(adminCreateDTO.isAdmin())
                .name(adminCreateDTO.getName())
                .surname(adminCreateDTO.getSurname())
                .userName(adminCreateDTO.getUserName())
                .passwordHash(null)
                .build();

        trainer = new TrainerBuilder()
                .born(trainerCreateDTO.getBorn())
                .id(TRAINER_ID)
                .isAdmin(trainerCreateDTO.isAdmin())
                .name(trainerCreateDTO.getName())
                .surname(trainerCreateDTO.getSurname())
                .userName(trainerCreateDTO.getUserName())
                .passwordHash(null)
                .build();

        adminDTO = new TrainerDTOBuilder()
                .admin(admin.isAdmin())
                .born(admin.getBorn())
                .id(ADMIN_ID)
                .name(admin.getName())
                .surname(admin.getSurname())
                .userName(admin.getUserName())
                .build();

        trainerDTO = new TrainerDTOBuilder()
                .admin(trainer.isAdmin())
                .born(trainer.getBorn())
                .id(TRAINER_ID)
                .name(trainer.getName())
                .surname(trainer.getSurname())
                .userName(trainer.getUserName())
                .build();

        getAllTrainersList = Arrays.asList(admin, trainer);

        when(mockTrainerService.getAllTrainers()).thenReturn(getAllTrainersList);

        when(mockTrainerService.getTrainerWithId(ADMIN_ID)).thenReturn(admin);
        when(mockTrainerService.getTrainerWithId(TRAINER_ID)).thenReturn(trainer);

        when(mockTrainerService.isGymLeader(admin)).thenReturn(true);
        when(mockTrainerService.isGymLeader(trainer)).thenReturn(false);

        when(mockTrainerService.authenticate(admin, adminCreateDTO.getPassword())).thenReturn(true);
        when(mockTrainerService.authenticate(trainer, trainerCreateDTO.getPassword())).thenReturn(false);

        when(mockTrainerService.createTrainer(trainer, trainerCreateDTO.getPassword()))
                .thenThrow(new NoAdministratorException("Would have no admin"));
        when(mockTrainerService.createTrainer(admin, adminCreateDTO.getPassword()))
                .thenReturn(admin);

        when(mockTrainerService.changePassword(trainer, PASSWD, PASSWD + PASSWD))
                .thenReturn(true);

        when(mockTrainerService.findTrainerByUsername(trainer.getUserName()))
                .thenReturn(trainer);

        when(mockTrainerService.findTrainerByUsername(admin.getUserName()))
                .thenReturn(admin);

        when(mockMappingService.mapTo(admin, TrainerDTO.class))
                .thenReturn(adminDTO);
        when(mockMappingService.mapTo(trainer, TrainerDTO.class))
                .thenReturn(trainerDTO);

        when(mockMappingService.mapTo(getAllTrainersList, TrainerDTO.class))
                .thenReturn(Arrays.asList(adminDTO, trainerDTO));

        when(mockMappingService.mapTo(adminCreateDTO, Trainer.class))
                .thenReturn(admin);
        when(mockMappingService.mapTo(trainerCreateDTO, Trainer.class))
                .thenReturn(trainer);
    }

    @Test
    public void testCreateTrainer() throws Exception {
        assertThatExceptionOfType(NoAdministratorException.class)
                .isThrownBy(() -> trainerFacade.createTrainer(trainerCreateDTO));
        verify(mockTrainerService, atLeastOnce()).createTrainer(trainer, trainerCreateDTO.getPassword());
        assertThat(trainerFacade.createTrainer(adminCreateDTO))
                .isEqualTo(ADMIN_ID);
        verify(mockTrainerService, atLeastOnce()).createTrainer(admin, adminCreateDTO.getPassword());
    }

    @Test
    public void testGetAllTrainers() {
        assertThat(trainerFacade.getAllTrainers())
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(adminDTO, trainerDTO);
        verify(mockTrainerService, atLeastOnce()).getAllTrainers();
    }

    @Test
    public void testGetTrainerWithId() {
        assertThat(trainerFacade.getTrainerWithId(TRAINER_ID))
                .isEqualToComparingFieldByField(trainerDTO);
        verify(mockTrainerService, atLeastOnce()).getTrainerWithId(TRAINER_ID);
        assertThat(trainerFacade.getTrainerWithId(ADMIN_ID))
                .isEqualToComparingFieldByField(adminDTO);
        verify(mockTrainerService, atLeastOnce()).getTrainerWithId(ADMIN_ID);
    }

    @Test
    public void testIsGymLeader() {
        assertThat(trainerFacade.isGymLeader(TRAINER_ID))
                .isFalse();
        verify(mockTrainerService, atLeastOnce()).isGymLeader(trainer);
        assertThat(trainerFacade.isGymLeader(ADMIN_ID))
                .isTrue();
        verify(mockTrainerService, atLeastOnce()).isGymLeader(admin);
    }

    @Test
    public void testRenameTrainer() {
        trainerFacade.renameTrainer(trainerRenameDTO);
        verify(mockTrainerService, atLeastOnce()).renameTrainer(trainer, "Hash", "Ketchum");
    }

    @Test
    public void testSetAdmin() throws Exception {
        trainerFacade.setAdmin(TRAINER_ID, true);
        verify(mockTrainerService, atLeastOnce()).setAdmin(trainer, true);
    }

    @Test
    public void testAuthenticate() {
        trainerAuthenticateDTO.setUserId(ADMIN_ID);
        assertThat(trainerFacade.authenticate(trainerAuthenticateDTO))
                .isTrue();
        trainerAuthenticateDTO.setUserId(TRAINER_ID);
        assertThat(trainerFacade.authenticate(trainerAuthenticateDTO))
                .isFalse();
    }

    @Test
    public void testChangePassword() {
        assertThat(trainerFacade.changePassword(trainerChangePasswordDTO))
                .isTrue();
        verify(mockTrainerService, atLeastOnce()).changePassword(trainer, PASSWD, PASSWD + PASSWD);
    }

    @Test
    public void testFindTrainerByUsername() {
        assertThat(trainerFacade.findTrainerByUsername(trainerDTO.getUserName()))
                .isEqualToComparingFieldByField(trainerDTO);
        verify(mockTrainerService, atLeastOnce()).findTrainerByUsername(trainerDTO.getUserName());
        assertThat(trainerFacade.findTrainerByUsername(adminDTO.getUserName()))
                .isEqualToComparingFieldByField(adminDTO);
        verify(mockTrainerService, atLeastOnce()).findTrainerByUsername(adminDTO.getUserName());
    }
}
