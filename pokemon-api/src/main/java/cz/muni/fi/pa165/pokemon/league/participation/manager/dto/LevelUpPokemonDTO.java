package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO for requests for increasing a Pokemon's level.
 * 
 * @author Tibor Zauko 433531
 */
public class LevelUpPokemonDTO {

    @NotNull
    private Long id;

    private Long requestingTrainerId;

    @Min(1)
    @Max(100)
    private int level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestingTrainerId() {
        return requestingTrainerId;
    }

    public void setRequestingTrainerId(Long requestingTrainerId) {
        this.requestingTrainerId = requestingTrainerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.requestingTrainerId);
        hash = 41 * hash + this.level;
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
        final LevelUpPokemonDTO other = (LevelUpPokemonDTO) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.requestingTrainerId, other.requestingTrainerId);
    }

    @Override
    public String toString() {
        return "LevelUpPokemonDTO{" + "id=" + id + ", requestingTrainersId=" + requestingTrainerId + ", level=" + level + '}';
    }

}
