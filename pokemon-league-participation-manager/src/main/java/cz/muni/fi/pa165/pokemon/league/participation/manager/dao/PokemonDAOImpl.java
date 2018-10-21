package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of Pokemon Data Access Object interface
 */
@Repository
public class PokemonDAOImpl implements PokemonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPokemon(Pokemon pokemon) throws ServiceFailureException {
        em.persist(pokemon);
    }

    @Override
    public void updatePokemon(Pokemon pokemon) throws ServiceFailureException {
        em.merge(pokemon);
    }

    @Override
    public void deletePokemon(Pokemon pokemon) throws ServiceFailureException {
        Pokemon attached = em.merge(pokemon);
        em.remove(attached);
    }

    @Override
    public Pokemon findPokemonById(Long id) throws ServiceFailureException {
        return em.find(Pokemon.class, id);
    }

    @Override
    public List<Pokemon> getAllPokemon() throws ServiceFailureException {
        return em.createQuery("SELECT pokemon FROM Pokemon pokemon", Pokemon.class)
                .getResultList();
    }
}
