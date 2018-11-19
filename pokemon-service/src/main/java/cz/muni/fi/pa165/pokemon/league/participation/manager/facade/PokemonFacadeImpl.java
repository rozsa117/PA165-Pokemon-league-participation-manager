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
    
    @Inject PokemonSpeciesService pss;
    
    @Inject
    private BeanMappingService bms;
    
    @Override
    public Long createPokemon(PokemonCreateDTO pokemon) {
        Pokemon pkmn = new Pokemon();
        pkmn.setDateTimeOfCapture(LocalDateTime.now());
        pkmn.setLevel(pokemon.getLevel());
        pkmn.setNickname(pokemon.getNickname());
        pkmn.setTrainer(ts.findTrainerById(pokemon.getCreatingTrainerId()));
        ps.createPokemon(pkmn);
        return pkmn.getId();
    }

    @Override
    public PokemonDTO findPokemonById(@NotNull Long id) {
        return bms.mapTo(ps.findPokemonById(id), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonOfTrainer(@NotNull Long trainerId) {
        Trainer tr = ts.findTrainerById(trainerId); 
        return tr == null ? new ArrayList<>() : bms.mapTo(tr.getPokemons(), PokemonDTO.class);
    }

    @Override
    public void releasePokemon(ReleasePokemonDTO releasedPokemon) {
        Trainer tr = ts.findTrainerById(releasedPokemon.getRequestingTrainerId());
        Pokemon pkmn = ps.findPokemonById(releasedPokemon.getPokemonId());
        if (tr != null && pkmn != null && pkmn.getTrainer().equals(tr)) {
            ps.releasePokemon(pkmn);
        }
    }

    @Override
    public void renamePokemon(RenamePokemonDTO newNickname) {
        Trainer tr = ts.findTrainerById(newNickname.getRequestingTrainerId());
        Pokemon pkmn = ps.findPokemonById(newNickname.getPokemonId());
        if (tr != null && pkmn != null && pkmn.getTrainer().equals(tr)) {
            ps.renamePokemon(pkmn, newNickname.getNewNickname());
        }        
    }

    @Override
    public void levelUpPokemon(LevelUpPokemonDTO newLevel) throws LevelNotIncreasedException {
        Trainer tr = ts.findTrainerById(newLevel.getRequestingTrainerId());
        Pokemon pkmn = ps.findPokemonById(newLevel.getPokemonId());
        if (tr != null && pkmn != null && pkmn.getTrainer().equals(tr)) {
            ps.increasePokemonLevel(pkmn, newLevel.getNewLevel());
        } 
    }

    @Override
    public void evolvePokemon(EvolvePokemonDTO chosenEvolution) throws InvalidPokemonEvolutionException {
        Trainer tr = ts.findTrainerById(chosenEvolution.getRequestingTrainerId());
        PokemonSpecies sp = pss.findPokemonSpeciesById(chosenEvolution.getNewSpeciesId());
        Pokemon pkmn = ps.findPokemonById(chosenEvolution.getPokemonId());
        if (tr != null && pkmn != null && sp != null && pkmn.getTrainer().equals(tr)) {
            ps.evolvePokemon(pkmn, sp);
        }
    }

    @Override
    public void giftPokemon(GiftPokemonDTO gift) {
        Trainer tr = ts.findTrainerById(gift.getRequestingTrainerId());
        Trainer giftedTr = ts.findTrainerById(gift.getGiftedTrainerId());
        Pokemon pkmn = ps.findPokemonById(gift.getPokemonId());
        if (tr != null && pkmn != null && giftedTr != null && pkmn.getTrainer().equals(tr)) {
            ps.giftPokemon(pkmn, giftedTr);
        } 
    }
    
}
