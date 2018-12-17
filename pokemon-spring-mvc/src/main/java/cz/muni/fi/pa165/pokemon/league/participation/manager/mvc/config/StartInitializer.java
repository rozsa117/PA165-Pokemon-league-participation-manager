package cz.muni.fi.pa165.pokemon.league.participation.manager.mvc.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Replaces web.xml file.
 *
 * @author Tamás Rózsa 445653
 */
public class StartInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringMvcConfig.class, SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        return new Filter[]{encodingFilter};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
