package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.controllers;

import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * A simple class that retrieves strings from resource bundle using locale from spring's locale context.
 * 
 * @author Tibor Zauko 433531
 */
public class I18n {
    
    /**
     * Get the string corresponding to given key in spring locale context's locale.
     * 
     * @param key Key whose string should be retrieved.
     * @return The retrieved string, if that is null, return the key.
     */
    public static String getLocalizedMessageOrReturnKey(String key) {
        ResourceBundle messages = ResourceBundle.getBundle("Texts", LocaleContextHolder.getLocale());
        return messages.containsKey(key) ? messages.getString(key) : key;
    }

}
