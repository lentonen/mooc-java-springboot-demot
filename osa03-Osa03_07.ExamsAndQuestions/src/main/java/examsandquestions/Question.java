package examsandquestions;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question extends AbstractPersistable<Long> {

    @ManyToMany(mappedBy = "questions")
    private List<Exam> exams;

    private String title;
    private String content;

    public Question(String title, String content){
        this.title=title;
        this.content=content;
    }

}
