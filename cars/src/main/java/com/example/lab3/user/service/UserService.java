package com.example.lab3.user.service;

import com.example.lab3.user.entity.User;
import com.example.lab3.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) { this.repository = repository; }

    public Optional<User> find(String login) { return repository.findById(login); }

    @Transactional
    public User create(User user) { return repository.save(user); }

    @Transactional
    public void delete(String login) { repository.deleteById(login); }


}
