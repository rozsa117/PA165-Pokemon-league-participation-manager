package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for Pokemon creation requests.
 * 
 * @author Tibor Zauko 433531
 */
public class PokemonCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    @NotNull
    private Long pokemonSpeciesId;

    @Min(1)
    @Max(100)
    private int level;

    private Long creatingTrainerId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getPokemonSpeciesId() {
        return pokemonSpeciesId;
    }

    public void setPokemonSpeciesId(Long pokemonSpeciesId) {
        this.pokemonSpeciesId = pokemonSpeciesId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getCreatingTrainerId() {
        return creatingTrainerId;
    }

    public void setCreatingTrainerId(Long creatingTrainerId) {
        this.creatingTrainerId = creatingTrainerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nickname);
        hash = 97 * hash + Objects.hashCode(this.pokemonSpeciesId);
        hash = 97 * hash + this.level;
        hash = 97 * hash + Objects.hashCode(this.creatingTrainerId);
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
        final PokemonCreateDTO other = (PokemonCreateDTO) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        if (!Objects.equals(this.pokemonSpeciesId, other.pokemonSpeciesId)) {
            return false;
        }
        return Objects.equals(this.creatingTrainerId, other.creatingTrainerId);
    }

    @Override
    public String toString() {
        return "PokemonCreateDTO{" + "nickname=" + nickname + ", pokemonSpeciesId=" + pokemonSpeciesId + ", level=" + level + ", creatingTrainerId=" + creatingTrainerId + '}';
    }

}
