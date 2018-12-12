package cz.muni.fi.pa165.pokemon.league.participation.manager.utils;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Gym;

/**
 * Class that combines a gym with a badge, useful for retrieving all gyms while
 * also adding a trainers badge to those, which the trainer challenged.
 *
 * @author Tibor Zauko 433531
 */
public class GymAndBadge {
    
    private Gym gym;
    
    private Badge badge;

    /**
     * Construct this class.
     *
     * Either gym or badge can be null, but not both at the same time.
     *
     * @param gym The gym.
     * @param badge The badge.
     * @throws IllegalArgumentException when both gym and badge are null.
     */
    public GymAndBadge(Gym gym, Badge badge) {
        if (gym == null && badge == null) {
            throw new IllegalArgumentException("Gym and Badge can't both be null");
        }
        this.gym = gym;
        this.badge = badge;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
    
}
