package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.configuration.security.JWTRequest;
import com.ftn.anticancerdrugrecord.configuration.security.JWTResponse;
import com.ftn.anticancerdrugrecord.configuration.security.JWTTokenUtil;
import com.ftn.anticancerdrugrecord.dto.user.UserDTO;
import com.ftn.anticancerdrugrecord.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping(value = "/login")
    public String logIn(@ModelAttribute("loginData") JWTRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        //ResponseEntity.ok(new JWTResponse(token))
        return (userDetails != null) ? "profile_view" : "";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        final var addedUser = userDetailService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
