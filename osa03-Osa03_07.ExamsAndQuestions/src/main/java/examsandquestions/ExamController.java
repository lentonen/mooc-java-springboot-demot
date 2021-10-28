package examsandquestions;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExamController {
    
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping("/exams")
    public String listExams(Model model) {
        model.addAttribute("exams", this.examRepository.findAll());
        return "exams";
    }

    @GetMapping("/exams/{id}")
    public String viewExam(Model model, @PathVariable Long id) {
        model.addAttribute("exam", this.examRepository.getOne(id));
        model.addAttribute("questions", this.questionRepository.findAll());

        return "exam";
    }

    @PostMapping("/exams")
    public String addExam(@RequestParam String subject,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {
        
        Exam e = new Exam(subject, examDate);
        examRepository.save(e);

        return "redirect:/exams";
    }

    @PostMapping("/exams/{examId}/questions/{questionId}")
    @Transactional
    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {
        Exam exam = examRepository.getOne(examId);
        Question q = questionRepository.getOne(questionId);
        
        if (!exam.getQuestions().contains(q) && !q.getExams().contains(exam)){
            exam.getQuestions().add(q);
        }
        return "redirect:/exams/" + examId;
    }
}
