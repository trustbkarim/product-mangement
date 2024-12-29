package com.product.security;

import com.product.dao.dto.LoginDto;
import com.product.dao.dto.UserDto;
import com.product.dao.entities.UserEntity;
import com.product.dao.repositories.UserRepository;
import com.product.exceptions.InvalidCredentialsException;
import com.product.exceptions.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("L'utilisateur avec cet email existe déjà !");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
    }

    public String authenticateUser(LoginDto loginDto) {

        UserEntity user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Adresse e-mail ou mot de passe invalide !"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("E-mail ou mot de passe invalide !");
        }

        return jwtTokenUtil.generateToken(user.getEmail());
    }

}
