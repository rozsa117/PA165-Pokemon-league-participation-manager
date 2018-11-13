package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;

/**
 * Interface for Badge Facade
 *
 * @author Michal Mokros 456442
 */
public interface BadgeFacade {

    Long createBadge(BadgeCreateDTO badge);

    BadgeDTO findBadgeById(Long id);

    GymDTO getGymOfBadge(Long id);

    TrainerDTO getTrainerOfBadge(Long id);

    void revokeBadge(BadgeStatusChangeDTO badge);

    void issueBadgeToTrainer(Long trainerId, BadgeStatusChangeDTO badge);

    void reopenChallenge(Long trainerId, BadgeStatusChangeDTO badge);

    void trainerBreachedRules(Long trainerId, BadgeStatusChangeDTO badge);

}
