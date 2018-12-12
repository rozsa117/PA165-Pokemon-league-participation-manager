package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.BadgeBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.GymBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymAndBadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.BadgeDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.GymDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.PokemonDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.PokemonSpeciesDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.TrainerDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.league.participation.manager.utils.GymAndBadge;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import javax.inject.Inject;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests verifying that BeanMappingService maps between entities and DTOs correctly.
 *
 * @author Tibor Zauko 433531
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringRunner.class)
public class BeanMappingServiceTest {

    @Inject
    private BeanMappingService bms;

    private static Trainer sourceTrainer;
    private static Trainer expectedTrainer;
    private static Gym sourceGym;
    private static Gym expectedGym;
    private static Badge sourceBadge;
    private static Badge expectedBadge;
    private static PokemonSpecies sourceSpecies;
    private static PokemonSpecies expectedSpecies;
    private static PokemonSpecies nestedSpecies;
    private static Pokemon sourcePokemon;
    private static Pokemon expectedPokemon;
    private static GymAndBadge sourceGymAndBadge;

    private static TrainerCreateDTO sourceTrainerCreateDTO;
    private static TrainerDTO expectedTrainerDTO;
    private static GymCreateDTO sourceGymCreateDTO;
    private static GymDTO expectedGymDTO;
    private static BadgeCreateDTO sourceBadgeCreateDTO;
    private static BadgeDTO expectedBadgeDTO;
    private static PokemonSpeciesCreateDTO sourceSpeciesCreateDTO;
    private static PokemonSpeciesDTO expectedSpeciesDTO;
    private static PokemonSpeciesDTO nestedSpeciesDTO;
    private static PokemonCreateDTO sourcePokemonCreateDTO;
    private static PokemonDTO expectedPokemonDTO;
    private static GymAndBadgeDTO expectedGymAndBadgeDTO;

    @BeforeClass
    public static void setUpClass() {
        TrainerBuilder tb = new TrainerBuilder()
                .born(LocalDate.of(1990, Month.MARCH, 15))
                .id(null)
                .isAdmin(true)
                .name("Mister")
                .passwordHash(null)
                .surname("Goodshow")
                .userName("adminforever");
        expectedTrainer = tb.build();
        tb.id(1L);
        sourceTrainer = tb.build();

        GymBuilder gb = new GymBuilder()
                .gymLeader(null)
                .id(null)
                .location("Indigo Plateau")
                .type(PokemonType.FAIRY);
        expectedGym = gb.build();
        gb.id(2L).gymLeader(sourceTrainer);
        sourceGym = gb.build();

        nestedSpecies = new PokemonSpeciesBuilder()
                .evolvesFrom(null)
                .id(3L)
                .primaryType(PokemonType.FIRE)
                .secondaryType(null)
                .speciesName("Charmeleon")
                .build();
        PokemonSpeciesBuilder psb = new PokemonSpeciesBuilder()
                .evolvesFrom(null)
                .id(null)
                .primaryType(PokemonType.FIRE)
                .secondaryType(PokemonType.FLYING)
                .speciesName("Charizard");
        expectedSpecies = psb.build();
        psb.id(4L).evolvesFrom(nestedSpecies);
        sourceSpecies = psb.build();

        PokemonBuilder pb = new PokemonBuilder()
                .dateTimeOfCapture(null)
                .id(null)
                .level(33)
                .nickname("Charcoal")
                .pokemonSpecies(null)
                .trainer(null);
        expectedPokemon = pb.build();
        pb.id(5L).dateTimeOfCapture(LocalDateTime.now()).pokemonSpecies(sourceSpecies).trainer(sourceTrainer);
        sourcePokemon = pb.build();

        BadgeBuilder bb = new BadgeBuilder()
                .date(null)
                .gym(null)
                .id(null)
                .status(null)
                .trainer(null);
        expectedBadge = bb.build();
        bb.id(6L).date(LocalDate.of(2018, Month.AUGUST, 24)).gym(sourceGym)
                .status(ChallengeStatus.WAITING_TO_ACCEPT).trainer(sourceTrainer);
        sourceBadge = bb.build();

        sourceTrainerCreateDTO = new TrainerCreateDTO();
        sourceTrainerCreateDTO.setAdmin(expectedTrainer.isAdmin());
        sourceTrainerCreateDTO.setBorn(expectedTrainer.getBorn());
        sourceTrainerCreateDTO.setName(expectedTrainer.getName());
        sourceTrainerCreateDTO.setPassword("SomePasswordThatShoulntMap");
        sourceTrainerCreateDTO.setSurname(expectedTrainer.getSurname());
        sourceTrainerCreateDTO.setUserName(expectedTrainer.getUserName());
        expectedTrainerDTO = new TrainerDTOBuilder()
                .admin(sourceTrainer.isAdmin())
                .born(sourceTrainer.getBorn())
                .id(sourceTrainer.getId())
                .name(sourceTrainer.getName())
                .surname(sourceTrainer.getSurname())
                .userName(sourceTrainer.getUserName())
                .build();

        sourceGymCreateDTO = new GymCreateDTO();
        sourceGymCreateDTO.setGymLeaderID(-100L); /* Set to something so that we fail if it maps to something. */
        sourceGymCreateDTO.setLocation(expectedGym.getLocation());
        sourceGymCreateDTO.setType(expectedGym.getType());
        expectedGymDTO = new GymDTOBuilder()
                .gymLeader(expectedTrainerDTO)
                .id(sourceGym.getId())
                .location(sourceGym.getLocation())
                .type(sourceGym.getType())
                .build();

        nestedSpeciesDTO = new PokemonSpeciesDTOBuilder()
                .evolvesFrom(null)
                .id(nestedSpecies.getId())
                .primaryType(nestedSpecies.getPrimaryType())
                .secondaryType(nestedSpecies.getSecondaryType())
                .speciesName(nestedSpecies.getSpeciesName())
                .build();
        sourceSpeciesCreateDTO = new PokemonSpeciesCreateDTO();
        sourceSpeciesCreateDTO.setEvolvesFromId(-99L); /* Set to something so that we fail if it maps to something. */
        sourceSpeciesCreateDTO.setPrimaryType(expectedSpecies.getPrimaryType());
        sourceSpeciesCreateDTO.setSecondaryType(expectedSpecies.getSecondaryType());
        sourceSpeciesCreateDTO.setSpeciesName(expectedSpecies.getSpeciesName());
        expectedSpeciesDTO = new PokemonSpeciesDTOBuilder()
                .evolvesFrom(nestedSpeciesDTO)
                .id(sourceSpecies.getId())
                .primaryType(sourceSpecies.getPrimaryType())
                .secondaryType(sourceSpecies.getSecondaryType())
                .speciesName(sourceSpecies.getSpeciesName())
                .build();

        sourcePokemonCreateDTO = new PokemonCreateDTO();
        sourcePokemonCreateDTO.setCreatingTrainerId(-98L); /* Set to something so that we fail if it maps to something. */
        sourcePokemonCreateDTO.setLevel(expectedPokemon.getLevel());
        sourcePokemonCreateDTO.setNickname(expectedPokemon.getNickname());
        sourcePokemonCreateDTO.setPokemonSpeciesId(-97L); /* Set to something so that we fail if it maps to something. */
        expectedPokemonDTO = new PokemonDTOBuilder()
                .dateTimeOfCapture(sourcePokemon.getDateTimeOfCapture())
                .id(sourcePokemon.getId())
                .level(sourcePokemon.getLevel())
                .nickname(sourcePokemon.getNickname())
                .species(expectedSpeciesDTO)
                .trainer(expectedTrainerDTO)
                .build();

        sourceBadgeCreateDTO = new BadgeCreateDTO();
        sourceBadgeCreateDTO.setGymId(-96L); /* Set to something so that we fail if it maps to something. */
        sourceBadgeCreateDTO.setTrainerId(-95L); /* Set to something so that we fail if it maps to something. */
        expectedBadgeDTO = new BadgeDTOBuilder()
                .date(sourceBadge.getDate())
                .gym(expectedGymDTO)
                .id(sourceBadge.getId())
                .status(sourceBadge.getStatus())
                .trainer(expectedTrainerDTO)
                .build();
        
        sourceGymAndBadge = new GymAndBadge(sourceGym, sourceBadge);
        expectedGymAndBadgeDTO = new GymAndBadgeDTO(expectedGymDTO, expectedBadgeDTO);
    }

