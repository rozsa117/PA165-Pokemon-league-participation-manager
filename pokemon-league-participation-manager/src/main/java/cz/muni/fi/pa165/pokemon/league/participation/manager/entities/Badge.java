package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class for badge object.
 *
 * @author Michal Mokros 456442
 */
@Entity(name = "Badge")
@Table(name = "BADGE", uniqueConstraints = @UniqueConstraint(columnNames = {"trainer_id", "gym_id"}))
public class Badge {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @NotNull
    @Enumerated
    @Column(nullable = false)
    private ChallengeStatus status;

    public Badge() {
    }

    public Badge(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Trainer getTrainer() { return this.trainer; }

    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    public Gym getGym() { return this.gym; }

    public void setGym(Gym gym) { this.gym = gym; }

    public LocalDate getDate() { return this.date; }

    public void setDate(LocalDate date) { this.date = date; }

    public ChallengeStatus getStatus() { return status; }

    public void setStatus(ChallengeStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Badge{ " + "id = " + getId() +
                "trainer = " + getTrainer() +
                ", gym = " + getGym() +
                ", date = " + getDate() + " }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Badge)) return false;
        Badge badge = (Badge) o;
        return Objects.equals(getTrainer(), badge.getTrainer())
                && Objects.equals(getGym(), badge.getGym());
    }
}
