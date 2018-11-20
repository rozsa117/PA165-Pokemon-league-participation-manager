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
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * Implementation of PokemonFacade.
 *
 * @author Tibor Zauko 433531
 */
public class PokemonFacadeImpl implements PokemonFacade {

    @Inject
    private PokemonService ps;

    @Inject
    private TrainerService ts;

    @Inject
    private PokemonSpeciesService pss;

    @Inject
    private BeanMappingService bms;

    @Override
    public Long createPokemon(PokemonCreateDTO pokemon) throws NoSuchEntityException {
        Trainer tr = ts.getTrainerWithId(pokemon.getCreatingTrainerId());
        if (tr == null) {
            throw new NoSuchEntityException("Can't create new Pokemon: trainer of id "
                    + pokemon.getCreatingTrainerId()
                    + " doesn't exist"
            );
        }
        PokemonSpecies sp = pss.findPokemonSpeciesById(pokemon.getPokemonSpeciesId());
        if (sp == null) {
            throw new NoSuchEntityException("Can't create new Pokemon: species of id "
                    + pokemon.getPokemonSpeciesId()
                    + " doesn't exist"
            );   
        }
        Pokemon pkmn = new Pokemon();
        pkmn.setSpecies(sp);
        pkmn.setDateTimeOfCapture(LocalDateTime.now());
        pkmn.setLevel(pokemon.getLevel());
        pkmn.setNickname(pokemon.getNickname());
        pkmn.setTrainer(tr);
        ps.createPokemon(pkmn);
        return pkmn.getId();
    }

    @Override
    public PokemonDTO findPokemonById(@NotNull Long id) {
        return bms.mapTo(ps.findPokemonById(id), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonOfTrainer(@NotNull Long trainerId)
            throws NoSuchEntityException {
        Trainer tr = getNonNullRequestingTrainer(trainerId);
        return bms.mapTo(tr.getPokemon(), PokemonDTO.class);
    }

    @Override
    public void releasePokemon(ReleasePokemonDTO releasedPokemon)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer tr = getNonNullRequestingTrainer(releasedPokemon.getRequestingTrainerId());
        Pokemon pkmn = getNonNullPokemon(releasedPokemon.getPokemonId());
        authorizeTrainer(tr, pkmn);
        ps.releasePokemon(pkmn);
    }

    @Override
    public void renamePokemon(RenamePokemonDTO newNickname)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer tr = getNonNullRequestingTrainer(newNickname.getRequestingTrainerId());
        Pokemon pkmn = getNonNullPokemon(newNickname.getPokemonId());
        authorizeTrainer(tr, pkmn);
        ps.renamePokemon(pkmn, newNickname.getNewNickname());
    }

    @Override
    public void levelUpPokemon(LevelUpPokemonDTO newLevel)
            throws LevelNotIncreasedException, InsufficientRightsException, NoSuchEntityException {
        Trainer tr = ts.getTrainerWithId(newLevel.getRequestingTrainerId());
        Pokemon pkmn = getNonNullPokemon(newLevel.getPokemonId());
        authorizeTrainer(tr, pkmn);
        ps.increasePokemonLevel(pkmn, newLevel.getNewLevel());
    }

    @Override
    public void evolvePokemon(EvolvePokemonDTO chosenEvolution)
            throws InvalidPokemonEvolutionException, InsufficientRightsException, NoSuchEntityException {
        Trainer tr = ts.getTrainerWithId(chosenEvolution.getRequestingTrainerId());
        PokemonSpecies sp = pss.findPokemonSpeciesById(chosenEvolution.getNewSpeciesId());
        if (sp == null) {
            throw new NoSuchEntityException("No Pokemon species of id " + chosenEvolution.getNewSpeciesId() + " exists");
        }
        Pokemon pkmn = getNonNullPokemon(chosenEvolution.getPokemonId());
        authorizeTrainer(tr, pkmn);
        ps.evolvePokemon(pkmn, sp);
    }

    @Override
    public void giftPokemon(GiftPokemonDTO gift)
            throws InsufficientRightsException, NoSuchEntityException {
        Trainer tr = ts.getTrainerWithId(gift.getRequestingTrainerId());
        Trainer giftedTr = ts.getTrainerWithId(gift.getGiftedTrainerId());
        if (giftedTr == null) {
            throw new NoSuchEntityException("No trainer of gifted trainer id " + gift.getGiftedTrainerId() + " exists");
        }
        Pokemon pkmn = getNonNullPokemon(gift.getPokemonId());
        authorizeTrainer(tr, pkmn);
        ps.giftPokemon(pkmn, giftedTr);
    }

    private void authorizeTrainer(Trainer requester, Pokemon pkmn)
            throws InsufficientRightsException {
        if (!requester.equals(pkmn.getTrainer())) {
            throw new InsufficientRightsException(
                    "Requesting trainer of id " + requester.getId() + " does not own Pokemon " + pkmn.getId()
            );
        }
    }

    private Pokemon getNonNullPokemon(Long pokemonId) throws NoSuchEntityException {
        Pokemon pkmn = ps.findPokemonById(pokemonId);
        if (pkmn == null) {
            throw new NoSuchEntityException("No Pokemon of id " + pokemonId + " exists");
        }
        return pkmn;
    }

    private Trainer getNonNullRequestingTrainer(Long trainerId) throws NoSuchEntityException {
        Trainer tr = ts.getTrainerWithId(trainerId);
        if (tr == null) {
            throw new NoSuchEntityException("No trainer of given requesting trainer id " + trainerId + " exists");
        }
        return tr;
    }

}
