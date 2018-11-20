package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonSpeciesService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/**
 * Implementation of PokemonSpeciesFacade.
 *
 * @author Tibor Zauko 433531
 */
@Service
@Transactional
public class PokemonSpeciesFacadeImpl implements PokemonSpeciesFacade {

    @Inject
    private PokemonSpeciesService pss;
    
    @Inject
    private BeanMappingService bms;
    
    @Override
    public Long createPokemonSpecies(PokemonSpeciesCreateDTO species) throws EvolutionChainTooLongException {
        PokemonSpecies sp = new PokemonSpecies();
        sp.setSpeciesName(species.getSpeciesName());
        sp.setPrimaryType(species.getPrimaryType());
        sp.setSecondaryType(species.getSecondaryType());
        sp.setEvolvesFrom(species.getPreevolutionId() == null ? null : pss.findPokemonSpeciesById(species.getPreevolutionId()));
        pss.createPokemonSpecies(sp);
        return sp.getId();
    }

    @Override
    public PokemonSpeciesDTO findPokemonSpeciesById(@NotNull Long id) {
        PokemonSpecies sp = pss.findPokemonSpeciesById(id);
        return sp == null ? null : bms.mapTo(sp, PokemonSpeciesDTO.class);
    }

    @Override
    public List<PokemonSpeciesDTO> getAllPokemonSpecies() {
        return bms.mapTo(pss.getAllPokemonSpecies(), PokemonSpeciesDTO.class);
    }

    @Override
    public List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(Long speciesId) {
        return bms.mapTo(
                pss.getAllEvolutionsOfPokemonSpecies(
                        speciesId == null ? null : pss.findPokemonSpeciesById(speciesId))
                , PokemonSpeciesDTO.class);
    }

    @Override
    public void changeTyping(ChangeTypingDTO newTyping) throws NoSuchEntityException {
        PokemonSpecies sp = getNonNullSpecies(newTyping.getSpeciesId());
        if (sp != null) {
            pss.changeTyping(sp, newTyping.getPrimaryType(), newTyping.getSecondaryType());
        }
    }

    @Override
    public void changePreevolution(ChangePreevolutionDTO newPreevolution)
            throws EvolutionChainTooLongException, CircularEvolutionChainException, NoSuchEntityException {
        PokemonSpecies sp = getNonNullSpecies(newPreevolution.getSpeciesId());
        if (sp != null) {
            pss.changePreevolution(sp, pss.findPokemonSpeciesById(newPreevolution.getPreevolutionId()));
        }
    }

    @Override
    public void removePokemonSpecies(@NotNull Long speciesId) throws EntityIsUsedException {
        PokemonSpecies sp = pss.findPokemonSpeciesById(speciesId);
        if (sp != null) {
            pss.remove(sp);
        }
    }
    
    private PokemonSpecies getNonNullSpecies(Long speciesId) throws NoSuchEntityException {
        PokemonSpecies sp = pss.findPokemonSpeciesById(speciesId);
        if (sp == null) {
            throw new NoSuchEntityException("No species of id " +speciesId+ " exists");
        }
        return sp;
    }
    
}
