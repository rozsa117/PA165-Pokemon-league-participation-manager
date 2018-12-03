package cz.muni.fi.pa165.pokemon.league.participation.manager.service;

import com.github.dozermapper.core.Mapper;
import java.util.Collection;
import java.util.List;


/**
 * Interface of mapping service to map objects in facade layer to another type.
 * 
 * @author Tamás Rózsa
 */
public interface BeanMappingService {
    
    /**
     * Returns the mapper.
     * 
     * @return mapper 
     */
    public Mapper getMapper(); 

    /**
     * Maps one object to the requested one.
     * 
     * @param <T> Type to map the object.
     * @param obj The object to map.
     * @param mapToClass Class to map to.
     * @return The mapped class.
     */
    public  <T> T mapTo(Object obj, Class<T> mapToClass);
    
    /**
     * Maps a list of objects to requested type.
     * 
     * @param <T> The type to map the objects.
     * @param objects The objects to map.
     * @param mapToClass Class to map to.
     * @return Lost of mapped classes.
     */
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
}
