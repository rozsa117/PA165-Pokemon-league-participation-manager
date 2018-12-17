package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.EvolvePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GiftPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.LevelUpPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ReleasePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.RenamePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonSpeciesService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/**
 * Implementation of PokemonFacade.
 *
 * @author Tibor Zauko 433531
 */
@Service
@Transactional
public class PokemonFacadeImpl implements PokemonFacade {

    @Inject
    private PokemonService pokemonService;

    @Inject
    private TrainerService trainerService;

    @Inject
    private PokemonSpeciesService pokemonSpeciesService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createPokemon(PokemonCreateDTO pokemon) throws NoSuchEntityException {
        Trainer trainer = trainerService.getTrainerWithId(pokemon.getCreatingTrainerId());
        if (trainer == null) {
            throw new NoSuchEntityException("Can't create new Pokemon: trainer of id "
                    + pokemon.getCreatingTrainerId()
                    + " doesn't exist"
            );
        }
        PokemonSpecies species = pokemonSpeciesService.findPokemonSpeciesById(pokemon.getPokemonSpeciesId());
        if (species == null) {
            throw new NoSuchEntityException("Can't create new Pokemon: species of id "
                    + pokemon.getPokemonSpeciesId()
                    + " doesn't exist"
            );   
        }
        Pokemon pokemonEntity = beanMappingService.mapTo(pokemon, Pokemon.class);
        pokemonEntity.setSpecies(species);
        pokemonEntity.setDateTimeOfCapture(LocalDateTime.now());
        pokemonEntity.setTrainer(trainer);
        pokemonService.createPokemon(pokemonEntity);
        return pokemonEntity.getId();
    }

    @Override
    public PokemonDTO findPokemonById(@NotNull Long id) {
        return beanMappingService.mapTo(pokemonService.findPokemonById(id), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonOfTrainer(@NotNull Long trainerId)
            throws NoSuchEntityException {
        Trainer trainer = getNonNullRequestingTrainer(trainerId);
        return beanMappingService.mapTo(pokemonService.getPokemonOfTrainer(trainer), PokemonDTO.class);
    }

    @Override
    public void releasePokemon(ReleasePokemonDTO releasedPokemon)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer trainer = getNonNullRequestingTrainer(releasedPokemon.getRequestingTrainerId());
        Pokemon pokemon = getNonNullPokemon(releasedPokemon.getPokemonId());
        authorizeTrainer(trainer, pokemon);
        pokemonService.releasePokemon(pokemon);
    }

    @Override
    public void renamePokemon(RenamePokemonDTO newNickname)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer trainer = getNonNullRequestingTrainer(newNickname.getRequestingTrainerId());
        Pokemon pokemon = getNonNullPokemon(newNickname.getPokemonId());
        authorizeTrainer(trainer, pokemon);
        pokemonService.renamePokemon(pokemon, newNickname.getNewNickname());
    }

    @Override
    public void levelUpPokemon(LevelUpPokemonDTO newLevel)
            throws LevelNotIncreasedException, InsufficientRightsException, NoSuchEntityException {
        Trainer trainer = trainerService.getTrainerWithId(newLevel.getRequestingTrainerId());
        Pokemon pokemon = getNonNullPokemon(newLevel.getId());
        authorizeTrainer(trainer, pokemon);
        pokemonService.increasePokemonLevel(pokemon, newLevel.getLevel());
    }

    @Override
    public void evolvePokemon(EvolvePokemonDTO chosenEvolution)
            throws InvalidPokemonEvolutionException, InsufficientRightsException, NoSuchEntityException {
        Trainer trainer = trainerService.getTrainerWithId(chosenEvolution.getRequestingTrainerId());
        PokemonSpecies species = pokemonSpeciesService.findPokemonSpeciesById(chosenEvolution.getNewSpeciesId());
        if (species == null) {
            throw new NoSuchEntityException("No Pokemon species of id " + chosenEvolution.getNewSpeciesId() + " exists");
        }
        Pokemon pokemon = getNonNullPokemon(chosenEvolution.getId());
        authorizeTrainer(trainer, pokemon);
        pokemonService.evolvePokemon(pokemon, species);
    }

    @Override
    public void giftPokemon(GiftPokemonDTO gift)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer trainer = trainerService.getTrainerWithId(gift.getRequestingTrainerId());
        Trainer giftedTrainer = trainerService.getTrainerWithId(gift.getGiftedTrainerId());
        if (giftedTrainer == null) {
            throw new NoSuchEntityException("No trainer of gifted trainer id " + gift.getGiftedTrainerId() + " exists");
        }
        Pokemon pokemon = getNonNullPokemon(gift.getId());
        authorizeTrainer(trainer, pokemon);
        pokemonService.giftPokemon(pokemon, giftedTrainer);
    }

    private void authorizeTrainer(Trainer requestingTrainer, Pokemon pokemon)
            throws InsufficientRightsException {
        if (!requestingTrainer.equals(pokemon.getTrainer())) {
            throw new InsufficientRightsException(
                    "Requesting trainer of id " + requestingTrainer.getId() + " does not own Pokemon " + pokemon.getId()
            );
        }
    }

    private Pokemon getNonNullPokemon(Long pokemonId) throws NoSuchEntityException {
        Pokemon pokemon = pokemonService.findPokemonById(pokemonId);
        if (pokemon == null) {
            throw new NoSuchEntityException("No Pokemon of id " + pokemonId + " exists");
        }
        return pokemon;
    }

    private Trainer getNonNullRequestingTrainer(Long trainerId) throws NoSuchEntityException {
        Trainer trainer = trainerService.getTrainerWithId(trainerId);
        if (trainer == null) {
            throw new NoSuchEntityException("No trainer of given requesting trainer id " + trainerId + " exists");
        }
        return trainer;
    }

}
