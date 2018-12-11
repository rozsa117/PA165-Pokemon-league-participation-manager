package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.GymFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Gym controller.
 * 
 * @author Tibor Zauko 433531
 */
@Controller
@RequestMapping("/gym")
public class GymController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GymController.class);
    
    @Inject
    private GymFacade gymFacade;
    
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("allGyms", gymFacade.getAllGyms());
        return "gym/list";
    }
    
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model, RedirectAttributes ra) {
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            ra.addAttribute("alert_warning", "The requested gym doesnt exist");
            return "gym/list";
        }
        model.addAttribute("pokemonSpecies", gym);
        return "gym/detail";
    }
    
}
