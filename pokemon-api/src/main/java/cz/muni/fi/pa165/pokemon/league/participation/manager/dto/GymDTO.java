package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.PokemonType;
import java.util.Objects;

/**
 * DTO for Gym.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymDTO {
    
    private Long id;;
    
    private String location;
    
    private PokemonType type;
    
    private TrainerDTO gymLeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TrainerDTO getGymLeader() {
        return gymLeader;
    }

    public void setGymLeader(TrainerDTO gymLeader) {
        this.gymLeader = gymLeader;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.gymLeader);
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
        final GymDTO other = (GymDTO) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.gymLeader, other.gymLeader);
    }

    @Override
    public String toString() {
        return "GymDTO{" + "id=" + id + ", location=" + location + ", type=" + type + ", gymLeader=" + gymLeader + '}';
    }
    
}
