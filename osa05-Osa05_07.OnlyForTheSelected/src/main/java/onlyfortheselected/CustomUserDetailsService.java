package onlyfortheselected;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        
        // Haetaan oikeudet listaan
        List<SimpleGrantedAuthority> oikeudet = new ArrayList<SimpleGrantedAuthority>();
        for (String auth : account.getAuthorities()) {
            oikeudet.add(new SimpleGrantedAuthority(auth));
        }

        return new org.springframework.security.core.userdetails.User(
        account.getUsername(),
        account.getPassword(),
        true,
        true,
        true,
        true,
        oikeudet // Tähän tuodaan lista käyttäjän oikeuksista
        );  
    }
}
