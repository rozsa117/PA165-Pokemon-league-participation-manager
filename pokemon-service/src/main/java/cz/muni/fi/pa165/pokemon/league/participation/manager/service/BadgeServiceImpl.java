package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.DAOExceptionWrapper;
import java.time.LocalDate;

import javax.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementation of Badge Service
 *
 * @author Michal Mokros 456442
 */
@Service
public class BadgeServiceImpl implements BadgeService {

    @Inject
    private BadgeDAO badgeDAO;

    @Override
    public void createBadge(Badge badge) throws EntityIsUsedException {
        if (badge.getGym().getGymLeader().equals(badge.getTrainer())) {
            throw new EntityIsUsedException("Challenger is gym leader");
        }
        DAOExceptionWrapper.withoutResult((
        ) -> badgeDAO.createBadge(badge), "Creation of the following badge failed: " + badge.toString());
    }

    @Override
    public void removeBadge(Badge badge) {
        DAOExceptionWrapper.withoutResult(
                () -> badgeDAO.deleteBadge(badge), "Could not remove the follwoing badge: " + badge.toString());
    }

    @Override
    public void changeBadgeStatus(Badge badge, ChallengeStatus newStatus) throws InvalidChallengeStatusChangeException {
        if (badge.getStatus() == newStatus)
            return;
        if (!isValidStatusChange(badge.getStatus(), newStatus)) {
            throw new InvalidChallengeStatusChangeException("Tried to change status from " + badge.getStatus() + " to "
                    + newStatus);
        }
        badge.setStatus(newStatus);
        DAOExceptionWrapper.withoutResult(
                () -> badgeDAO.updateBadge(badge), "Could not update the following badge: " + badge.toString());
    }
        

    @Override
    public Badge findBadgeById(Long id) {
        return DAOExceptionWrapper.withResult(
                () -> badgeDAO.findBadgeById(id), "Could not find the badge with id " + id);
    }

    @Override
    public List<Badge> findBadgesOfTrainer(Trainer trainer) {
        return DAOExceptionWrapper.withResult(
                () -> badgeDAO.findBadgesOfTrainer(trainer), "Could not find the badges of " + trainer.toString());
    }

    @Override
    public List<Badge> findBadgesOfGym(Gym gym) {
        return DAOExceptionWrapper.withResult(
                () -> badgeDAO.findBadgesOfGym(gym), "Could not find the badges of " + gym.toString());
    }
        
    private boolean isValidStatusChange(ChallengeStatus oldStatus, ChallengeStatus newStatus) {
        switch(oldStatus) {
            case WON:
                return ChallengeStatus.REVOKED.equals(newStatus);
            case LOST:
                return ChallengeStatus.WAITING_TO_ACCEPT.equals(newStatus);
            case WAITING_TO_ACCEPT:
                return ChallengeStatus.WON.equals(newStatus) || ChallengeStatus.LOST.equals(newStatus);
            case REVOKED:
                return ChallengeStatus.WON.equals(newStatus);
            default:
                throw new IllegalArgumentException("Old status is not valid.");
        }
    }
}
