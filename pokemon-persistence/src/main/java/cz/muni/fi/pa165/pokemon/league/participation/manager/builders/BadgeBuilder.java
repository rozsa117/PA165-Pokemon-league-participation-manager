package cz.muni.fi.pa165.pokemon.league.participation.manager.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import java.time.LocalDate;

/**
 * Builder class for Badge.
 * 
 * @author Tamás Rózsa 445653
 */
public class BadgeBuilder {
    
    private Long id;
    private Trainer trainer;
    private Gym gym;
    private LocalDate date;
    private ChallengeStatus status;
    
    public Badge build() {
        Badge badge = new Badge(id);
        badge.setTrainer(trainer);
        badge.setGym(gym);
        badge.setDate(date);
        badge.setStatus(status);
        return badge;
    }
    
    public BadgeBuilder trainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }
    
    public BadgeBuilder gym(Gym gym) {
        this.gym = gym;
        return this;
    }
    
    public BadgeBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }
    
    public BadgeBuilder status(ChallengeStatus status) {
        this.status = status;
        return this;
    }

    public BadgeBuilder id(Long id) {
        this.id = id;
        return this;
    }

}
