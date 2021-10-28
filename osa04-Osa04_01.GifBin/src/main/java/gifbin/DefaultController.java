package gifbin;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DefaultController {

    @Autowired
    private GifRepository gifRepository;
    
    @RequestMapping("/")
    public String redirect() {
        return "redirect:/gifs";
    }
    
    @GetMapping("/gifs")
    public String show() {
        return "redirect:/gifs/1";
    }
    
    @GetMapping("/gifs/{id}")
    public String showGif(Model model, @PathVariable Long id) {
        model.addAttribute("count", this.gifRepository.count());
       
        if (this.gifRepository.existsById(id)) {
            model.addAttribute("current", this.gifRepository.getOne(id).getId());
        }
        
        for (Long i = id-1; i >= 0; i--) {
            if (this.gifRepository.existsById(i)){
                FileObject fo = this.gifRepository.getOne(i);
                model.addAttribute("previous", fo.getId());
                break;  
            }
        }
            
        for (Long i = id+1; i <= id+1000; i++) {
            if (this.gifRepository.existsById(i)){
                FileObject fo = this.gifRepository.getOne(i);
                model.addAttribute("next", fo.getId());
                break;  
            }
        }        
       
        //if (this.gifRepository.existsById(id+1))
        //    model.addAttribute("next", this.gifRepository.getOne(id+1).getId());

        return "gifs";
    }
    
    @GetMapping(path = "/gifs/{id}/content", produces = "image/gif")
    @ResponseBody
    public byte[] showOneGif(@PathVariable Long id){
        return gifRepository.getOne(id).getContent();
    }
    
    @PostMapping("/gifs")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || !file.getContentType().equals("image/gif"))
            return "redirect:/gifs";  
        FileObject fo = new FileObject();
        fo.setContent(file.getBytes());
        gifRepository.save(fo);
        return "redirect:/gifs";
    }
}
