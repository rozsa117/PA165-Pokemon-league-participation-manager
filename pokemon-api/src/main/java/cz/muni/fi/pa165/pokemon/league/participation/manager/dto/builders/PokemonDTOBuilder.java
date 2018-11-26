package cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.PokemonSpeciesDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import java.time.LocalDateTime;

/**
 * Builder class for PokemonDTO.
 * 
 * @author Tamás Rózsa 445653
 */
public class PokemonDTOBuilder {
    
    private Long id;

    private String nickname;

    private PokemonSpeciesDTO species;

    private int level;

    private TrainerDTO trainer;

    private LocalDateTime dateTimeOfCapture;

    public PokemonDTO build() {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(id);
        pokemonDTO.setNickname(nickname);
        pokemonDTO.setSpecies(species);
        pokemonDTO.setLevel(level);
        pokemonDTO.setTrainer(trainer);
        pokemonDTO.setDateTimeOfCapture(dateTimeOfCapture);
        return pokemonDTO;
    }
    
    public PokemonDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public PokemonDTOBuilder nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
    
    public PokemonDTOBuilder species(PokemonSpeciesDTO species) {
        this.species = species;
        return this;
    }
    
    public PokemonDTOBuilder level(int level) {
        this.level = level;
        return this;
    }
    
    public PokemonDTOBuilder trainer(TrainerDTO trainer) {
        this.trainer = trainer;
        return this;
    }
    
    public PokemonDTOBuilder dateTimeOfCapture(LocalDateTime dateTimeOfCapture) {
        this.dateTimeOfCapture = dateTimeOfCapture;
        return this;
    }
}
