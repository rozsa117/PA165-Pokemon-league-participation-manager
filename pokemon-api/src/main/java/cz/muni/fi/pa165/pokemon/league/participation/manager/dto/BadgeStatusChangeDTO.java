package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

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

    private Long trainerId;

    private ChallengeStatus newStatus;

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

    public ChallengeStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ChallengeStatus newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeStatusChangeDTO that = (BadgeStatusChangeDTO) o;
        return Objects.equals(badgeId, that.badgeId) &&
                Objects.equals(trainerId, that.trainerId) &&
                Objects.equals(newStatus, that.newStatus);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 29 * Objects.hashCode(trainerId) * Objects.hashCode(badgeId) * Objects.hashCode(newStatus);
        return hash;
    }

    @Override
    public String toString() {
        return "BadgeStatusChangeDTO{" +
                "badgeId=" + badgeId +
                ", trainerId=" + trainerId +
                ", newStatus=" + newStatus +
                '}';
    }
}
