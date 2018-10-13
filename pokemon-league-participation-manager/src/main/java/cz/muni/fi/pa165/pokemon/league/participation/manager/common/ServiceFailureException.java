/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.common;

/**
 * Exception thrown in case some db operation fails.
 * 
 * @author Tamás Rózsa 445653
 */
public class ServiceFailureException extends RuntimeException {

    public ServiceFailureException() {
    }

    public ServiceFailureException(String message) {
        super(message);
    }
    
}
