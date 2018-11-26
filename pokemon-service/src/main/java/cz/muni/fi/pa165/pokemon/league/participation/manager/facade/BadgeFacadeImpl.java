package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BadgeService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import org.dozer.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Badge facade interface
 *
 * @author Michal Mokros 456442
 */
@Service
@Transactional
public class BadgeFacadeImpl implements BadgeFacade {

    @Inject
    private BadgeService badgeService;

    @Inject
    private GymService gymService;

    @Inject
    private TrainerService trainerService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public void createBadge(BadgeCreateDTO badge) {
        badgeService.createBadge(beanMappingService.mapTo(badge, Badge.class));
    }

    @Override
    public BadgeDTO findBadgeById(Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id), BadgeDTO.class);
    }

    @Override
    public GymDTO getGymOfBadge(Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id).getGym(), GymDTO.class);
    }

    @Override
    public TrainerDTO getTrainerOfBadge(Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id).getTrainer(), TrainerDTO.class);
    }

    @Override
    public void revokeBadge(BadgeStatusChangeDTO badge) {
        badgeService.changeBadgeStatus(badgeService.findBadgeById(badge.getBadgeId()), ChallengeStatus.REVOKED);
    }

    @Override
    public void issueBadgeToTrainer(Long trainerId, BadgeDTO badge) {
        badge.setTrainer(beanMappingService.mapTo(trainerService.getTrainerWithId(trainerId), TrainerDTO.class));
    }

    @Override
    public void reopenChallenge(Long trainerId, BadgeStatusChangeDTO badge)
            throws InsufficientRightsException {
        if (trainerId.equals(badge.getTrainerId()) ||
                !badgeService.findBadgeById(badge.getBadgeId()).getStatus().equals(ChallengeStatus.LOST)) {
            throw new InsufficientRightsException("Trainer " + trainerId + " tried to reopen badge not belonging to him");
        }

        badgeService.changeBadgeStatus(badgeService.findBadgeById(badge.getBadgeId()), ChallengeStatus.WAITING_TO_ACCEPT);
    }

    @Override
    public void updateBadgeStatus(Long trainerId, BadgeStatusChangeDTO badge) throws InsufficientRightsException {
        Gym gym = badgeService.findBadgeById(badge.getBadgeId()).getGym();

        if (!gym.getGymLeader().equals(trainerService.getTrainerWithId(trainerId))) {
            throw new InsufficientRightsException("Trainer " + trainerId + " is not leader of gym " + gym.getId());
        }

        badgeService.changeBadgeStatus(badgeService.findBadgeById(badge.getBadgeId()), badge.getNewStatus());
    }
}
