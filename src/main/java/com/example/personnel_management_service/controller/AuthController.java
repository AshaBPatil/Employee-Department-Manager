package com.example.personnel_management_service.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class); // Added Logger for better debugging
    private final AuthenticationManager authManager;
    private final RSAPrivateKey privateKey;
    private final UserDetailsService userDetailsService;

    // Constructor injection to get the required beans
    public AuthController(AuthenticationManager authManager, RSAPrivateKey privateKey, UserDetailsService uds) {
        this.authManager = authManager;
        this.privateKey = privateKey;
        this.userDetailsService = uds;
    }

    // Endpoint for generating JWT token
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticating the user with provided credentials
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    authRequest.username(), authRequest.password()
            );

            // This will throw exception if authentication fails
            Authentication result = authManager.authenticate(auth);

            // Getting the user details
            UserDetails user = (UserDetails) result.getPrincipal();

            // Generate JWT token
            String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", user.getAuthorities().stream()
                            .map(a -> a.getAuthority().replace("ROLE_", ""))
                            .toList())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plusSeconds(3600)))  // Token expires in 1 hour
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                    .compact();

            // Log the generated token for debugging (You can disable or remove in production)
            logger.info("Generated JWT Token for user: {}", user.getUsername());

            // Return the token as a response
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            // Invalid credentials
            logger.error("Invalid credentials for username: {}", authRequest.username());
            return ResponseEntity.status(401).body("Invalid credentials");

        } catch (AuthenticationException e) {
            // Authentication failure
            logger.error("Authentication failed for username: {}", authRequest.username(), e);
            return ResponseEntity.status(401).body("Authentication failed");

        } catch (Exception e) {
            // Catch other unexpected exceptions
            logger.error("Unexpected error occurred during token generation", e);
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    // Record to capture the login request (username and password)
    public record AuthRequest(@NotBlank String username, @NotBlank String password) {}
}
