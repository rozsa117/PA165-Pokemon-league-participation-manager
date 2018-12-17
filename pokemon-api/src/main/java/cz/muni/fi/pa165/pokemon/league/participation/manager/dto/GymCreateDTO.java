package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.deserializers.PokemonTypeEnumDeserializer;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for gym creation requests.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymCreateDTO {
    
    @NotNull
    @Size(min = 1, max = 50)
    private String location;
    
    @JsonDeserialize(using = PokemonTypeEnumDeserializer.class)
    private PokemonType type;

    @NotNull
    private Long gymLeaderID;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public Long getGymLeaderID() {
        return gymLeaderID;
    }

    public void setGymLeaderID(Long gymLeaderID) {
        this.gymLeaderID = gymLeaderID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.location);
        hash = 43 * hash + Objects.hashCode(this.type);
        hash = 43 * hash + Objects.hashCode(this.gymLeaderID);
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
        final GymCreateDTO other = (GymCreateDTO) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.gymLeaderID, other.gymLeaderID);
    }

    @Override
    public String toString() {
        return "GymCreateDTO{" + "location=" + location + ", type=" + type + ", gymLeaderID=" + gymLeaderID + '}';
    }
}
