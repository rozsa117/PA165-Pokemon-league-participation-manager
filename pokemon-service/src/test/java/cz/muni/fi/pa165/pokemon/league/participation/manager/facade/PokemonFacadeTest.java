package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.PokemonSpeciesBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.builders.TrainerBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.EvolvePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GiftPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.LevelUpPokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.ReleasePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.RenamePokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.PokemonDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.PokemonSpeciesDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders.TrainerDTOBuilder;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Pokemon;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.PokemonSpecies;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidPokemonEvolutionException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.LevelNotIncreasedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.PokemonSpeciesService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.config.ServiceConfiguration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test for pokemon facade.
 * 
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PokemonFacadeTest {
    
    @Mock
    private PokemonService pokemonService;
    
    @Mock
    private PokemonSpeciesService pokemonSpeciesService;
    
    @Mock
    private TrainerService trainerService;
    
    @Mock
    private BeanMappingService beanMappingService;
    
    @InjectMocks
    private final PokemonFacadeImpl pokemonFacade = new PokemonFacadeImpl();
    
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule(); 
    
    private static TrainerDTO ashDTO;
    private static TrainerDTO brockDTO;
    
    private static PokemonSpeciesDTO electricDTO;
    private static PokemonSpeciesDTO raichuSpeciesDTO;
    
    private static PokemonDTO pikachuDTO;
    private static PokemonDTO raichuDTO;
    
    private static Trainer ashEntity;
    private static Trainer brockEntity;
    private static PokemonSpecies electricEntity;
    private static PokemonSpecies raichuSpeciesEntity;
    private static Pokemon pikachuEntity;
    private static Pokemon raichuEntity;
    
    public PokemonFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
         ashDTO = new TrainerDTOBuilder()
                .id(100L)
                .born(LocalDate.of(2000, Month.APRIL, 8))
                .admin(false)
                .name("Ash")
                .surname("Ketchum")
                .userName("ketchup")
                .build();
        
         ashEntity = new TrainerBuilder()
                 .id(100L)
                .born(LocalDate.of(2000, Month.APRIL, 8))
                .name("Ash")
                .surname("Ketchum")
                .userName("ketchup")
                .passwordHash("pswda")
                .build();
         
        brockDTO = new TrainerDTOBuilder()
                .id(101L)
                .born(LocalDate.of(1995, Month.JANUARY, 19))
                .admin(false)
                .name("Brock")
                .surname("Boulder")
                .userName("onixDude")
                .build();
        
        brockEntity = new TrainerBuilder()
                .id(101L)
                .born(LocalDate.of(1995, Month.JANUARY, 19))
                .name("Brock")
                .surname("Boulder")
                .userName("onixDude")
                .passwordHash("nohash")
                .build();
        
        electricDTO = new PokemonSpeciesDTOBuilder()
                .id(10L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .build();
        
        electricEntity = new PokemonSpeciesBuilder()
                .id(10L)
                .speciesName("Electric")
                .primaryType(PokemonType.ELECTRIC)
                .build();
        
        raichuSpeciesEntity = new PokemonSpeciesBuilder()
                .id(12L)
                .speciesName("Raichu")
                .primaryType(PokemonType.ELECTRIC)
                .build();
        
        raichuSpeciesDTO = new PokemonSpeciesDTOBuilder()
                .id(11L)
                .speciesName("Raichu")
                .primaryType(PokemonType.ELECTRIC)
                .build();
        
        pikachuDTO = new PokemonDTOBuilder()
                .id(1L)
                .nickname("Pikachu")
                .dateTimeOfCapture(LocalDateTime.of(2017, Month.JUNE, 1, 1, 1))
                .species(electricDTO)
                .trainer(ashDTO)
                .level(20)
                .build();
        
        pikachuEntity = new PokemonBuilder()
                .id(1L)
                .nickname("Pikachu")
                .dateTimeOfCapture(LocalDateTime.now())
                .pokemonSpecies(electricEntity)
                .trainer(ashEntity)
                .level(20)
                .build();
        
        raichuEntity = new PokemonBuilder()
                .id(2L)
                .nickname("Raichu")
                .dateTimeOfCapture(LocalDateTime.of(2018, Month.MARCH, 1, 0, 0))
                .pokemonSpecies(raichuSpeciesEntity)
                .trainer(brockEntity)
                .level(100)
                .build();
    }
    
    @Before
    public void setUp() {
        when(trainerService.getTrainerWithId(ashDTO.getId())).thenReturn(ashEntity);
        when(pokemonService.findPokemonById(pikachuDTO.getId())).thenReturn(pikachuEntity);
        when(pokemonSpeciesService.findPokemonSpeciesById(raichuSpeciesEntity.getId())).thenReturn(raichuSpeciesEntity);
        when(trainerService.getTrainerWithId(brockDTO.getId())).thenReturn(brockEntity);
        when(pokemonService.findPokemonById(raichuEntity.getId())).thenReturn(raichuEntity);
    }
    
    @Test
    public void testCreatePokemon() throws NoSuchEntityException {
        final LocalDateTime NOW = LocalDateTime.now();
        Pokemon fromDTO = new PokemonBuilder()
                .dateTimeOfCapture(null)
                .id(20L)
                .level(50)
                .nickname("Sparky")
                .pokemonSpecies(null)
                .trainer(null)
                .build();
        PokemonCreateDTO createDTO = new PokemonCreateDTO();
        createDTO.setCreatingTrainerId(ashEntity.getId());
        createDTO.setPokemonSpeciesId(raichuSpeciesEntity.getId());
        createDTO.setLevel(fromDTO.getLevel());
        createDTO.setNickname(fromDTO.getNickname());
        when(beanMappingService.mapTo(createDTO, Pokemon.class)).thenReturn(fromDTO);
        doAnswer(invocation -> {
            assertThat(fromDTO.getDateTimeOfCapture()).isNotNull().isAfterOrEqualTo(NOW);
            assertThat(fromDTO.getSpecies()).isNotNull().isEqualTo(raichuSpeciesEntity);
            assertThat(fromDTO.getTrainer()).isNotNull().isEqualTo(ashEntity);
            return null;
        }).when(pokemonService).createPokemon(fromDTO);
        assertThat(pokemonFacade.createPokemon(createDTO)).isEqualTo(fromDTO.getId());
        verify(pokemonService, atLeastOnce()).createPokemon(fromDTO);
    }
    
    @Test
    public void testFindPokemonById() {
        when(pokemonService.findPokemonById(pikachuDTO.getId())).thenReturn(pikachuEntity);
        when(beanMappingService.mapTo(pikachuEntity, PokemonDTO.class)).thenReturn(pikachuDTO);
        assertThat(pokemonFacade.findPokemonById(pikachuDTO.getId())).isEqualToComparingFieldByField(pikachuDTO);
    }
    
    @Test
    public void testGetPokemonOfTrainer() throws NoSuchEntityException {
        List<Pokemon> pokemonEntity = Arrays.asList(new Pokemon[] {pikachuEntity});
        List<PokemonDTO> pokemonDTO = Arrays.asList(new PokemonDTO[] {pikachuDTO});
        when(pokemonService.getPokemonOfTrainer(ashEntity)).thenReturn(pokemonEntity);
        when(beanMappingService.mapTo(pokemonEntity, PokemonDTO.class)).thenReturn(pokemonDTO);
        assertThat(pokemonFacade.getPokemonOfTrainer(ashDTO.getId()))
                .usingFieldByFieldElementComparator().containsOnly(pikachuDTO);
    }
    
    @Test
    public void testGetPokemonOfTrainerThrowsNoSuchEntityException() {
        assertThatExceptionOfType(NoSuchEntityException.class).isThrownBy(() -> pokemonFacade.getPokemonOfTrainer(Long.MIN_VALUE));
    }
    
    @Test
    public void testReleasePokemon() throws InsufficientRightsException, NoSuchEntityException {
        ReleasePokemonDTO release = new ReleasePokemonDTO();
        release.setPokemonId(pikachuDTO.getId());
        release.setRequestingTrainerId(ashDTO.getId());
        pokemonFacade.releasePokemon(release);
        verify(pokemonService, atLeastOnce()).releasePokemon(pikachuEntity);
    }
    
    @Test
    public void testReleasePokemonInsufficientRightsException() {
        ReleasePokemonDTO release = new ReleasePokemonDTO();
        release.setPokemonId(pikachuDTO.getId());
        release.setRequestingTrainerId(brockDTO.getId());
        assertThatExceptionOfType(InsufficientRightsException.class).isThrownBy(() -> pokemonFacade.releasePokemon(release));
    }
    
    @Test
    public void testRenamePokemon() throws InsufficientRightsException, NoSuchEntityException {
        RenamePokemonDTO rename = new RenamePokemonDTO();
        rename.setNewNickname("Pika");
        rename.setPokemonId(pikachuDTO.getId());
        rename.setRequestingTrainerId(ashDTO.getId());
        pokemonFacade.renamePokemon(rename);
        verify(pokemonService, atLeastOnce()).renamePokemon(pikachuEntity, rename.getNewNickname());
    }
    
    @Test
    public void testLevelUpPokemon() 
            throws LevelNotIncreasedException, InsufficientRightsException, NoSuchEntityException {
        LevelUpPokemonDTO levelUp = new LevelUpPokemonDTO();
        levelUp.setLevel(30);
        levelUp.setId(pikachuDTO.getId());
        levelUp.setRequestingTrainerId(ashDTO.getId());
        pokemonFacade.levelUpPokemon(levelUp);
        verify(pokemonService, atLeastOnce()).increasePokemonLevel(pikachuEntity, levelUp.getLevel());
    }
    
    @Test
    public void testEvolvePokemon() 
            throws InvalidPokemonEvolutionException, InsufficientRightsException, NoSuchEntityException {
        EvolvePokemonDTO evolve = new EvolvePokemonDTO();
        evolve.setNewSpeciesId(raichuSpeciesEntity.getId());
        evolve.setId(pikachuDTO.getId());
        evolve.setRequestingTrainerId(ashDTO.getId());
        pokemonFacade.evolvePokemon(evolve);
        verify(pokemonService, atLeastOnce()).evolvePokemon(pikachuEntity, raichuSpeciesEntity);
    }
    
    @Test
    public void testGiftPokemon() throws InsufficientRightsException, NoSuchEntityException {
        GiftPokemonDTO gift = new GiftPokemonDTO();
        gift.setGiftedTrainerId(ashDTO.getId());
        gift.setId(raichuEntity.getId());
        gift.setRequestingTrainerId(brockDTO.getId());
        pokemonFacade.giftPokemon(gift);
        verify(pokemonService, atLeastOnce()).giftPokemon(raichuEntity, ashEntity);
    }
}
