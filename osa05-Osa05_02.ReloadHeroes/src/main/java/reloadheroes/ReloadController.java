package reloadheroes;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReloadController {

    @Autowired
    private ReloadStatusRepository reloadStatusRepository;

    @Autowired
    private HttpSession session;

    @RequestMapping("*")
    public String reload(Model model) {
        int visits = 0;
        ReloadStatus r = new ReloadStatus("name " +UUID.randomUUID(), visits);;
        if (session.getAttribute("count") != null) {
            r = (ReloadStatus) session.getAttribute("reloadstatus");
            r = reloadStatusRepository.findByName(r.getName());
            visits = (int) r.getReloads();
        }
        visits++;
        r.setReloads(visits);
        session.setAttribute("count", visits);
        session.setAttribute("reloadstatus", r);
        reloadStatusRepository.save(r);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("reloads").descending());
        
        model.addAttribute("status", r);
        model.addAttribute("scores", reloadStatusRepository.findAll(pageable));
        return "index";
    }
}

