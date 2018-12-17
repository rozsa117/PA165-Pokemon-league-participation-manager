package cz.muni.fi.pa165.pokemon.league.participation.manager;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EvolutionChainTooLongException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoAdministratorException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BadgeService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonSpeciesService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for interface SampleDataLoadingFacade.
 * 
 * @author Tamás Rózsa 445653
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);
    
    @Inject
    PokemonService pokemonService;
    
    @Inject
    PokemonSpeciesService pokemonSpeciesService;
    
    @Inject
    TrainerService trainerService;
    
    @Inject
    BadgeService badgeService;
    
    @Inject
    GymService gymService;
    
    @Override
    public void loadData() throws EvolutionChainTooLongException, NoAdministratorException, EntityIsUsedException {
        log.debug("Loading sample data");
        
        PokemonSpecies pikachuSpecies = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.ELECTRIC)
                .speciesName("Pikachu")
                .build();
        
        PokemonSpecies raichuSpecies = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.ELECTRIC)
                .speciesName("Raichu")
                .evolvesFrom(pikachuSpecies)
                .build();
        
        PokemonSpecies onixSpecies = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.GROUND)
                .secondaryType(PokemonType.ROCK)
                .speciesName("Onix")
                .build();
        
        PokemonSpecies staryuSpecies = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.WATER)
                .speciesName("Staryu")
                .build();
        
        PokemonSpecies bulbassaurSpecies = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.GRASS)
                .speciesName("Bulbasaur")
                .build();
        
        pokemonSpeciesService.createPokemonSpecies(pikachuSpecies);
        pokemonSpeciesService.createPokemonSpecies(raichuSpecies);
        pokemonSpeciesService.createPokemonSpecies(onixSpecies);
        pokemonSpeciesService.createPokemonSpecies(staryuSpecies);
        pokemonSpeciesService.createPokemonSpecies(bulbassaurSpecies);
        
        Trainer ashTrainer = new TrainerBuilder()
                .name("Ash")
                .surname("Ketchum")
                .born(LocalDate.of(1998, Month.JANUARY, 11))
                .isAdmin(true)
                .userName("ash")
                .build();
        
        Trainer brockTrainer = new TrainerBuilder()
                .name("Brock")
                .surname("Sleepyeye")
                .born(LocalDate.of(1999, Month.NOVEMBER, 27))
                .isAdmin(false)
                .userName("brock")
                .build();
        
        Trainer mistyTrainer = new TrainerBuilder()
                .name("Misty")
                .surname("Yawa")
                .born(LocalDate.of(1999, Month.JUNE, 10))
                .isAdmin(false)
                .userName("misty")
                .build();
        
        trainerService.createTrainer(ashTrainer, "ash123");
        trainerService.createTrainer(brockTrainer, "brock123");
        trainerService.createTrainer(mistyTrainer, "misty123");
        
        Pokemon pikachuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2017, Month.MARCH, 7, 0, 0))
                .level(50)
                .nickname("Pika")
                .pokemonSpecies(pikachuSpecies)
                .trainer(ashTrainer)
                .build();
        
        Pokemon raichuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2018, Month.APRIL, 8, 0, 0))
                .level(100)
                .nickname("Raichu")
                .pokemonSpecies(raichuSpecies)
                .trainer(ashTrainer)
                .build();
        
        Pokemon onixPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2016, Month.JULY, 20, 0, 0))
                .level(75)
                .nickname("Onix")
                .pokemonSpecies(onixSpecies)
                .trainer(brockTrainer)
                .build();
        
        Pokemon staryuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2017, Month.OCTOBER, 15, 0, 0))
                .level(100)
                .nickname("Staryu")
                .pokemonSpecies(staryuSpecies)
                .trainer(mistyTrainer)
                .build();
        
        pokemonService.createPokemon(pikachuPokemon);
        pokemonService.createPokemon(raichuPokemon);
        pokemonService.createPokemon(onixPokemon);
        pokemonService.createPokemon(staryuPokemon);
        
        Gym vermilionGym = new GymBuilder()
                .gymLeader(ashTrainer)
                .location("Vermilion")
                .type(PokemonType.ELECTRIC)
                .build();
        
        Gym pewterGym = new GymBuilder()
                .gymLeader(brockTrainer)
                .location("Pewter")
                .type(PokemonType.GROUND)
                .build();
        
        gymService.createGym(vermilionGym);
        gymService.createGym(pewterGym);
        
        Badge badge = new BadgeBuilder()
                .date(LocalDate.of(2018, Month.OCTOBER, 17))
                .gym(pewterGym)
                .trainer(ashTrainer)
                .status(ChallengeStatus.WON)
                .build();
        
        badgeService.createBadge(badge);
    }
    
}
