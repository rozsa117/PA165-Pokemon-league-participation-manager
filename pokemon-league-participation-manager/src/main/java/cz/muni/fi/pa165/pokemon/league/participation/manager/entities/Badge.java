package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class for badge object.
 *
 * @author Michal Mokros 456442
 */
@Entity(name = "Badge")
@Table(name = "BADGE")
public class Badge {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Column(nullable = false)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Column(nullable = false, unique = true)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    @Enumerated
    private ChallengeStatus status;

    public Badge() {
    }

    public Badge(Long id) {
        this.id = id;
        this.trainer = null;
        this.gym = null;
        this.date = null;
        this.status = null;
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
