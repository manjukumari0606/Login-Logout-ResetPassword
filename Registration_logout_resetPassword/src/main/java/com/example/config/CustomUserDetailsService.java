package com.example.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(String roles) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }
    
   
}