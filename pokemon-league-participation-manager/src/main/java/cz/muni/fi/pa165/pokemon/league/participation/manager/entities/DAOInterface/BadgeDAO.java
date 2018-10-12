package cz.muni.fi.pa165.pokemon.league.participation.manager.entities.DAOInterface;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.ServiceFailureException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;

import javax.xml.ws.Service;
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
     */
    void createBadge(Badge badge) throws ServiceFailureException;

    /**
     * Updates badge in database.
     *
     * @param badge to be updated.
     * @throws IllegalArgumentException when badge is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void updateBadge(Badge badge) throws ServiceFailureException;

    /**
     * Deleted badge from database.
     *
     * @param badge to be deleted.
     * @throws IllegalArgumentException when badge is null.
     * @throws ServiceFailureException when db operation fails.
     */
    void deleteBadge(Badge badge) throws ServiceFailureException;

    /**
     * Returns the badge with given id.
     *
     * @param id primary key of the requested badge.
     * @return requested Badge, null in case no such badge exists.
     * @throws IllegalArgumentException when Id is null.
     * @throws ServiceFailureException when db operation fails.
     */
    Badge findBadge(Long id) throws ServiceFailureException;

    /**
     * Returns list of all Badges.
     *
     * @return list of all badges.
     * @throws ServiceFailureException when db operation fails.
     */
    List<Badge> getAllBadges() throws ServiceFailureException;
}
