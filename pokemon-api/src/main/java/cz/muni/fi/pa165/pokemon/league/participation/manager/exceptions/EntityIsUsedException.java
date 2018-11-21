/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions;

/**
 * Exception for situations when an entity can't be removed because it is still
 * in use somewhere.
 * 
 * @author Tibor Zauko 433531
 */
public class EntityIsUsedException extends Exception {

    public EntityIsUsedException() {
    }

    public EntityIsUsedException(String msg) {
        super(msg);
    }

    public EntityIsUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}
