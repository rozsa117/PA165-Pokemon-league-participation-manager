package cz.muni.fi.pa165.pokemon.league.participation.manager.facade;

import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.BadgeStatusChangeDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.GymDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;
import cz.muni.fi.pa165.pokemon.league.participation.manager.enums.ChallengeStatus;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.EntityIsUsedException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InsufficientRightsException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.InvalidChallengeStatusChangeException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BadgeService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.GymService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.TrainerService;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.BeanMappingService;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private TrainerService trainerService;
    
    @Inject
    private GymService gymService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createBadge(@Valid BadgeCreateDTO badge) throws NoSuchEntityException, EntityIsUsedException {
        Trainer challenger = trainerService.getTrainerWithId(badge.getTrainerId());
        if (challenger == null) {
            throw new NoSuchEntityException("The given trainer for the badge doesn't exist");
        }
        Gym gym = gymService.findGymById(badge.getGymId());
        if (gym == null) {
            throw new NoSuchEntityException("The given gym for the badge doesn't exist");
        }
        Badge newBadge = beanMappingService.mapTo(badge, Badge.class);
        newBadge.setDate(LocalDate.now());
        newBadge.setStatus(ChallengeStatus.WAITING_TO_ACCEPT);
        newBadge.setTrainer(challenger);
        newBadge.setGym(gym);
        badgeService.createBadge(newBadge);
        return newBadge.getId();
    }

    @Override
    public BadgeDTO findBadgeById(@NotNull Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id), BadgeDTO.class);
    }

    @Override
    public GymDTO getGymOfBadge(@NotNull Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id).getGym(), GymDTO.class);
    }

    @Override
    public TrainerDTO getTrainerOfBadge(@NotNull Long id) {
        return beanMappingService.mapTo(badgeService.findBadgeById(id).getTrainer(), TrainerDTO.class);
    }

    @Override
    public void revokeBadge(@Valid BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, InvalidChallengeStatusChangeException  {
        updateBadgeStatus(badge.getRequestingTrainerId(), badge, ChallengeStatus.REVOKED);
    }

    @Override
    public void loseBadge(@Valid BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, InvalidChallengeStatusChangeException  {
        updateBadgeStatus(badge.getRequestingTrainerId(), badge, ChallengeStatus.LOST);
    }

    @Override
    public void winBadge(BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        updateBadgeStatus(badge.getRequestingTrainerId(), badge, ChallengeStatus.WON);
    }
    
    @Override
    public void reopenChallenge(@Valid BadgeStatusChangeDTO badge)
            throws InsufficientRightsException, NoSuchEntityException, InvalidChallengeStatusChangeException {
        Badge badgeEntity = badgeService.findBadgeById(badge.getBadgeId());
        if (badgeEntity == null) {
            throw new NoSuchEntityException("The requested badge doesn't exist");
        }
        if (!badgeEntity.getTrainer().getId().equals(badge.getRequestingTrainerId())) {
            throw new InsufficientRightsException("Trainer " + badge.getRequestingTrainerId() 
                    + " tried to reopen badge challenge not belonging to him");
        }

        badgeService.changeBadgeStatus(badgeService.findBadgeById(badge.getBadgeId()), ChallengeStatus.WAITING_TO_ACCEPT);
    }

    private void updateBadgeStatus(Long trainerId, BadgeStatusChangeDTO badge, ChallengeStatus status)
            throws InsufficientRightsException, InvalidChallengeStatusChangeException {
        Gym gym = badgeService.findBadgeById(badge.getBadgeId()).getGym();

        if (!gym.getGymLeader().equals(trainerService.getTrainerWithId(trainerId))) {
            throw new InsufficientRightsException("Trainer " + trainerId + " is not leader of gym " + gym.getId());
        }

        badgeService.changeBadgeStatus(badgeService.findBadgeById(badge.getBadgeId()), status);
    }

    @Override
    public List<BadgeDTO> findBadgesOfTrainer(Long trainerId)
            throws NoSuchEntityException {
        Trainer t = trainerService.getTrainerWithId(trainerId);
        if (t == null) {
            throw new NoSuchEntityException("No trainer of requested id exists");
        }
        return beanMappingService.mapTo(badgeService.findBadgesOfTrainer(t), BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> findBadgesOfGym(Long gymId)
            throws NoSuchEntityException {
        Gym g = gymService.findGymById(gymId);
        if (g == null) {
            throw new NoSuchEntityException("No gym of requested id exists");
        }
        return beanMappingService.mapTo(badgeService.findBadgesOfGym(g), BadgeDTO.class);
    }
}
