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
    private Long id;
    
    @NotNull
    private Long gymLeader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGymLeader() {
        return gymLeader;
    }

    public void setGymLeader(Long gymLeader) {
        this.gymLeader = gymLeader;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.gymLeader);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.gymLeader, other.gymLeader);
    }

    @Override
    public String toString() {
        return "ChangeGymLeaderDTO{" + "gymID=" + id + ", newGymLeaderID=" + gymLeader + '}';
    }
    
}
