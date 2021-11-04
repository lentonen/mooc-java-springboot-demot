/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Henri
 */
@Service
public class VoteService {

    /**
     * Äänestää tiettyä vitsiä
     * @param id vitsi jolle ääni annetaan
     * @param value äänen arvo
     */
    public void vote(Long id, String value) {
        Vote vote = this.voteRepository.findByJokeId(id);
        if (vote == null) {
            vote = new Vote(id, 0, 0);
        }
        if ("up".equals(value)) {
            vote.setUpVotes(vote.getUpVotes() + 1);
        } else if ("down".equals(value)) {
            vote.setDownVotes(vote.getDownVotes() + 1);
        }
        voteRepository.save(vote);
    }
    
    @Autowired
    private VoteRepository voteRepository;
    
    
    /**
     * Kertoo onko vitsillä ääniä
     * @param jokeId
     * @return true jos ääniä on, false jos ei ole
     */
    public boolean hasVote(Long jokeId){
        if (voteRepository.findByJokeId(jokeId) == null) return false;
        return true;
    }

    
    /**
     * Tallentaa äänen tietokantaan
     * @param v tallennettava ääni
     */
    void save(Vote v) {
        voteRepository.save(v);
    }

    public Vote findByJokeId(Long jokeId) {
        return voteRepository.findByJokeId(jokeId);
    }

    /**
     * Tarkistaa onko vitsille luoto vote-oliota. Luo uuden vote-olion jos ei ole luotu
     * @param jokeId vitsi jolle vote-olio luodaan
     */
    void tarkistaVotet(Long jokeId) {
        if (voteRepository.findByJokeId(jokeId) == null) {
            Vote v = new Vote(jokeId, 0, 0);
            voteRepository.save(v);
        }
    }
    
}
