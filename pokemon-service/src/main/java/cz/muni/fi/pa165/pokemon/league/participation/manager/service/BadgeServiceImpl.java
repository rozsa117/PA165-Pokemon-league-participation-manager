package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dao.BadgeDAO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Set;

/**
 * Implementation of Badge Service
 *
 * @author Michal Mokros 456442
 */
public class BadgeServiceImpl implements BadgeService {

    @Inject
    private BadgeDAO badgeDAO;

    @Override
    public void createBadge(Badge badge) {
        badgeDAO.createBadge(badge);
    }

    @Override
    public void removeBadge(Badge badge) {
        badgeDAO.deleteBadge(badge);
    }

    @Override
    public void changeBadgeStatus(Badge badge, ChallengeStatus newStatus) {
        badge.setStatus(newStatus);
        badgeDAO.updateBadge(badge);
    }

    @Override
    public Badge findBadgeById(Long id) {
        return badgeDAO.findBadgeById(id);
    }

    @Override
    public Set<Badge> findBadgesOfTrainer(Trainer trainer) {
        return trainer.getBadges();
    }

    @Override
    public Set<Badge> findBadgesOfGym(Gym gym) {
        return gym.getBadges();
    }
}
