package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonSpeciesDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of PokemonSpecies service.
 *
 * @author Tibor Zauko 433531
 */
@Service
public class PokemonSpeciesServiceImpl implements PokemonSpeciesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonSpeciesServiceImpl.class);
    
    @Inject
    private PokemonSpeciesDAO speciesDao;
    
    @Inject
    private PokemonDAO pkmnDao;

    @Override
    public void createPokemonSpecies(PokemonSpecies species) throws EvolutionChainTooLongException {
        if (species.getEvolvesFrom() != null && !evolutionChainNotLongerThan3(species.getEvolvesFrom(), species, true)) {
            LOGGER.debug("{} as preevolution of {} would make too long evolution chain", species.getEvolvesFrom(), species);
            throw new EvolutionChainTooLongException(String.format("Can't create %s as evolution of %s", species, species.getEvolvesFrom()));
        }
        speciesDao.createPokemonSpecies(species);
    }

    @Override
    public void changeTyping(PokemonSpecies species, PokemonType newPrimaryType, PokemonType newSecondaryType) {
        species.setPrimaryType(newPrimaryType);
        species.setSecondaryType(newSecondaryType);
        speciesDao.updatePokemonSpecies(species);
    }

    @Override
    public void changePreevolution(PokemonSpecies species, PokemonSpecies newPreevolution)
            throws EvolutionChainTooLongException, CircularEvolutionChainException {
        checkEvolutionChainValidity(newPreevolution, species);
        species.setEvolvesFrom(newPreevolution);
        speciesDao.updatePokemonSpecies(species);
    }

    @Override
    public void remove(PokemonSpecies species) throws EntityIsUsedException {
        if (!pkmnDao.getAllPokemonOfSpecies(species).isEmpty()
                || !getAllEvolutionsOfPokemonSpecies(species).isEmpty()) {
            throw new EntityIsUsedException("The species is still used in a Pokemon or as a preevolution.");
        }
        speciesDao.deletePokemonSpecies(species);
    }

    @Override
    public PokemonSpecies findPokemonSpeciesById(Long id) {
        return speciesDao.findPokemonSpeciesById(id);
    }

    @Override
    public List<PokemonSpecies> getAllPokemonSpecies() {
        return speciesDao.getAllPokemonSpecies();
    }

    @Override
    public List<PokemonSpecies> getAllEvolutionsOfPokemonSpecies(PokemonSpecies species) {
        return speciesDao.getAllEvolutionsOfPokemonSpecies(species);
    }

    private boolean hasMoreEvolutionaryStagesThan(PokemonSpecies species, int i) {
        List<PokemonSpecies> evolutions = speciesDao.getAllEvolutionsOfPokemonSpecies(species);
        if (i > 0) {
            return evolutions.stream().allMatch(
                    (PokemonSpecies sp) -> hasMoreEvolutionaryStagesThan(sp, i - 1));
        } else if (i == 0) {
            return !evolutions.isEmpty();
        } else {
            return true;
        }
    }

    private void checkEvolutionChainValidity(PokemonSpecies newPreevolution, PokemonSpecies species) 
            throws EvolutionChainTooLongException, CircularEvolutionChainException {
        if (!evolutionChainNotLongerThan3(newPreevolution, species, false)) {
            LOGGER.debug("Joining {} as preevolution of {} would create too long evolution chain", newPreevolution, species);
            throw new EvolutionChainTooLongException(String.format("Can't add %s as preevolution of %s", newPreevolution, species));
        }
        if (!evolutionChainNotCircular(newPreevolution, species)) {
            LOGGER.debug("Joining {} as preevolution of {} would create circular evolution chain", newPreevolution, species);
            throw new CircularEvolutionChainException(String.format("Can't add %s as preevolution of %s", newPreevolution, species));
        }
    }

    private boolean evolutionChainNotCircular(PokemonSpecies newPreevolution, PokemonSpecies species) throws CircularEvolutionChainException {
        PokemonSpecies s = newPreevolution;
        while (s != null && !s.equals(species)) {
            s = s.getEvolvesFrom();
        }
        return s == null;
    }

    
    private boolean evolutionChainNotLongerThan3(PokemonSpecies newPreevolution, PokemonSpecies species, boolean onlyCheckPreevolutionChain)
            throws EvolutionChainTooLongException {
        int chainLen = 1;
        PokemonSpecies s = newPreevolution;
        while (s != null) {
            chainLen += 1;
            s = s.getEvolvesFrom();
        }
        if (onlyCheckPreevolutionChain && chainLen <= 3) {
            return true;
        }
        return !(chainLen > 3 || hasMoreEvolutionaryStagesThan(species, 3 - chainLen));
    }

}
