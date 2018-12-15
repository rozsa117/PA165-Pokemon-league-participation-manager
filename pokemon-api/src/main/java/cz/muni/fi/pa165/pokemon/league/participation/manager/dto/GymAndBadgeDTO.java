package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import java.util.Objects;

/**
 * DTO for GymAndBadge objects.
 *
 * @author Tibor Zauko 433531
 */
public class GymAndBadgeDTO {
    
    private GymDTO gym;
    private BadgeDTO badge;

    public GymAndBadgeDTO() {
    }

    public GymAndBadgeDTO(GymDTO gym, BadgeDTO badge) {
        this.gym = gym;
        this.badge = badge;
    }

    public GymDTO getGym() {
        return gym;
    }

    public void setGym(GymDTO gym) {
        this.gym = gym;
    }

    public BadgeDTO getBadge() {
        return badge;
    }

    public void setBadge(BadgeDTO badge) {
        this.badge = badge;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.gym);
        hash = 73 * hash + Objects.hashCode(this.badge);
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
        final GymAndBadgeDTO other = (GymAndBadgeDTO) obj;
        if (!Objects.equals(this.gym, other.gym)) {
            return false;
        }
        if (!Objects.equals(this.badge, other.badge)) {
            return false;
        }
        return true;
    }
    
}
