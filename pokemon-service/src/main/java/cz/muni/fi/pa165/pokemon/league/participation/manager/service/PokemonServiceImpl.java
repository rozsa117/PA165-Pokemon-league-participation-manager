package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.PokemonDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
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
    private PokemonDAO pkmnDao;

    @Override
    public void createPokemon(Pokemon pokemon) {
        pkmnDao.createPokemon(pokemon);
    }

    @Override
    public void renamePokemon(Pokemon pokemon, String newNickname) {
        pokemon.setNickname(newNickname);
        pkmnDao.updatePokemon(pokemon);
    }

    @Override
    public void increasePokemonLevel(Pokemon pokemon, int to) {
        pokemon.setLevel(to);
        pkmnDao.updatePokemon(pokemon);
    }

    @Override
    public void evolvePokemon(Pokemon pokemon, PokemonSpecies evolveInto)
            throws InvalidPokemonEvolutionException {
        if (!pokemon.getSpecies().equals(evolveInto.getEvolvesFrom())) {
            throw new InvalidPokemonEvolutionException(String.format("%s cannot evolve into %s", pokemon.getSpecies(), evolveInto));
        }
        pokemon.setSpecies(evolveInto);
        pkmnDao.updatePokemon(pokemon);
    }

    @Override
    public void releasePokemon(Pokemon pokemon) {
        pkmnDao.deletePokemon(pokemon);
    }

    @Override
    public Pokemon findPokemonById(Long id) {
        return pkmnDao.findPokemonById(id);
    }

    @Override
    public List<Pokemon> findPokemonOfTrainer(Trainer trainer) {
        return pkmnDao.findPokemonOfTrainer(trainer);
    }

    @Override
    public void giftPokemon(Pokemon pokemon, Trainer newTrainer) {
        pokemon.setTrainer(newTrainer);
        pkmnDao.updatePokemon(pokemon);
    }

}
