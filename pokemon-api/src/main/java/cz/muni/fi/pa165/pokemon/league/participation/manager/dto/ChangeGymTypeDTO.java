package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing type of the gym. 
 * 
 * @author Tamás Rózsa 445653
 */
public class ChangeGymTypeDTO {
    
    @NotNull
    private Long gymId;
    
    @NotNull
    private PokemonType newGymType;

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public PokemonType getNewGymType() {
        return newGymType;
    }

    public void setNewGymType(PokemonType newGymType) {
        this.newGymType = newGymType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.gymId);
        hash = 59 * hash + Objects.hashCode(this.newGymType);
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
        final ChangeGymTypeDTO other = (ChangeGymTypeDTO) obj;
        if (!Objects.equals(this.gymId, other.gymId)) {
            return false;
        }
        return Objects.equals(this.newGymType, other.newGymType);
    }

    @Override
    public String toString() {
        return "ChangeGymTypeDTO{" + "gymId=" + gymId + ", newGymType=" + newGymType + '}';
    }
}
