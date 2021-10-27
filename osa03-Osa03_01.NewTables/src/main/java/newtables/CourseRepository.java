package newtables;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
// tehty testaamista varten, ei vaadittu tehtävänannossa
public interface CourseRepository extends JpaRepository<Course, Long> {
 
}