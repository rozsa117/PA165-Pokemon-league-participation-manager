/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.common;

/**
 * Exception is thrown in case there is some problem with id. Ex. id is set 
 * before creating the db object.
 * 
 * @author Tamás Rózsa 445653
 */
public class IDException extends RuntimeException {

    public IDException() {
    }

    public IDException(String message) {
        super(message);
    }
    
}
