/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class for ovject trainer.
 * 
 * @author Tamás Rózsa 445653
 */
public class Trainer {
    
    private Long id;
    private String name;
    private String surname;
    private LocalDate born;

    public Trainer() {
    }

    public Trainer(Long id, String name, String surname, LocalDate born) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id  = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    @Override
    public String toString() {
        return "Trainer{ " + "id = " + id + ", name = " + name + ", surname = " + surname + ", born = " + born + " }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trainer other = (Trainer) obj;
        return Objects.equals(this.id, other.id);
    }
}
