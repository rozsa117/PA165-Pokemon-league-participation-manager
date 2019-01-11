package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerRenameDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Facade interface implementation for object Trainer.
 *
 * @author Jiří Medveď 38451
 */
@Service
@Transactional
public class TrainerFacadeImpl implements TrainerFacade {

    @Inject
    private TrainerService trainerService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public boolean authenticate(TrainerAuthenticateDTO trainer) {
        return trainerService.authenticate(trainerService.getTrainerWithId(trainer.getUserId()),
                trainer.getPassword());
    }

    @Override
    public boolean changePassword(TrainerChangePasswordDTO trainerChangePassword) {
        return trainerService.changePassword(trainerService
                .getTrainerWithId(trainerChangePassword.getTrainerId()),
                trainerChangePassword.getOldPassword(),
                trainerChangePassword.getNewPassword());
    }

    @Override
    public Long createTrainer(TrainerCreateDTO trainer) throws NoAdministratorException {
        Trainer trainerEntity = beanMappingService.mapTo(trainer, Trainer.class);
        return trainerService.createTrainer(trainerEntity, trainer.getPassword()).getId();
    }

    @Override
    public List<TrainerDTO> getAllTrainers() {
        return beanMappingService.mapTo(trainerService.getAllTrainers(), TrainerDTO.class);
    }

    @Override
    public TrainerDTO getTrainerWithId(Long trainerId) {
        Trainer trainer = trainerService.getTrainerWithId(trainerId);
        return trainer == null ? null : beanMappingService.mapTo(trainerService.getTrainerWithId(trainerId), TrainerDTO.class);
    }

    @Override
    public boolean isGymLeader(Long trainerId) {
        return trainerService.isGymLeader(trainerService.getTrainerWithId(trainerId));
    }

    @Override
    public void renameTrainer(TrainerRenameDTO trainerRename) {
        Trainer trainerEntity = trainerService.getTrainerWithId(trainerRename.getId());
        trainerService.renameTrainer(trainerEntity, trainerRename.getName(), trainerRename.getSurname());
    }

    @Override
    public void setAdmin(Long trainerId, boolean admin) throws NoAdministratorException {
        trainerService.setAdmin(trainerService.getTrainerWithId(trainerId), admin);
    }
    
    @Override
    public TrainerDTO findTrainerByUsername(String username) {
        Trainer trainer = trainerService.findTrainerByUsername(username);
        return trainer == null ? null : beanMappingService.mapTo(trainerService.findTrainerByUsername(username), TrainerDTO.class);
    }

}
