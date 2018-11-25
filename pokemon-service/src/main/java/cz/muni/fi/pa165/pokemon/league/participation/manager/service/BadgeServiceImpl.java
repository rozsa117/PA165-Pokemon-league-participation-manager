package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.DAOExceptionWrapper;

import javax.inject.Inject;
import java.time.LocalDate;
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
    public void createBadge(Badge badge) {
        DAOExceptionWrapper.withoutResult((
        ) -> badgeDAO.createBadge(badge), "Creation of the following badge failed: " + badge.toString());
    }

    @Override
    public void removeBadge(Badge badge) {
        DAOExceptionWrapper.withoutResult(
                () -> badgeDAO.deleteBadge(badge), "Could not remove the follwoing badge: " + badge.toString());
    }

    @Override
    public void changeBadgeStatus(Badge badge, ChallengeStatus newStatus) {
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
}
