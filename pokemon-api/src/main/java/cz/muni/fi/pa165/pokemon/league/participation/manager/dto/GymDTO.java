package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;

/**
 * DTO for Gym.
 * 
 * @author Tamás Rózsa 445653
 */
public class GymDTO {
    
    private Long gymID;
    
    private String location;
    
    private PokemonSpecies type;
    
        private TrainerDTO gymLeader;

    public Long getGymID() {
        return gymID;
    }

    public void setGymID(Long gymID) {
        this.gymID = gymID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PokemonSpecies getType() {
        return type;
    }

    public void setType(PokemonSpecies type) {
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
        hash = 97 * hash + Objects.hashCode(this.gymID);
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
        if (!Objects.equals(this.gymID, other.gymID)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.gymLeader, other.gymLeader);
    }

    @Override
    public String toString() {
        return "GymDTO{" + "gymID=" + gymID + ", location=" + location + ", type=" + type + ", gymLeader=" + gymLeader + '}';
    }
    
}
