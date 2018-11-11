package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonSpeciesDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Implementation of PokemonSpecies service.
 *
 * @author Tibor Zauko 433531
 */
@Service
public class PokemonSpeciesServiceImpl implements PokemonSpeciesService {

    @Inject
    private PokemonSpeciesDAO speciesDao;

    @Override
    public void createPokemonSpecies(PokemonSpecies species) {
        speciesDao.createPokemonSpecies(species);
    }

    @Override
    public void changeTyping(PokemonSpecies species, PokemonType newPrimaryType, PokemonType newSecondaryType) {
        species.setPrimaryType(newPrimaryType);
        species.setSecondaryType(newSecondaryType);
        speciesDao.updatePokemonSpecies(species);
    }

    @Override
    public void changePreevolution(PokemonSpecies species, PokemonSpecies newPreevolution) {
        // TODO verify chain is short enough
        species.setEvolvesFrom(newPreevolution);
        speciesDao.updatePokemonSpecies(species);
    }

    @Override
    public void remove(PokemonSpecies species) {
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
        // TODO extend DAO with getAllEvolutions method
        return null;
    }

}
