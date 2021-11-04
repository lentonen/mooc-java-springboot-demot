package filemanager;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    
    @Autowired
    private FileRepository fileRepository; 

    @GetMapping("/files")
    public String show(Model model){
        model.addAttribute("files", this.fileRepository.findAll());
        return "files";
    }

    @PostMapping("/files/{id}")
    public String delete(@PathVariable Long id) throws IOException {
        this.fileRepository.delete(this.fileRepository.getOne(id));
        return "redirect:/";
    }
    
    
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        /** Näitä ei tarvita erikseen. file viedään jo ohjelmaan /files vaiheessa.
        FileObject fo = this.fileRepository.getOne(id);
        model.addAttribute("file.id", fo.getId());
        model.addAttribute("file.name", fo.getName());
        model.addAttribute("file.contentLength", fo.getContentLength());
        model.addAttribute("file.contentType", fo.getContentType());
        **/
        FileObject fo2 = fileRepository.getOne(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo2.getContentType()));
        headers.setContentLength(fo2.getContentLength());
        
        headers.add("Content-Disposition", "attachment; filename=" + fo2.getName());
        
        return new ResponseEntity<>(fo2.getContent(), headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/files")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        FileObject fo = new FileObject();
        
        fo.setName(file.getOriginalFilename());
        fo.setContentType(file.getContentType());
        fo.setContentLength(file.getSize());
        fo.setContent(file.getBytes());
        fileRepository.save(fo);
        
        return "redirect:/files";
    }
}
