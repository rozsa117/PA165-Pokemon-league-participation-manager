package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing preevolution of a Pokemon species.
 * 
 * @author Tibor Zauko 433531
 */
public class ChangePreevolutionDTO {

    @NotNull
    private Long speciesId;

    private Long preevolutionId;

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
    }

    public Long getPreevolutionId() {
        return preevolutionId;
    }

    public void setPreevolutionId(Long preevolutionId) {
        this.preevolutionId = preevolutionId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.speciesId);
        hash = 19 * hash + Objects.hashCode(this.preevolutionId);
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
        final ChangePreevolutionDTO other = (ChangePreevolutionDTO) obj;
        if (!Objects.equals(this.speciesId, other.speciesId)) {
            return false;
        }
        return Objects.equals(this.preevolutionId, other.preevolutionId);
    }

    @Override
    public String toString() {
        return "ChangePreevolutionDTO{" + "speciesId=" + speciesId + ", preevolutionId=" + preevolutionId + '}';
    }

}
