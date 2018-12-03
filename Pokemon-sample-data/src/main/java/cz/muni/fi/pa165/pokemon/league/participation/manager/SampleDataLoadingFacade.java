package cz.muni.fi.pa165.pokemon.league.participation.manager;

import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;

/**
 * Interface for sample data loading.
 * 
 * @author Tamás Rózsa 445653
 */
public interface SampleDataLoadingFacade {
    
    /**
     * Basically the only public method, that loads all the sample data for the application.
     */
    public void loadData() throws EvolutionChainTooLongException, NoAdministratorException, EntityIsUsedException;
    
}
