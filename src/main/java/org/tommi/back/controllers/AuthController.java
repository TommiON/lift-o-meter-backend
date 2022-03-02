package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.domain.UserFactory;
import org.tommi.back.payloads.JwtResponse;
import org.tommi.back.payloads.LoginRequest;
import org.tommi.back.payloads.MessageResponse;
import org.tommi.back.payloads.SignupRequest;
import org.tommi.back.security.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;

@CrossOrigin(origins = "http://lift-o-meter-front.herokuapp.com/")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserFactory userFactory;

    @CrossOrigin(origins = "http://lift-o-meter-front.herokuapp.com/")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @CrossOrigin(origins = "http://lift-o-meter-front.herokuapp.com/")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if(userFactory.usernameAlreadyTaken(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Virhe: käyttäjänimi on jo varattu!"));
        }

        userFactory.build(
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getBestBarbellRow(),
                signUpRequest.getBestBenchPress(),
                signUpRequest.getBestDeadlift(),
                signUpRequest.getBestOverheadPress(),
                signUpRequest.getBestSquat()
        );

        return ResponseEntity.ok(new MessageResponse("Uusi käyttäjätunnus lisätty!"));
    }

    @CrossOrigin(origins = "http://lift-o-meter-front.herokuapp.com/")
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        //SecurityContextHolder.clearContext();
        //SecurityContextHolder.setContext(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok(new MessageResponse("Kirjauduttu ulos!"));
    }

    @CrossOrigin(origins = "http://lift-o-meter-front.herokuapp.com/")
    @GetMapping("/currentUser")
    @ResponseBody
    public String currentUserName(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }
}
