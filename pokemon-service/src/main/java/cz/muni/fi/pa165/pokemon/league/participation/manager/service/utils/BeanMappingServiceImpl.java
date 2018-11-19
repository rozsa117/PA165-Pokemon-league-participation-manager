
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

import org.dozer.Mapper;
import cz.muni.fi.pa165.pokemon.league.participation.manager.service.utils.BeanMappingService;

/**
 * Implementation of interface beam mapping service
 * 
 * @author Tamás Rózsa 445653
 */
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public <T> T mapTo(Object obj, Class<T> mapToClass) {
        return mapper.map(obj, mapToClass);
    }
    
    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        return objects.stream().map((obj) -> mapper.map(obj, mapToClass)).collect(Collectors.toList());
    }
}
