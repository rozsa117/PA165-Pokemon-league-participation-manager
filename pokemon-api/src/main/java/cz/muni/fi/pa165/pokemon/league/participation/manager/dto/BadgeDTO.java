package cz.muni.fi.pa165.pokemon.league.participation.manager.dto;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for Badge object
 *
 * @author Michal Mokros 456442
 */
public class BadgeDTO {

    private Long id;
    private TrainerDTO trainer;
    private GymDTO gym;
    private LocalDate date;
    private ChallengeStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public GymDTO getGym() {
        return gym;
    }

    public void setGym(GymDTO gym) {
        this.gym = gym;
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
    public String toString() {
        return "Badge{ " + "id = " + getId() +
                "trainer = " + getTrainer() +
                ", gym = " + getGym() +
                ", date = " + getDate() + " }";
    }

    @Override
    public int hashCode() {
        final int hash = 7;
        int result = 1;
        result = hash * result
                + ((trainer == null) ? 0 : trainer.hashCode())
                + ((gym == null) ? 0 : gym.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BadgeDTO)) return false;
        BadgeDTO badge = (BadgeDTO) o;
        return Objects.equals(getTrainer(), badge.getTrainer())
                && Objects.equals(getGym(), badge.getGym());
    }
}
