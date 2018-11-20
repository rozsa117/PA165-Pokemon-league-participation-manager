package cz.muni.fi.pa165.pokemon.league.participation.manager.service.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.*;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;
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
    private PokemonService pokemonService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override

    public void addPokemon(Long trainerId, Long pokemonId) {
        trainerService.getTrainerWithId(trainerId)
                .addPokemon(pokemonService.findPokemonById(pokemonId));
    }

    @Override
    public Boolean authenticate(TrainerAuthenticateDTO trainer) {
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
    public List<PokemonDTO> getOwnPokemons(Long trainerId) {
        return beanMappingService.mapTo(trainerService.getTrainerWithId(trainerId)
                .getPokemons(), PokemonDTO.class);
    }

    @Override
    public TrainerDTO getTrainerWithId(Long trainerId) {
        return beanMappingService.mapTo(trainerService.getTrainerWithId(trainerId), TrainerDTO.class);
    }

    @Override
    public Boolean isGymLeader(Long trainerId) {
        return trainerService.isGymLeader(trainerService.getTrainerWithId(trainerId));
    }

    @Override
    public void removePokemon(Long trainerId, Long pokemonId) {
        trainerService.getTrainerWithId(trainerId).deletePokemon(pokemonService.findPokemonById(pokemonId));
    }

    @Override
    public void renameTrainer(TrainerRenameDTO trainerRename) {
        Trainer trainerEntity = trainerService.getTrainerWithId(trainerRename.getTrainerId());
        trainerEntity.setName(trainerRename.getName());
        trainerEntity.setSurname(trainerRename.getSurname());
    }

    @Override
    public void setAdmin(Long trainerId, boolean admin) throws NoAdministratorException {
        trainerService.setAdmin(trainerService.getTrainerWithId(trainerId), admin);
    }

}
