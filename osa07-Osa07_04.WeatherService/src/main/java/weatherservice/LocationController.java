package weatherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    @Autowired
    //private LocationRepository locationRepository;
    WeatherService weatherService; 

    //@Cacheable("locations")
    @GetMapping("/locations")
    public String list(Model model) {
        model.addAttribute("locations", weatherService.list()); //locationRepository.findAll());
        return "locations";
    }

    //@Cacheable("locations")
    @GetMapping("/locations/{id}")
    public String view(Model model, @PathVariable Long id) {
        model.addAttribute("location", weatherService.getOne(id));//locationRepository.getOne(id));
        return "location";
    }

    //@CacheEvict(value ={"locations"})
    @PostMapping("/locations")
    public String add(@ModelAttribute Location location) {
        //locationRepository.save(location);
        weatherService.add(location);
        return "redirect:/locations";
    }
    
    @GetMapping("/flushcaches") 
    public String flush(){
       weatherService.flush();
       return "locations";
    }
    
}
