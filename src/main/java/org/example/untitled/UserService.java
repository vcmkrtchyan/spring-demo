package org.example.untitled;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final Map<String, User> users = new HashMap<>();

    public UserService() {
        users.put("admin", new User("admin", "admin", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    public User registerUser(String username, String password) {
        if (users.containsKey(username)) {
            throw new RuntimeException("Duplicate username");
        }

        User user = new User(username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        users.put(username, user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return user;
    }

}