    @Test
    public void testMapTrainerCreateDTOToTrainer() {
        assertThat(bms.mapTo(sourceTrainerCreateDTO, Trainer.class))
                .isEqualToComparingFieldByField(expectedTrainer);
    }

    @Test
    public void testMapGymCreateDTOToGym() {
        assertThat(bms.mapTo(sourceGymCreateDTO, Gym.class))
                .isEqualToComparingFieldByField(expectedGym);
    }

    @Test
    public void testMapPokemonSpeciesCreateDTOToPokemonSpecies() {
        assertThat(bms.mapTo(sourceSpeciesCreateDTO, PokemonSpecies.class))
                .isEqualToComparingFieldByField(expectedSpecies);
    }

    @Test
    public void testMapPokemonCreateDTOToPokemon() {
        assertThat(bms.mapTo(sourcePokemonCreateDTO, Pokemon.class))
                .isEqualToComparingFieldByField(expectedPokemon);
    }
    
    @Test
    public void testMapBadgeCreateDTOToBadge() {
        assertThat(bms.mapTo(sourceBadgeCreateDTO, Badge.class))
                .isEqualToComparingFieldByField(expectedBadge);
    }

    @Test
    public void testMapTrainerToTrainerDTO() {
        assertThat(bms.mapTo(sourceTrainer, TrainerDTO.class))
                .isEqualToComparingFieldByField(expectedTrainerDTO);
    }

    @Test
    public void testMapGymToGymDTO() {
        assertThat(bms.mapTo(sourceGym, GymDTO.class))
                .isEqualToComparingFieldByField(expectedGymDTO);
    }

    @Test
    public void testMapPokemonSpeciesToPokemonSpeciesDTO() {
        assertThat(bms.mapTo(sourceSpecies, PokemonSpeciesDTO.class))
                .isEqualToComparingFieldByField(expectedSpeciesDTO);
    }

    @Test
    public void testMapPokemonToPokemonDTO() {
        assertThat(bms.mapTo(sourcePokemon, PokemonDTO.class))
                .isEqualToComparingFieldByField(expectedPokemonDTO);
    }
    
    @Test
    public void testMapBadgeToBadgeDTO() {
        assertThat(bms.mapTo(sourceBadge, BadgeDTO.class))
                .isEqualToComparingFieldByField(expectedBadgeDTO);
    }
    
    @Test
    public void testMapGymAndBadgeToGymAndBadgeDTO() {
        assertThat(bms.mapTo(sourceGymAndBadge, GymAndBadgeDTO.class))
                .isEqualToComparingFieldByField(expectedGymAndBadgeDTO);
    }

    @Test
    public void testMapCollection() {
        assertThat(bms.mapTo(Arrays.asList(nestedSpecies, sourceSpecies), PokemonSpeciesDTO.class))
                .usingFieldByFieldElementComparator()
                .containsExactly(nestedSpeciesDTO, expectedSpeciesDTO);
    }

}
