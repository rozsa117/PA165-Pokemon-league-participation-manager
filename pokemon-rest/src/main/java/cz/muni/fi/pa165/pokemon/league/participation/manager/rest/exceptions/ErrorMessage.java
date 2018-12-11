package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.exceptions;

/**
 * Error message to be returned by Rest API.
 *
 * @author Jiří Medveď 38451
 */
public class ErrorMessage {
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" + "error=" + error + '}';
    }

}
