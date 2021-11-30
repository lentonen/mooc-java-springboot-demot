
package weatherservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    LocationRepository locationRepository;
    
    @Cacheable("locations")
    public List<Location> list(){
        List<Location> locations = locationRepository.findAll();
        return locations;
    }
    
    @Cacheable("locations")
    public Location getOne(Long id) {
        return locationRepository.getOne(id);
    }
    
    @CacheEvict(value="locations", allEntries=true)
    public void add(Location location) {
        locationRepository.save(location);
    }
    
    @CacheEvict(value="locations", allEntries=true)
    public void flush() {
        //
    }
}
