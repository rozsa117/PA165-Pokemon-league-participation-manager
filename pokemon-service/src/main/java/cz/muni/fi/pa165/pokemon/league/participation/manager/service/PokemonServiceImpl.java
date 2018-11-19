package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        pokemonDao.createPokemon(pokemon);
    }

    @Override
    public void renamePokemon(Pokemon pokemon, String newNickname) {
        pokemon.setNickname(newNickname);
        pokemonDao.updatePokemon(pokemon);
    }

    @Override
    public void increasePokemonLevel(Pokemon pokemon, int to) throws LevelNotIncreasedException {
        if (to < pokemon.getLevel()) {
            throw new LevelNotIncreasedException("The new level is lower than the current level of the Pokemon");
        }
        pokemon.setLevel(to);
        pokemonDao.updatePokemon(pokemon);
    }

    @Override
    public void evolvePokemon(Pokemon pokemon, PokemonSpecies evolveInto)
            throws InvalidPokemonEvolutionException {
        if (!pokemon.getSpecies().equals(evolveInto.getEvolvesFrom())) {
            throw new InvalidPokemonEvolutionException(String.format("%s cannot evolve into %s", pokemon.getSpecies(), evolveInto));
        }
        pokemon.setSpecies(evolveInto);
        pokemonDao.updatePokemon(pokemon);
    }

    @Override
    public void releasePokemon(Pokemon pokemon) {
        pokemonDao.deletePokemon(pokemon);
    }

    @Override
    public Pokemon findPokemonById(Long id) {
        return pokemonDao.findPokemonById(id);
    }

    @Override
    public void giftPokemon(Pokemon pokemon, Trainer newTrainer) {
        pokemon.setTrainer(newTrainer);
        pokemonDao.updatePokemon(pokemon);
    }

    @Override
    public List<Pokemon> getPokemonOfTrainer(Trainer trainer) {
        return pokemonDao.getPokemonOfTrainer(trainer);
    }
}
