package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for requests to release a Pokemon.
 * Needed, because only owning trainer can release a Pokemon.
 * 
 * @author Tibor Zauko 433531
 */
public class ReleasePokemonDTO {

    @NotNull
    private Long pokemonId;

    @NotNull
    private Long requestingTrainerId;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.pokemonId);
        hash = 43 * hash + Objects.hashCode(this.requestingTrainerId);
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
        final ReleasePokemonDTO other = (ReleasePokemonDTO) obj;
        if (!Objects.equals(this.pokemonId, other.pokemonId)) {
            return false;
        }
        return Objects.equals(this.requestingTrainerId, other.requestingTrainerId);
    }

    @Override
    public String toString() {
        return "ReleasePokemonDTO{" + "pokemonId=" + pokemonId + ", requestingTrainerId=" + requestingTrainerId + '}';
    }

}
