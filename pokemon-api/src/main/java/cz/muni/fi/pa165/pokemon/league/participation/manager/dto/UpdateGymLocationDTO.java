package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for changing location of Gym.
 * 
 * @author Tamás Rózsa 445653
 */
public class UpdateGymLocationDTO {
    
    @NotNull
    private Long gymID;

    @NotNull
    @Size(min = 3, max = 50)
    private String newLocation;

    public Long getGymID() {
        return gymID;
    }

    public void setGymID(Long gymID) {
        this.gymID = gymID;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.gymID);
        hash = 97 * hash + Objects.hashCode(this.newLocation);
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
        final UpdateGymLocationDTO other = (UpdateGymLocationDTO) obj;
        if (!Objects.equals(this.newLocation, other.newLocation)) {
            return false;
        }
        return Objects.equals(this.gymID, other.gymID);
    }

    @Override
    public String toString() {
        return "UpdateGymLocationDTO{" + "gymID=" + gymID + ", newLocation=" + newLocation + '}';
    }
}
