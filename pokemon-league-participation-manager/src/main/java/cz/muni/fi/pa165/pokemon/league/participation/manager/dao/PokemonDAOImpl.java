package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

/**
 * Implementation of Pokemon Data Access Object interface
 *
 * @author Michal Mokros 456442
 */
@Repository
public class PokemonDAOImpl implements PokemonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPokemon(@Valid Pokemon pokemon) {
        em.persist(pokemon);
    }

    @Override
    public void updatePokemon(@Valid Pokemon pokemon) {
        em.merge(pokemon);
    }

    @Override
    public void deletePokemon(@Valid Pokemon pokemon) {
        Pokemon attached = em.merge(pokemon);
        em.remove(attached);
    }

    @Override
    public Pokemon findPokemonById(Long id) {
        return em.find(Pokemon.class, id);
    }

    @Override
    public List<Pokemon> getAllPokemon() {
        return em.createQuery("SELECT pokemon FROM Pokemon pokemon", Pokemon.class)
                .getResultList();
    }
}
