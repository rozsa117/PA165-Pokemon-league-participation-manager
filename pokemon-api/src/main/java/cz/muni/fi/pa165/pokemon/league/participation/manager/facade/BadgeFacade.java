package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import java.util.List;

/**
 * Interface for Badge Facade
 *
 * @author Michal Mokros 456442
 */
public interface BadgeFacade {

    /**
     * Create a new badge
     * @param badge DTO of badge to create
     * @return ID of the newly created badge.
     * @throws NoSuchEntityException when given trainer or gym doesn't exist.
     * @throws EntityIsUsedException when challenger and gym's leader are the same trainer.
     */
    Long createBadge(BadgeCreateDTO badge) throws NoSuchEntityException, EntityIsUsedException;

    /**
     * Finds the badge with given ID
     * @param id of the badge to be found
     * @return BadgeDTO with id or null if such badge does not exist.
     */
    BadgeDTO findBadgeById(Long id);

    /**
     * Returns gym for badge with given id
     * @param id of the badge with gym
     * @return GymDTO of the Badge with given id, null if such badge does not exist.
     */
    GymDTO getGymOfBadge(Long id);

    /**
     * Returns trainer for badge with given id
     * @param id of the badge with trainer
     * @return TrainerDTO of the Badge with given id, null if such badge does not exist.
     */
    TrainerDTO getTrainerOfBadge(Long id);

    /**
     * Revokes the badge from trainer, changes status of badge to @ChallengeStatus.REVOKED
     * @param badge to be revoked
     * @throws InvalidChallengeStatusChangeException in case invalid change of challenge status is required.
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge.
     * @throws NoSuchEntityException when requested badge doesn't exist.
     */
    void revokeBadge(BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, NoSuchEntityException, InvalidChallengeStatusChangeException;

    /**
     * A trainer loses a badge
     * @param badge to be lost
     * @throws InvalidChallengeStatusChangeException in case invalid change of challenge status is required.
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge.
     * @throws NoSuchEntityException when requested badge doesn't exist.
     */
    void loseBadge(BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, NoSuchEntityException, InvalidChallengeStatusChangeException;

    /**
     * A trainer wins a badge
     * @param badge to be won
     * @throws InvalidChallengeStatusChangeException in case invalid change of challenge status is required.
     * @throws InsufficientRightsException in case requesting trainer is not the leader of the gym that gave the badge.
     * @throws NoSuchEntityException when requested badge doesn't exist.
     */
    void winBadge(BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, NoSuchEntityException, InvalidChallengeStatusChangeException;

    /**
     * Reopens a closed challenge for trainer with given id
     * @param badge of challenge to be reopened
     * @throws InsufficientRightsException in case requesting trainer is not the trainer who received the badge.
     * @throws InvalidChallengeStatusChangeException in case invalid change of challenge status is required.
     * @throws NoSuchEntityException when requested badge doesn't exist.
     */
    void reopenChallenge(BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, NoSuchEntityException, InvalidChallengeStatusChangeException;

    /**
     * Retrieves a list of all badges of a trainer.
     * @param trainerId ID of trainer.
     * @return List of badges belonging to a trainer.
     * @throws NoSuchEntityException when trainer of given id doesn't exist.
     */
    List<BadgeDTO> findBadgesOfTrainer(Long trainerId)
            throws NoSuchEntityException;
    /**
     * Retrieves a list of all badges of a gym.
     * @param gymId ID of gym.
     * @return List of badges issued by a gym.
     * @throws NoSuchEntityException when gym of given id doesn't exist.
     */
    List<BadgeDTO> findBadgesOfGym(Long gymId)
            throws NoSuchEntityException;
    
}
