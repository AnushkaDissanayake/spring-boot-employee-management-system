package com.gtngroup.ems.controller;

import com.gtngroup.ems.dto.AuthResponseDTO;
import com.gtngroup.ems.dto.LoginDTO;
import com.gtngroup.ems.dto.RegisterDTO;
import com.gtngroup.ems.entity.Role;
import com.gtngroup.ems.entity.UserEntity;
import com.gtngroup.ems.repo.RoleRepo;
import com.gtngroup.ems.repo.UserRepo;
import com.gtngroup.ems.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator tokenGenerator;
    @Autowired

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepo userRepo, RoleRepo roleRepo,
                          PasswordEncoder passwordEncoder, JWTGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        if (userRepo.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Role roles = roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepo.save(user);
        return new ResponseEntity<>("Register successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
