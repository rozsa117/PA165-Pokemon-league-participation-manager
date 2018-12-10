package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.ApiUris;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangePreevolutionDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ChangeTypingDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.CircularEvolutionChainException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions.InvalidPreevolutionChangeException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This is the controller class of Pokemon Species to handle all the calls of rest api.
 * 
 * @author Tamás Rózsa 445653
 */
@RestController
@RequestMapping(ApiUris.POKEMON_SPECIES_URI)
public class PokemonSpeciesController {

    final static Logger logger = LoggerFactory.getLogger(PokemonSpeciesController.class);
    
    @Inject
    private PokemonSpeciesFacade pokemonSpeciesFacade;
    
    /**
     * With this command
     * curl -X POST -i -H "Content-Type: application/json" --data 
     * '{"speciesId":"4","primaryType":"FIRE","secondaryType":"DRAGON"}' 
     * http://localhost:8080/pa165/rest/pokemonSpecies/changeTyping
     * the type of the pokemon species is changed.
     * 
     * @param newTyping The new typing of the pokemon species.
     * @return The updated pokemon species.
     */
    @RequestMapping(
            value = "/changeTyping",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO changeTyping(@RequestBody ChangeTypingDTO newTyping) {
        logger.debug("rest changeTyping({})", newTyping);
        try {
            pokemonSpeciesFacade.changeTyping(newTyping);
            return pokemonSpeciesFacade.findPokemonSpeciesById(newTyping.getSpeciesId());
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
    
    /**
     * With command 
     * curl -X POST -i -H "Content-Type: application/json" --data 
     * '{"speciesId":"4","preevolutionId":"3"}' 
     * http://localhost:8080/pa165/rest/pokemonSpecies/changePreevolution
     * the preevolution of pokemon with species id is changed.
     * @param newPreevolution The requested preevolution change.
     * @return The updated pokemon species.
     */
    @RequestMapping(
            value = "/changePreevolution",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO changePreevolution(@RequestBody ChangePreevolutionDTO newPreevolution) {
        logger.debug("rest changePreevolution({})", newPreevolution);
        try {
            pokemonSpeciesFacade.changePreevolution(newPreevolution);
            return pokemonSpeciesFacade.findPokemonSpeciesById(newPreevolution.getSpeciesId());
        }
        catch(CircularEvolutionChainException | EvolutionChainTooLongException ex) {
            throw new InvalidPreevolutionChangeException(ex.getMessage(),ex);
        }
        catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
    
    /**
     * With the following command
     * curl -i -X DELETE http://localhost:8080/pa165/rest/pokemonSpecies/4
     * a pokemon species is deleted with given id.
     * 
     * @param id Id of the pokemon species to delete.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final void removePokemonSpecies(@PathVariable("id") long id) throws EntityIsUsedException {
        logger.debug("rest removePokemonSpecies({})", id);
        try {
            pokemonSpeciesFacade.removePokemonSpecies(id);
        }
        catch(EntityIsUsedException ex) {
//            throw new EntityUsedException();
              throw new EntityIsUsedException(ex.getMessage(),ex);
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
    
    /**
     * With the following command
     * curl -X POST -i -H "Content-Type: application/json" --data  
     * '{"speciesName":"Charmander","primaryType":"FIRE","secondaryType":"DRAGON",
     * "preevolutionId":"null"}' 
     * http://localhost:8080/pa165/rest/pokemonSpecies/create
     * a pokemon is created with the given attributes, evolves form takes an id 
     * as an argument.
     * 
     * @param species The species to create.
     * @return The created pokemon species.
     */
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO createPokemonSpecies(@RequestBody PokemonSpeciesCreateDTO species) {
        logger.debug("rest createPokemonSpecies({})", species);
        try {
            Long id = pokemonSpeciesFacade.createPokemonSpecies(species);
            return pokemonSpeciesFacade.findPokemonSpeciesById(id);
        }
        catch(Exception ex) {
            throw new InvalidParameterException(ex.getMessage(),ex);
        }
    }
    
    /**
     * With the following command
     * curl -i -X GET http://localhost:8080/pa165/rest/pokemonSpecies/1
     * return pokemon species with the given id.
     * 
     * @param id id of the pokemon species.
     * @return The found pokemon species.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final PokemonSpeciesDTO findPokemonSpeciesById(@PathVariable("id") long id) {
        logger.debug("rest findPokemonSpeciesById({})", id);
        try {
            return pokemonSpeciesFacade.findPokemonSpeciesById(id);
        }
        catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
    
    /**
     * The following command
     * curl -i -X GET http://localhost:8080/pa165/rest/pokemonSpecies
     * gets all pokemon species.
     * 
     * @return all pokemon species
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final List<PokemonSpeciesDTO> getAllPokemonSpecies() {
        logger.debug("rest getAllPokemonSpecies()");
        try {
            return pokemonSpeciesFacade.getAllPokemonSpecies();
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
    
    /**
     * With command 
     * curl -i -X GET http://localhost:8080/pa165/rest/pokemonSpecies/1/allEvolutions
     * all evolutions of pokemon species is returned.
     * 
     * @param speciesId The evolutions of pokemon species to find.
     * @return All evolutions of pokemon species.
     */
    @RequestMapping(
            value = "/{speciesId}/allEvolutions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public final List<PokemonSpeciesDTO> getAllEvolutionsOfPokemonSpecies(@PathVariable("speciesId") long speciesId) {
        logger.debug("rest getAllEvolutionsOfPokemonSpecies({})", speciesId);
        try {
            return pokemonSpeciesFacade.getAllEvolutionsOfPokemonSpecies(speciesId);
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage(),ex);
        }
    }
}
