package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;

import java.util.List;

/**
 * Interface of Data Access Objects for Badge class
 *
 * @author Michal Mokros 456442
 */
public interface BadgeDAO {

    /**
     * Creates new Badge in database.
     *
     * @param badge to be created.
     * @throws IllegalArgumentException when badge is null.
     */
    void createBadge(Badge badge);

    /**
     * Updates badge in database.
     *
     * @param badge to be updated.
     * @throws IllegalArgumentException when badge is null.
     */
    void updateBadge(Badge badge);

    /**
     * Deleted badge from database.
     *
     * @param badge to be deleted.
     * @throws IllegalArgumentException when badge is null.
     */
    void deleteBadge(Badge badge);

    /**
     * Returns the badge with given id.
     *
     * @param id primary key of the requested badge.
     * @return requested Badge, null in case no such badge exists.
     * @throws IllegalArgumentException when Id is null.
     */
    Badge findBadgeById(Long id);

    /**
     * Returns list of all Badges.
     *
     * @return list of all badges.
     */
    List<Badge> getAllBadges();
    
    /**
     * Returns list of all badges of a gym.
     * 
     * @param gym Gym to find the badges of.
     * @return List of all badges of the gym.
     */
    List<Badge> findBadgesOfGym(Gym gym);
    
    /**
     * Returns list of all badges of a trainer.
     * 
     * @param trainer Trainer to find the badges of.
     * @return List of all badges of the trainer.
     */
    List<Badge> findBadgesOfTrainer(Trainer trainer);
}
