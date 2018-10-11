package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ValidationException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;

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
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException in case badge is not valid (ex. gym does not exist in database).
     */
    void createBadge(Badge badge) throws ServiceFailureException, ValidationException;

    /**
     * Updates badge in database.
     *
     * @param badge to be updated.
     * @throws IllegalArgumentException when badge is null.
     * @throws ServiceFailureException when db operation fails.
     * @throws ValidationException when badge is not valid.
     */
    void updateBadge(Badge badge) throws ServiceFailureException, ValidationException;

    /**
     * Deleted badge from database.
     *
     * @param badge to be deleted.
     * @throws IllegalArgumentException when badge is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void deleteBadge(Badge badge) throws ServiceFailureException;

    /**
     * Returns list of all Badges.
     *
     * @return list of all badges.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Badge> getAllBadges() throws ServiceFailureException;
}
