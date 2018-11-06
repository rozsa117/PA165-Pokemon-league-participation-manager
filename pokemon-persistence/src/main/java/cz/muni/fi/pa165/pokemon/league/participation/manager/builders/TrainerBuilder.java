package cz.muni.fi.pa165.pokemon.league.participation.manager.builders;

import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Trainer;

import java.time.LocalDate;

/**
 * Builder class for Trainer objects.
 *
 * @author Michal Mokros 456442
 */
public class TrainerBuilder {

    private Long id = null;
    private String passwordHash = null;
    private String userName = null;
    private String name = null;
    private String surname = null;
    private LocalDate born = null;
    private boolean isAdmin = false;

    public Trainer build() {
        Trainer trainer = new Trainer(id);
        trainer.setPasswordHash(passwordHash);
        trainer.setUserName(userName);
        trainer.setName(name);
        trainer.setSurname(surname);
        trainer.setBorn(born);
        trainer.setAdmin(isAdmin);
        return trainer;
    }

    public TrainerBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TrainerBuilder passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public TrainerBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public TrainerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TrainerBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public TrainerBuilder born(LocalDate born) {
        this.born = born;
        return this;
    }

    public TrainerBuilder isAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
}
