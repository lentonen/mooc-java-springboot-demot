package scoreservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Henri
 */
@RestController
public class ScoreController {
    
    @Autowired
    private ScoreRepository scoreRepository;
    
    @Autowired
    private GameRepository gameRepository;

    
    @PostMapping("/games/{name}/scores")
    public Score postScore(@RequestBody Score score, @PathVariable String name) {
        score.setGame(gameRepository.findByName(name));
        scoreRepository.save(score);
        return score;
    }
    
    @GetMapping("/games/{name}/scores")
    public List<Score> ListScores(@PathVariable String name) {   
        return scoreRepository.findByGame(gameRepository.findByName(name));
    }
    
    
    @GetMapping("/games/{name}/scores/{id}")
    public Score getScore(@PathVariable String name,@PathVariable Long id) {
        Score score = scoreRepository.findByGameAndId(gameRepository.findByName(name), id);
        return score;
    }
   
    
    @DeleteMapping("/games/{name}/scores/{id}")
    public Score deleteScore(@PathVariable String name,@PathVariable Long id) {
        Score score = scoreRepository.findByGameAndId(gameRepository.findByName(name), id);
        scoreRepository.delete(score);
        return score;
    } 
}

