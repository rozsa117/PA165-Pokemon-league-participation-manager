package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for Pokemon.
 * 
 * @author Tibor Zauko 433531
 */
public class PokemonDTO {

    private Long pokemonId;

    private String nickname;

    private PokemonSpeciesDTO species;

    private int level;

    private TrainerDTO trainer;

    private LocalDateTime dateTimeOfCreation;

    public Long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(Long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonSpeciesDTO getSpecies() {
        return species;
    }

    public void setSpecies(PokemonSpeciesDTO species) {
        this.species = species;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public LocalDateTime getDateTimeOfCreation() {
        return dateTimeOfCreation;
    }

    public void setDateTimeOfCreation(LocalDateTime dateTimeOfCreation) {
        this.dateTimeOfCreation = dateTimeOfCreation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.pokemonId);
        hash = 71 * hash + Objects.hashCode(this.nickname);
        hash = 71 * hash + Objects.hashCode(this.species);
        hash = 71 * hash + this.level;
        hash = 71 * hash + Objects.hashCode(this.trainer);
        hash = 71 * hash + Objects.hashCode(this.dateTimeOfCreation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PokemonDTO other = (PokemonDTO) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        if (!Objects.equals(this.pokemonId, other.pokemonId)) {
            return false;
        }
        if (!Objects.equals(this.species, other.species)) {
            return false;
        }
        if (!Objects.equals(this.trainer, other.trainer)) {
            return false;
        }
        return Objects.equals(this.dateTimeOfCreation, other.dateTimeOfCreation);
    }

    @Override
    public String toString() {
        return "PokemonDTO{" + "pokemonId=" + pokemonId + ", nickname=" + nickname + ", species=" + species + ", level=" + level + ", trainer=" + trainer + ", dateTimeOfCreation=" + dateTimeOfCreation + '}';
    }

}
