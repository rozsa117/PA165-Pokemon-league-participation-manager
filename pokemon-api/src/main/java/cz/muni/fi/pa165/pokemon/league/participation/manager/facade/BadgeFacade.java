package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

public interface BadgeFacade {
    Long createBadge(BadgeCreateDTO badge);
    BadgeDTO findBadgeById(Long id);
    GymDTO getGymOfBadge(Long id);
    TrainerDTO getTrainerOfBadge(Long id);
    void updateStatusOnBadge(Long id, ChallengeStatus status);
}
