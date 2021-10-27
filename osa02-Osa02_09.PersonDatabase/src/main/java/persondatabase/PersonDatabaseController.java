package persondatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonDatabaseController {


    @Autowired
    private PersonRepository PersonRepository;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("persons", this.PersonRepository.findAll());
        return "index";
    }
    
    @PostMapping("/")
    public String add(@RequestParam String name) {
        this.PersonRepository.save(new Person(name));
        return "redirect:/";
    }

}
