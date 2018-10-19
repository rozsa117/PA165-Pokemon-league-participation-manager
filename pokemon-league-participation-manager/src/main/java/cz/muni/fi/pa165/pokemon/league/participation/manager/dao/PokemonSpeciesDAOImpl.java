/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PokemonSpeciesDAOImpl implements PokemonSpeciesDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException {
        em.persist(pokemonSpecies);
    }

    @Override
    public void updatePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePokemonSpecies(PokemonSpecies pokemonSpecies) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PokemonSpecies findPokemonSpeciesById(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PokemonSpecies> getAllPokemonSpecies() throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
