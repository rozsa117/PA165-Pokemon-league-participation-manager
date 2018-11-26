package cz.muni.fi.pa165.pokemon.league.participation.manager.dto.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import java.time.LocalDate;

/**
 * Builder class for BadgeDTO.
 * 
 * @author Tamás Rózsa 445653
 */
public class BadgeDTOBuilder {
    
    private Long id;
    private TrainerDTO trainer;
    private GymDTO gym;
    private LocalDate date;
    private ChallengeStatus status;

    public BadgeDTO build() {
        BadgeDTO badge = new BadgeDTO();
        badge.setId(id);
        badge.setTrainer(trainer);
        badge.setGym(gym);
        badge.setDate(date);
        badge.setStatus(status);
        return badge;
    }
    
    public BadgeDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public BadgeDTOBuilder trainer(TrainerDTO trainer) {
        this.trainer = trainer;
        return this;
    }
    
    public BadgeDTOBuilder gym(GymDTO gym) {
        this.gym = gym;
        return this;
    }
    
    public BadgeDTOBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }
    public BadgeDTOBuilder status(ChallengeStatus status) {
        this.status = status;
        return this;
    }
}
