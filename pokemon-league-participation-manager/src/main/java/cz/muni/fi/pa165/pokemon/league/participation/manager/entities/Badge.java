package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class for badge object.
 *
 * @author Michal Mokros 456442
 */
public class Badge {

    private Trainer trainer;
    private Gym gym;
    private LocalDate date;

    public Badge() {
        this(null, null, null);
    }

    public Badge(Trainer trainer, Gym gym, LocalDate date) {
        this.trainer = trainer;
        this.gym = gym;
        this.date = date;
    }

    public Trainer getTrainer() { return this.trainer; }

    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    public Gym getGym() { return this.gym; }

    public void setGym(Gym gym) { this.gym = gym; }

    public LocalDate getDate() { return this.date; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Badge{ " + "trainer = " + trainer + ", gym = " + gym + ", date = " + date + " }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.trainer.hashCode() ^ (this.gym.hashCode() >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(trainer, badge.trainer) &&
                Objects.equals(gym, badge.gym) &&
                Objects.equals(date, badge.date);
    }
}
