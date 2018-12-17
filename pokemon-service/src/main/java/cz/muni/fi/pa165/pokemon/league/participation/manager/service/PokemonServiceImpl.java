package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.DAOExceptionWrapper;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Implementation of Pokemon service.
 *
 * @author Tibor Zauko 433531
 */
@Service
public class PokemonServiceImpl implements PokemonService {

    @Inject
    private PokemonDAO pokemonDao;

    @Override
    public void createPokemon(Pokemon pokemon) {
        DAOExceptionWrapper.withoutResult(() -> pokemonDao.createPokemon(pokemon), "createPokemon failed");
    }

    @Override
    public void renamePokemon(Pokemon pokemon, String newNickname) {
        pokemon.setNickname(newNickname);
        daoUpdatePokemon(pokemon);
    }

    @Override
    public void increasePokemonLevel(Pokemon pokemon, int to) throws LevelNotIncreasedException {
        if (to <= pokemon.getLevel()) {
            throw new LevelNotIncreasedException("The new level is lower or equal than the current level of the Pokemon");
        }
        pokemon.setLevel(to);
        daoUpdatePokemon(pokemon);
    }

    @Override
    public void evolvePokemon(Pokemon pokemon, PokemonSpecies evolveInto)
            throws InvalidPokemonEvolutionException {
        if (!pokemon.getSpecies().equals(evolveInto.getEvolvesFrom())) {
            throw new InvalidPokemonEvolutionException(String.format("%s cannot evolve into %s", pokemon.getSpecies(), evolveInto));
        }
        pokemon.setSpecies(evolveInto);
        daoUpdatePokemon(pokemon);
    }

    @Override
    public void releasePokemon(Pokemon pokemon) {
        DAOExceptionWrapper.withoutResult(() -> pokemonDao.deletePokemon(pokemon), "deletePokemon failed");
    }

    @Override
    public Pokemon findPokemonById(Long id) {
        return DAOExceptionWrapper.withResult(() -> pokemonDao.findPokemonById(id), "findPokemonById failed");
    }

    @Override
    public void giftPokemon(Pokemon pokemon, Trainer newTrainer) {
        pokemon.setTrainer(newTrainer);
        daoUpdatePokemon(pokemon);
    }

    @Override
    public List<Pokemon> getPokemonOfTrainer(Trainer trainer) {
        return DAOExceptionWrapper.withResult(()-> pokemonDao.getPokemonOfTrainer(trainer), "getPokemonOfTrainerFailed");
    }

    private void daoUpdatePokemon(Pokemon pokemon) {
        DAOExceptionWrapper.withoutResult(() -> pokemonDao.updatePokemon(pokemon), "updatePokemon failed");
    }
}
