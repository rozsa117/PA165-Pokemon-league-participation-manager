package cz.muni.fi.pa165.pokemon.league.participation.manager.rest.controllers;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Internationalize custom validation messages.
 *
 * @author Tibor Zauko 433531
 */
public class I18n {
  
    /**
     * Gets localized message for key, or returns key if it doesn't exist.
     *
     * @param key Key to fetch.
     * @return Localized value if the key exists, otherwise it returns the key.
     */
    public static String getLocalizedMessageOrReturnKey(String key) {
        ResourceBundle messages = ResourceBundle.getBundle("ContributorValidationMessages", LocaleContextHolder.getLocale());
        return messages.containsKey(key) ? messages.getString(key) : key;
    }
    
}
