package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing the leader of a gym.
 * 
 * @author Tamás Rózsa 445653
 */
public class ChangeGymLeaderDTO {
    
    @NotNull
    private Long gymID;
    
    @NotNull
    private Long newGymLeaderID;

    public Long getGymID() {
        return gymID;
    }

    public void setGymID(Long gymID) {
        this.gymID = gymID;
    }

    public Long getNewGymLeaderID() {
        return newGymLeaderID;
    }

    public void setNewGymLeaderID(Long newGymLeaderID) {
        this.newGymLeaderID = newGymLeaderID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.gymID);
        hash = 71 * hash + Objects.hashCode(this.newGymLeaderID);
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
        final ChangeGymLeaderDTO other = (ChangeGymLeaderDTO) obj;
        if (!Objects.equals(this.gymID, other.gymID)) {
            return false;
        }
        return Objects.equals(this.newGymLeaderID, other.newGymLeaderID);
    }

    @Override
    public String toString() {
        return "ChangeGymLeaderDTO{" + "gymID=" + gymID + ", newGymLeaderID=" + newGymLeaderID + '}';
    }
    
}
