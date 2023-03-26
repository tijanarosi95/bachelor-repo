package com.ftn.anticancerdrugrecord.controller;

import com.ftn.anticancerdrugrecord.configuration.security.JWTRequest;
import com.ftn.anticancerdrugrecord.configuration.security.JWTResponse;
import com.ftn.anticancerdrugrecord.configuration.security.JWTTokenUtil;
import com.ftn.anticancerdrugrecord.dto.user.UserDTO;
import com.ftn.anticancerdrugrecord.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> logIn(@RequestBody JWTRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        final var addedUser = userDetailService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping("/profile")
    public ResponseEntity<com.ftn.anticancerdrugrecord.model.user.User> getLoggedUser(@AuthenticationPrincipal User user) {
        var loggedUser = userDetailService.getUserDetails(user.getUsername());
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
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
