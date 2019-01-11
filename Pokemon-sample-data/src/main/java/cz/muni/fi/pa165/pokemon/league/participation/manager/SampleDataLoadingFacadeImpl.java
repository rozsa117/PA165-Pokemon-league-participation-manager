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
        PokemonSpeciesBuilder familyBuilder = new PokemonSpeciesBuilder();
        PokemonSpecies bulbasaur = familyBuilder
                .primaryType(PokemonType.GRASS)
                .secondaryType(PokemonType.POISON)
                .speciesName("Bulbasaur")
                .build();
        PokemonSpecies ivysaur = familyBuilder
                .speciesName("Ivysaur")
                .evolvesFrom(bulbasaur)
                .build();
        PokemonSpecies venusaur = familyBuilder
                .speciesName("Venusaur")
                .evolvesFrom(ivysaur)
                .build();
        
        familyBuilder = new PokemonSpeciesBuilder();
        PokemonSpecies charmander = familyBuilder
                .primaryType(PokemonType.FIRE)
                .speciesName("Charmander")
                .build();
        PokemonSpecies charmeleon = familyBuilder
                .speciesName("Charmeleon")
                .evolvesFrom(charmander)
                .build();
        PokemonSpecies charizard = familyBuilder
                .secondaryType(PokemonType.FLYING)
                .speciesName("Charizard")
                .evolvesFrom(charmeleon)
                .build();
        
        familyBuilder = new PokemonSpeciesBuilder();
        PokemonSpecies squirtle = familyBuilder
                .primaryType(PokemonType.WATER)
                .speciesName("Squirtle")
                .build();
        PokemonSpecies wartortle = familyBuilder
                .speciesName("Wartortle")
                .evolvesFrom(squirtle)
                .build();
        PokemonSpecies blastoise = familyBuilder
                .speciesName("Blastoise")
                .evolvesFrom(wartortle)
                .build();
        
        PokemonSpecies pikachu = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.ELECTRIC)
                .speciesName("Pikachu")
                .build();
        
        PokemonSpecies raichu = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.ELECTRIC)
                .speciesName("Raichu")
                .evolvesFrom(pikachu)
                .build();
        
        PokemonSpecies onix = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.GROUND)
                .secondaryType(PokemonType.ROCK)
                .speciesName("Onix")
                .build();
        
        PokemonSpecies staryu = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.WATER)
                .speciesName("Staryu")
                .build();
        
        PokemonSpecies wingull = new PokemonSpeciesBuilder()
                .primaryType(PokemonType.WATER)
                .secondaryType(PokemonType.FLYING)
                .speciesName("Wingull")
                .build();
        
        pokemonSpeciesService.createPokemonSpecies(bulbasaur);
        pokemonSpeciesService.createPokemonSpecies(ivysaur);
        pokemonSpeciesService.createPokemonSpecies(venusaur);
        pokemonSpeciesService.createPokemonSpecies(charmander);
        pokemonSpeciesService.createPokemonSpecies(charmeleon);
        pokemonSpeciesService.createPokemonSpecies(charizard);
        pokemonSpeciesService.createPokemonSpecies(squirtle);
        pokemonSpeciesService.createPokemonSpecies(wartortle);
        pokemonSpeciesService.createPokemonSpecies(blastoise);
        pokemonSpeciesService.createPokemonSpecies(pikachu);
        pokemonSpeciesService.createPokemonSpecies(raichu);
        pokemonSpeciesService.createPokemonSpecies(onix);
        pokemonSpeciesService.createPokemonSpecies(staryu);
        pokemonSpeciesService.createPokemonSpecies(wingull);
        
        Trainer mrGoodshow = new TrainerBuilder()
                .name("John")
                .surname("Goodshow")
                .born(LocalDate.of(1937, Month.MARCH, 5))
                .isAdmin(true)
                .userName("admin1")
                .build();
        
        Trainer ash = new TrainerBuilder()
                .name("Ash")
                .surname("Ketchum")
                .born(LocalDate.of(1998, Month.JANUARY, 11))
                .isAdmin(false)
                .userName("ash")
                .build();
        
        Trainer mrBriney = new TrainerBuilder()
                .name("Quentin")
                .surname("Briney")
                .born(LocalDate.of(1967, Month.SEPTEMBER, 23))
                .isAdmin(false)
                .userName("briney")
                .build();

        Trainer brock = new TrainerBuilder()
                .name("Brock")
                .surname("Takeshi")
                .born(LocalDate.of(1999, Month.NOVEMBER, 27))
                .isAdmin(false)
                .userName("brock")
                .build();
        
        Trainer misty = new TrainerBuilder()
                .name("Misty")
                .surname("Yawa")
                .born(LocalDate.of(1999, Month.JUNE, 10))
                .isAdmin(false)
                .userName("misty")
                .build();
        
        trainerService.createTrainer(mrGoodshow, "admin1123");
        trainerService.createTrainer(ash, "ash123");
        trainerService.createTrainer(brock, "brock123");
        trainerService.createTrainer(misty, "misty123");
        
        Pokemon pikachuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2017, Month.MARCH, 7, 0, 0))
                .level(20)
                .nickname("Pikachu")
                .pokemonSpecies(pikachu)
                .trainer(ash)
                .build();
        
        Pokemon raichuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2018, Month.APRIL, 8, 0, 0))
                .level(18)
                .nickname("Bulbasaur")
                .pokemonSpecies(bulbasaur)
                .trainer(ash)
                .build();
        
        Pokemon onixPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2016, Month.JULY, 20, 0, 0))
                .level(50)
                .nickname("Onix")
                .pokemonSpecies(onix)
                .trainer(brock)
                .build();
        
        Pokemon staryuPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2017, Month.OCTOBER, 15, 0, 0))
                .level(45)
                .nickname("Staryu")
                .pokemonSpecies(staryu)
                .trainer(misty)
                .build();
        
        Pokemon wingullPokemon = new PokemonBuilder()
                .dateTimeOfCapture(LocalDateTime.of(2001, Month.OCTOBER, 15, 0, 0))
                .level(24)
                .nickname("Peeko")
                .pokemonSpecies(wingull)
                .trainer(mrBriney)
                .build();
        
        pokemonService.createPokemon(pikachuPokemon);
        pokemonService.createPokemon(raichuPokemon);
        pokemonService.createPokemon(onixPokemon);
        pokemonService.createPokemon(staryuPokemon);
        
        Gym ceruleanGym = new GymBuilder()
                .gymLeader(misty)
                .location("Cerulean")
                .type(PokemonType.WATER)
                .build();
        
        Gym pewterGym = new GymBuilder()
                .gymLeader(brock)
                .location("Pewter")
                .type(PokemonType.ROCK)
                .build();
        
        gymService.createGym(pewterGym);
        gymService.createGym(ceruleanGym);
        
        Badge badge = new BadgeBuilder()
                .date(LocalDate.of(2018, Month.OCTOBER, 17))
                .gym(pewterGym)
                .trainer(ash)
                .status(ChallengeStatus.WON)
                .build();
        
        badgeService.createBadge(badge);
    }
    
}
