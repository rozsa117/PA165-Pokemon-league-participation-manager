/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonDAOImpl implements PokemonDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPokemon(Pokemon pokemon) throws ServiceFailureException {
        em.persist(pokemon);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePokemon(Pokemon pokemon) throws ServiceFailureException {
    }

    @Override
    public void deletePokemon(Pokemon pokemon) throws ServiceFailureException {
        em.remove(pokemon);
    }

    @Override
    public Pokemon findPokemonById(Long id) throws ServiceFailureException {
        return em.find(Pokemon.class, id);
    }

    @Override
    public List<Pokemon> getAllPokemon() throws ServiceFailureException {
        return em.createQuery("Select p from Pokemon p", Pokemon.class).getResultList();
    }

}
