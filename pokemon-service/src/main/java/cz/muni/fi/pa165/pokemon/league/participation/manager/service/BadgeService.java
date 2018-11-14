package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface of Badge Service
 *
 * @author Michal Mokros 456442
 */
public interface BadgeService {

    /**
     * Creates new Badge
     * @param badge to be created
     */
    void createBadge(Badge badge);

    /**
     * Removes badge from trainer (deleted badge)
     * @param badge to be deleted
     */
    void removeBadge(Badge badge);

    /**
     * Changes date on badge.
     * @param badge to be changed
     * @param newDate new date of badge
     */
    void changeBadgeDate(Badge badge, LocalDate newDate);

    /**
     * Changes status on badge
     * @param badge to be changed
     * @param newStatus new status of badge
     */
    void changeBadgeStatus(Badge badge, ChallengeStatus newStatus);

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
    Set<Badge> findBadgeOfTrainer(Trainer trainer);

    /**
     * Return all currently existing badges for gym
     * @param gym for which badges shall be found
     * @return list of badges for gym
     */
    Set<Badge> findBadgeOfGym(Gym gym);

}
