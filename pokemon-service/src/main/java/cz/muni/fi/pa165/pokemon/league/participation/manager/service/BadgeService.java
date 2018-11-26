package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import java.util.List;

/**
 * Interface of Badge Service
 *
 * @author Michal Mokros 456442
 */
public interface BadgeService {

    /**
     * Creates new Badge
     * @param badge to be created
     * @throws EntityIsUsedException when challenging trainer is the leader of the challenged gym.
     */
    void createBadge(Badge badge) throws EntityIsUsedException;

    /**
     * Removes badge from trainer (deleted badge)
     * @param badge to be deleted
     */
    void removeBadge(Badge badge);

    /**
     * Changes status on badge
     * @param badge to be changed
     * @param newStatus new status of badge
     * @throws InvalidChallengeStatusChangeException In case invalid status change is requested.
     * The following changes are valid(from-to) WON - REVOKED, LOST - WAITING_TO_ACCEPT, WITING_TO_ACCEPT - WON || LOST,
     * REVOKED - WON
     */
    void changeBadgeStatus(Badge badge, ChallengeStatus newStatus) throws InvalidChallengeStatusChangeException;

    /**
     * Find the badge with given id
     * @param id of the badge to be found
     * @return Found badge, null if none exist
     */
    Badge findBadgeById(Long id);

    /**
     * Return all badges currently owned by trainer
     * @param trainer whose badges shall be found
     * @return list of badges belonging to the trainer
     */
    List<Badge> findBadgesOfTrainer(Trainer trainer);

    /**
     * Return all currently existing badges for gym
     * @param gym for which badges shall be found
     * @return list of badges for gym
     */
    List<Badge> findBadgesOfGym(Gym gym);

}
