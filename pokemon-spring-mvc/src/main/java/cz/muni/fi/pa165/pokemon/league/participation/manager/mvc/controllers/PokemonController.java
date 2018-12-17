package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.EvolvePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GiftPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.LevelUpPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.PokemonSpeciesFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.security.TrainerIdContainingUser;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Pokemon controller.
 *
 * @author Jiří Medveď 38451
 */
@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonController.class);

    @Inject
    private PokemonFacade pokemonFacade;

    @Inject
    private TrainerFacade trainerFacade;

    @Inject
    private PokemonSpeciesFacade pokemonSpeciesFacade;

    @RequestMapping("/list")
    public String list(Model model, Authentication authentication) {
        List<PokemonDTO> pokemons = null;
        TrainerDTO currentTrainer = trainerFacade.getTrainerWithId(getCurrentTrainerId(authentication));
        model.addAttribute("currentTrainer", currentTrainer);
        try {
            pokemons = pokemonFacade.getPokemonOfTrainer(getCurrentTrainerId(authentication));
        } catch (NoSuchEntityException ex) {
            LOGGER.warn("Trainer got deleted in the meantime");
        }
        LOGGER.debug("Got Pokemons ", pokemons);
        model.addAttribute("pokemons", pokemons);
        return "pokemon/list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model, RedirectAttributes ra) {
        PokemonDTO pokemon = pokemonFacade.findPokemonById(id);
        if (pokemon == null) {
            ra.addAttribute("alert_warning", MessageFormat.format(I18n.getLocalizedMessageOrReturnKey("entity.does.not.exist"), I18n.getLocalizedMessageOrReturnKey("gym"), id));
            return "pokemon/list";
        }
        model.addAttribute("pokemon", pokemon);
        return "pokemon/detail";
    }

    /**
     * Get controller for evolve pokemon.
     *
     * @param id Id of pokemon to change evolution.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/evolve/{id}", method = RequestMethod.GET)
    public String evolve(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        LOGGER.debug("mvc GET evolve({})", id);

        PokemonDTO pokemon = pokemonFacade.findPokemonById(id);

        if (pokemon == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }

        if (!Objects.equals(pokemon.getTrainer().getId(), getCurrentTrainerId(authentication))) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }

        List<PokemonSpeciesDTO> speciesEvolveTo = pokemonSpeciesFacade.getAllEvolutionsOfPokemonSpecies(pokemon.getSpecies().getId());

        if (speciesEvolveTo.isEmpty()) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.cannot.evolve"));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }

        model.addAttribute("speciesEvolveTo", speciesEvolveTo);

        model.addAttribute("pokemon", pokemon);

        EvolvePokemonDTO pokemonToEvolve = new EvolvePokemonDTO();
        pokemonToEvolve.setId(id);

        model.addAttribute("pokemonToEvolve", pokemonToEvolve);

        return "pokemon/evolve";

    }

    /**
     * Post controller for evolve pokemon
     *
     * @param pokemon DTO of pokemon to evolve
     * @param id Id of pokemon species to change preevolution.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/evolve/{id}", method = RequestMethod.POST)
    public String evolve(@Valid @ModelAttribute("pokemonToEvolve") EvolvePokemonDTO pokemon,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication,
            @PathVariable long id) {

        LOGGER.debug("mvc POST evolve({})", id);
        pokemon.setRequestingTrainerId(getCurrentTrainerId(authentication));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemon/evolve";
        }
        try {
            pokemonFacade.evolvePokemon(pokemon);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        } catch (InvalidPokemonEvolutionException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.invalid.evolution"));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/evolve/" + pokemon.getId()).build().encode().toUriString();
        } catch (InsufficientRightsException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.successfully.updated"), messages.getString("pokemon")));
        return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
    }

    /**
     * Get controller for gift pokemon.
     *
     * @param id Id of pokemon to gift.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/gift/{id}", method = RequestMethod.GET)
    public String gift(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        LOGGER.debug("mvc GET gift({})", id);

        PokemonDTO pokemon = pokemonFacade.findPokemonById(id);

        if (pokemon == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }

        if (!Objects.equals(pokemon.getTrainer().getId(), getCurrentTrainerId(authentication))) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }
        model.addAttribute("pokemon", pokemon);

        GiftPokemonDTO pokemonToGift = new GiftPokemonDTO();
        pokemonToGift.setId(pokemon.getId());
        model.addAttribute("pokemonToGift", pokemonToGift);

        List<TrainerDTO> otherTrainers = trainerFacade.getAllTrainers();
        otherTrainers.removeIf(trainer -> trainer.getId() == getCurrentTrainerId(authentication));
        model.addAttribute("otherTrainers", otherTrainers);

        return "pokemon/gift";
    }

    /**
     * Post controller for evolve pokemon
     *
     * @param pokemon DTO of pokemon to gift
     * @param id Id of pokemon species to gift
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/gift/{id}", method = RequestMethod.POST)
    public String gift(@Valid @ModelAttribute("pokemonToGift") GiftPokemonDTO pokemon,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication,
            @PathVariable long id) {

        LOGGER.debug("mvc POST gift({})", id);
        pokemon.setRequestingTrainerId(getCurrentTrainerId(authentication));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemon/gift";
        }
        try {
            pokemonFacade.giftPokemon(pokemon);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        } catch (InsufficientRightsException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.successfully.updated"), messages.getString("pokemon")));
        return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
    }

    /**
     * Get controller for level up pokemon.
     *
     * @param id Id of pokemon to level up.
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.GET)
    public String levelUp(@PathVariable long id,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        LOGGER.debug("mvc GET level up({})", id);

        PokemonDTO pokemon = pokemonFacade.findPokemonById(id);

        if (pokemon == null) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }

        if (!Objects.equals(pokemon.getTrainer().getId(), getCurrentTrainerId(authentication))) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }
 
        model.addAttribute("pokemonToLevelUp", pokemon);

        return "pokemon/levelup";
    }

    /**
     * Post controller for level up pokemon
     *
     * @param pokemon DTO of pokemon to level up
     * @param id Id of pokemon species to level up
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.POST)
    public String levelUp(@Valid @ModelAttribute("pokemonLevelUp") LevelUpPokemonDTO pokemon,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication,
            @PathVariable long id) {

        LOGGER.debug("mvc POST level up({})", id);
        pokemon.setRequestingTrainerId(getCurrentTrainerId(authentication));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            return "pokemon/levelup";
        }
        try {
            pokemonFacade.levelUpPokemon(pokemon);
        } catch (NoSuchEntityException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", MessageFormat.format(messages.getString("entity.does.not.exist"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        } catch (InsufficientRightsException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", MessageFormat.format(messages.getString("not.authorized"), messages.getString("pokemon"), id));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        } catch (LevelNotIncreasedException ex) {
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_warning", messages.getString("pokemon.level.not.increased"));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/levelup/" + pokemon.getId())
                    .build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.successfully.updated"), messages.getString("pokemon")));
        return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
    }

    private Long getCurrentTrainerId(Authentication authentication) {
        return ((TrainerIdContainingUser) authentication.getPrincipal()).getTrainerId();
    }

    /**
     * Get controller for create a new pokemon.
     *
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        LOGGER.debug("mvc GET create");

        PokemonCreateDTO pokemon = new PokemonCreateDTO();
        pokemon.setLevel(1);

        model.addAttribute("pokemonCreate", pokemon);
        model.addAttribute("allSpecies",
                pokemonSpeciesFacade.getAllPokemonSpecies());

        return "pokemon/create";
    }

    /**
     * Post controller for create a mew pokemon
     *
     * @param pokemon DTO of pokemon to create
     * @return Path to jsp page.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("pokemonCreate") PokemonCreateDTO pokemon,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriComponentsBuilder,
            Authentication authentication) {

        LOGGER.debug("mvc POST create");
        Long id = null;
        pokemon.setCreatingTrainerId(getCurrentTrainerId(authentication));

        if (bindingResult.hasErrors()) {
            bindingResult.getGlobalErrors().forEach((ge) -> {
                ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
                LOGGER.trace("ObjectError: {}", ge);
                model.addAttribute("alert_warning", messages.getString(ge.getDefaultMessage()));
            });
            bindingResult.getFieldErrors().forEach((fe) -> {
                model.addAttribute(fe.getField() + "_error", true);
            });
            model.addAttribute("allSpecies",
                    pokemonSpeciesFacade.getAllPokemonSpecies());
            return "pokemon/create";
        }
        try {
            pokemonFacade.createPokemon(pokemon);
        } catch (NoSuchEntityException ex) {
            LOGGER.error("Pokemon create failed", ex);
            ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("alert_danger", messages.getString("something.went.wrong"));
            return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
        }
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("alert_success", MessageFormat.format(messages.getString("entity.created.successfully"), messages.getString("pokemon"), id));
        return "redirect:" + uriComponentsBuilder.path("/pokemon/list").build().encode().toUriString();
    }
}
