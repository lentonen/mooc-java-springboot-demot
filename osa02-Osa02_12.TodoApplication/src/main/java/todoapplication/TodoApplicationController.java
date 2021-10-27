package todoapplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class TodoApplicationController {

    @Autowired
    private TodoRepository todoRepository;


    @PostMapping("/")
    public String post(@RequestParam String name) {
        todoRepository.save(new Item(name));
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", todoRepository.findAll());
        return "index";
    }
    
    @GetMapping("/{id}")
    public String getOne(Model model, @PathVariable Long id) {
        Item item = todoRepository.getOne(id).kasvataCounter();
        item = todoRepository.save(item);
        model.addAttribute("item", todoRepository.getOne(id));
        return "todo";
    }
}
