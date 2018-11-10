package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for Pokemon's nickname change requests.
 * 
 * @author Tibor Zauko 433531
 */
public class RenamePokemonDTO {

    @NotNull
    private Long pokemonId;

    @NotNull
    private Long requestingTrainerId;

    @NotNull
    @Size(min = 3, max = 50)
    private String newNickname;

    public Long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(Long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Long getRequestingTrainerId() {
        return requestingTrainerId;
    }

    public void setRequestingTrainerId(Long requestingTrainerId) {
        this.requestingTrainerId = requestingTrainerId;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.pokemonId);
        hash = 67 * hash + Objects.hashCode(this.requestingTrainerId);
        hash = 67 * hash + Objects.hashCode(this.newNickname);
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
        final RenamePokemonDTO other = (RenamePokemonDTO) obj;
        if (!Objects.equals(this.newNickname, other.newNickname)) {
            return false;
        }
        if (!Objects.equals(this.pokemonId, other.pokemonId)) {
            return false;
        }
        return Objects.equals(this.requestingTrainerId, other.requestingTrainerId);
    }

    @Override
    public String toString() {
        return "RenamePokemonDTO{" + "pokemonId=" + pokemonId + ", trainersId=" + requestingTrainerId + ", newNickname=" + newNickname + '}';
    }

}
