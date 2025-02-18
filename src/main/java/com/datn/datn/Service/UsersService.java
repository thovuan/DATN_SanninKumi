package com.datn.datn.Service;

import com.datn.datn.Model.Users;
import com.datn.datn.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    // Constructor Injection for PasswordEncoder
    public UsersService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String storedHashPassword) {
        return passwordEncoder.matches(rawPassword, storedHashPassword);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(String id) {
        return usersRepository.getReferenceById(id);
    }

    public Users RegisterUser(Users user) {
        return usersRepository.save(user);
    }



}
