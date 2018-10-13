package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    public Badge() {
        this(null, null, null, null);
    }

    public Badge(Long id, Trainer trainer, Gym gym, LocalDate date) {
        this.id = id;
        this.trainer = trainer;
        this.gym = gym;
        this.date = date;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Trainer getTrainer() { return this.trainer; }

    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    public Gym getGym() { return this.gym; }

    public void setGym(Gym gym) { this.gym = gym; }

    public LocalDate getDate() { return this.date; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Badge{ " + "id = " + id + "trainer = " + trainer + ", gym = " + gym + ", date = " + date + " }";
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
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(id, badge.id);
    }
}
