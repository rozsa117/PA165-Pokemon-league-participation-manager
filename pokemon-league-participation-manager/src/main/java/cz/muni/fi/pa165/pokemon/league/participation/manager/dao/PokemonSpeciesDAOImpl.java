package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Interface of data access objects for PokemonSpecies entity class
 * implementation.
 *
 * @author Jiří Medveď 38451
 */
@Repository
public class PokemonSpeciesDAOImpl implements PokemonSpeciesDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPokemonSpecies(PokemonSpecies pokemonSpecies) {
        em.persist(pokemonSpecies);
    }

    @Override
    public void deletePokemonSpecies(PokemonSpecies pokemonSpecies) {
        pokemonSpecies = em.merge(pokemonSpecies);
        em.remove(pokemonSpecies);
    }

    @Override
    public PokemonSpecies findPokemonSpeciesById(Long id) {
        return em.find(PokemonSpecies.class, id);
    }

    @Override
    public List<PokemonSpecies> getAllPokemonSpecies() {
        return em.createQuery("select ps from PokemonSpecies ps", PokemonSpecies.class).getResultList();
    }

    @Override
    public void updatePokemonSpecies(PokemonSpecies pokemonSpecies) {
        em.merge(pokemonSpecies);
    }

}
