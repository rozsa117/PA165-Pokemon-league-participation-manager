package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.ApiUris;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main controller of the rest application.
 * 
 * @author Tamás Rózsa 445653
 */
@RestController
public class MainController {
    
    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        Map<String,String> resourcesMap = new HashMap<>();
        
        resourcesMap.put("pokemon_species_uri", ApiUris.POKEMON_SPECIES_URI);
        
        return Collections.unmodifiableMap(resourcesMap);
    }
}
