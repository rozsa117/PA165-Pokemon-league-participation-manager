package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for Pokemon.
 * 
 * @author Tibor Zauko 433531
 */
public class PokemonDTO {

    private Long id;

    private String nickname;

    private PokemonSpeciesDTO species;

    private int level;

    private TrainerDTO trainer;

    private LocalDateTime dateTimeOfCapture;

    public Long getId() {
        return id;
    }

    public void setId(Long pokemonId) {
        this.id = pokemonId;
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

    public LocalDateTime getDateTimeOfCapture() {
        return dateTimeOfCapture;
    }

    public void setDateTimeOfCapture(LocalDateTime dateTimeOfCreation) {
        this.dateTimeOfCapture = dateTimeOfCreation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.nickname);
        hash = 71 * hash + Objects.hashCode(this.species);
        hash = 71 * hash + this.level;
        hash = 71 * hash + Objects.hashCode(this.trainer);
        hash = 71 * hash + Objects.hashCode(this.dateTimeOfCapture);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.species, other.species)) {
            return false;
        }
        if (!Objects.equals(this.trainer, other.trainer)) {
            return false;
        }
        return Objects.equals(this.dateTimeOfCapture, other.dateTimeOfCapture);
    }

    @Override
    public String toString() {
        return "PokemonDTO{" + "id=" + id + ", nickname=" + nickname + ", species=" + species + ", level=" + level + ", trainer=" + trainer + ", dateTimeOfCapture=" + dateTimeOfCapture + '}';
    }

}
