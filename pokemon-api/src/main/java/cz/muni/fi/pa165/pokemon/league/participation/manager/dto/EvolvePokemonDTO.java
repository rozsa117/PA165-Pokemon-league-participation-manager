package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for requests for evolution of a Pokemon.
 * 
 * @author Tibor Zauko 433531
 */
public class EvolvePokemonDTO {

    private Long pokemonId;

    @NotNull
    private Long newSpeciesId;

    private Long requestingTrainerId;

    public Long getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(Long pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Long getNewSpeciesId() {
        return newSpeciesId;
    }

    public void setNewSpeciesId(Long newSpeciesId) {
        this.newSpeciesId = newSpeciesId;
    }

    public Long getRequestingTrainerId() {
        return requestingTrainerId;
    }

    public void setRequestingTrainerId(Long requestingTrainerId) {
        this.requestingTrainerId = requestingTrainerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.pokemonId);
        hash = 17 * hash + Objects.hashCode(this.newSpeciesId);
        hash = 17 * hash + Objects.hashCode(this.requestingTrainerId);
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
        final EvolvePokemonDTO other = (EvolvePokemonDTO) obj;
        if (!Objects.equals(this.pokemonId, other.pokemonId)) {
            return false;
        }
        if (!Objects.equals(this.newSpeciesId, other.newSpeciesId)) {
            return false;
        }
        return Objects.equals(this.requestingTrainerId, other.requestingTrainerId);
    }

    @Override
    public String toString() {
        return "EvolvePokemonDTO{" + "pokemonId=" + pokemonId + ", newSpeciesId=" + newSpeciesId + ", trainersId=" + requestingTrainerId + '}';
    }

}
