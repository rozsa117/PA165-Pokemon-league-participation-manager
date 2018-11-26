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
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
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
    private PokemonSpeciesService pokemonSpeciesService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createPokemonSpecies(PokemonSpeciesCreateDTO species) throws EvolutionChainTooLongException {
        PokemonSpecies speciesEntity = beanMappingService.mapTo(species, PokemonSpecies.class);
        speciesEntity.setEvolvesFrom(
                species.getPreevolutionId() == null
                ? null
                : pokemonSpeciesService.findPokemonSpeciesById(species.getPreevolutionId())
        );
        pokemonSpeciesService.createPokemonSpecies(speciesEntity);
        return speciesEntity.getId();
    }

    @Override
    public PokemonSpeciesDTO findPokemonSpeciesById(@NotNull Long id) {
        PokemonSpecies species = pokemonSpeciesService.findPokemonSpeciesById(id);
        return species == null ? null : beanMappingService.mapTo(species, PokemonSpeciesDTO.class);
    }

    @Override
    public List<PokemonSpeciesDTO> getAllPokemonSpecies() {
        return beanMappingService.mapTo(pokemonSpeciesService.getAllPokemonSpecies(), PokemonSpeciesDTO.class);
    }

    @Override
    public List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(Long speciesId) {
        return beanMappingService.mapTo(
                pokemonSpeciesService.getAllEvolutionsOfPokemonSpecies(
                        speciesId == null ? null : pokemonSpeciesService.findPokemonSpeciesById(speciesId)),
                 PokemonSpeciesDTO.class);
    }

    @Override
    public void changeTyping(ChangeTypingDTO newTyping) throws NoSuchEntityException {
        PokemonSpecies species = getNonNullSpecies(newTyping.getSpeciesId());
        pokemonSpeciesService.changeTyping(species, newTyping.getPrimaryType(), newTyping.getSecondaryType());
    }

    @Override
    public void changePreevolution(ChangePreevolutionDTO newPreevolution)
            throws EvolutionChainTooLongException, CircularEvolutionChainException, NoSuchEntityException {
        PokemonSpecies species = getNonNullSpecies(newPreevolution.getSpeciesId());
            pokemonSpeciesService.changePreevolution(
                    species,
                    pokemonSpeciesService.findPokemonSpeciesById(newPreevolution.getPreevolutionId())
            );
    }

    @Override
    public void removePokemonSpecies(@NotNull Long speciesId) throws EntityIsUsedException {
        PokemonSpecies species = pokemonSpeciesService.findPokemonSpeciesById(speciesId);
        if (species != null) {
            pokemonSpeciesService.remove(species);
        }
    }

    private PokemonSpecies getNonNullSpecies(Long speciesId) throws NoSuchEntityException {
        PokemonSpecies species = pokemonSpeciesService.findPokemonSpeciesById(speciesId);
        if (species == null) {
            throw new NoSuchEntityException("No species of id " + speciesId + " exists");
        }
        return species;
    }

}
