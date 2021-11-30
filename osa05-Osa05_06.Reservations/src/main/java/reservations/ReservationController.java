package reservations;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {
    
    @Autowired
    ReservationRepository reservationRepository;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping("/reservations")
    public String show(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        
        // Luodaan testikäyttäjä
        if (accountRepository.findByUsername("admin") != null) {
            return "reservations";
        }
        Account a = new Account("admin", passwordEncoder.encode("admin"));
        accountRepository.save(a);
        return "reservations";
    }

    
    @PostMapping("/reservations")
    public String addReservation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationTo) {
        
        //Tapa 1
        
        if (reservationFrom != null && reservationTo != null &&
                reservationRepository.findOverlappingReservations(reservationFrom, reservationTo).isEmpty()) {
             // Katsotaan kuka on sisällä
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Account account = accountRepository.findByUsername(username);
             // Luodaan uusi varaus
            Reservation varaus = new Reservation(account, reservationFrom, reservationTo);
             reservationRepository.save(varaus);
        }
        
        // TAPA 2
        
        /*
        // Katsotaan kuka on sisällä
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        // Vanhat varaukset listaan
        List<Reservation> varaukset = reservationRepository.findAll();
        
        // Luodaan uusi varaus
        Reservation varaus = new Reservation(account, reservationFrom, reservationTo);
        if (reservationFrom == null || reservationTo == null) {
            return "redirect:/reservations";
        }
        
        //katsotaan meneekö päälle
        boolean meneePäälle = false;
       
        for (Reservation res : varaukset) {
            if (varaus.getReservationFrom().isBefore(res.getReservationTo()) && varaus.getReservationFrom().isAfter(res.getReservationFrom()) || 
                varaus.getReservationTo().isBefore(res.getReservationTo()) && varaus.getReservationTo().isAfter(res.getReservationFrom()) ||
                varaus.getReservationFrom().isEqual(res.getReservationFrom()) || varaus.getReservationFrom().isEqual(res.getReservationTo()) ||  
                varaus.getReservationTo().isEqual(res.getReservationFrom()) || varaus.getReservationTo().isEqual(res.getReservationTo()))  {
                meneePäälle = true;
            }
        }
        // Tallennetaan jos ei mene
        if (meneePäälle == false ) {
            reservationRepository.save(varaus);
        }
        */
        return "redirect:/reservations";
    }
   

}
