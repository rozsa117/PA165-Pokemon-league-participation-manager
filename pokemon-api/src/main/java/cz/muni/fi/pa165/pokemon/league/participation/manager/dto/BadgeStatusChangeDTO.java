package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for change of badge status
 *
 * @author Michal Mokros 456442
 */
public class BadgeStatusChangeDTO {

    @NotNull
    private Long badgeId;

    @NotNull
    private Long trainerId;

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeStatusChangeDTO that = (BadgeStatusChangeDTO) o;
        return Objects.equals(badgeId, that.badgeId) &&
                Objects.equals(trainerId, that.trainerId);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 29 * Objects.hashCode(trainerId) * Objects.hashCode(badgeId);
        return hash;
    }

    @Override
    public String toString() {
        return "BadgeStatusChangeDTO{" +
                "badgeId=" + badgeId +
                ", trainerId=" + trainerId +
                '}';
    }
}
