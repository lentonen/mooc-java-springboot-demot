package newtables;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
// tehty testaamista varten, ei vaadittu tehtävänannossa
@Controller
public class TestController {
 
    @Autowired
    CourseRepository courseRepository;
 
    @Autowired
    StudentRepository studentRepository;
 
    @GetMapping("/")
    public String test() {
        Course c1 = courseRepository.save(new Course("Wepa", new ArrayList<>()));
        Course c2 = courseRepository.save(new Course("Tikape", new ArrayList<>()));
 
        Student s1 = studentRepository.save(new Student("A", "A", new ArrayList<>()));
        Student s2 = studentRepository.save(new Student("B", "B", new ArrayList<>()));
        Student s3 = studentRepository.save(new Student("C", "C", new ArrayList<>()));
 
        s1.getCourse().add(c1);
        s1.getCourse().add(c2);
        studentRepository.save(s1);
 
        s2.getCourse().add(c1);
        studentRepository.save(s2);
 
        return "index";
    }
}
