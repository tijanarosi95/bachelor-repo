package com.ftn.anticancerdrugrecord.service;

import com.ftn.anticancerdrugrecord.dto.user.UserDTO;
import com.ftn.anticancerdrugrecord.model.user.User;
import com.ftn.anticancerdrugrecord.model.user.UserRole;
import com.ftn.anticancerdrugrecord.repository.UserRepository;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getUserRole()));
    }

    public User getUserDetails(String username) {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public User addUser(final UserDTO userDTO) {
        final User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserRole(UserRole.valueOf(userDTO.getUserRole()));
        return userRepository.save(user);
    }

    private Set<GrantedAuthority> getAuthorities(UserRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
    }
}
