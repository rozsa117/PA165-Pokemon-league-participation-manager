package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers.PokemonTypeEnumDeserializer;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing type of the gym. 
 * 
 * @author Tamás Rózsa 445653
 */
public class ChangeGymTypeDTO {
    
    @NotNull
    private Long id;
    
    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType type;

    private Long trainerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }
    
    public Long getTrainerId() {
        return trainerId;
    }
    
    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + Objects.hashCode(this.trainerId);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.trainerId, other.trainerId);
    }

    @Override
    public String toString() {
        return "ChangeGymTypeDTO{" + "gymId=" + id + ", newGymType=" + type + ", trainerId=" + trainerId + '}';
    }
}
