package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import com.sun.istack.internal.NotNull;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Badge creation request
 *
 * @author Michal Mokros 456442
 */
public class BadgeCreateDTO {

    @NotNull
    private Long trainerId;

    @NotNull
    private Long gymId;

    @NotNull
    private LocalDate date;

    @NotNull
    private ChallengeStatus status;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public void setStatus(ChallengeStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeCreateDTO that = (BadgeCreateDTO) o;
        return Objects.equals(trainerId, that.trainerId) &&
                Objects.equals(gymId, that.gymId);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 29 * hash + Objects.hashCode(this.trainerId) + Objects.hashCode(this.gymId);
        return hash;
    }

    @Override
    public String toString() {
        return "BadgeCreateDTO{" +
                "trainerId=" + trainerId +
                ", gymId=" + gymId +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
