package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;

/**
 * Interface for Badge Facade
 *
 * @author Michal Mokros 456442
 */
public interface BadgeFacade {

    /**
     * Create a new badge
     * @param badge DTO of badge to create
     */
    void createBadge(BadgeCreateDTO badge);

    /**
     * Finds the badge with given ID
     * @param id of the badge to be found
     * @return BadgeDTO with id or null if such badge does not exist
     */
    BadgeDTO findBadgeById(Long id);

    /**
     * Returns gym for badge with given id
     * @param id of the badge with gym
     * @return GymDTO of the Badge with given id, null if such badge does not exist
     */
    GymDTO getGymOfBadge(Long id);

    /**
     * Returns trainer for badge with given id
     * @param id of the badge with trainer
     * @return TrainerDTO of the Badge with given id, null if such badge does not exist
     */
    TrainerDTO getTrainerOfBadge(Long id);

    /**
     * Revokes the badge from trainer, changes status of badge to @ChallengeStatus.REVOKED
     * @param badge to be revoked
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge
     */
    void revokeBadge(BadgeStatusChangeDTO badge) throws InsufficientRightsException;

    /**
     * A trainer losses a badge
     * @param badge to be lost
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge
     */
    void looseBadge(BadgeStatusChangeDTO badge) throws InsufficientRightsException;

    /**
     * A trainer wins a badge
     * @param badge to be won
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge
     */
    void wonBadge(BadgeStatusChangeDTO badge) throws InsufficientRightsException;

    /**
     * Reopens a closed challenge for trainer with given id
     * @param trainerId id of trainer to which challenge should be reopen to
     * @param badge of challenge to be reopened
     */
    void reopenChallenge(Long trainerId, BadgeStatusChangeDTO badge) throws InsufficientRightsException;

}
