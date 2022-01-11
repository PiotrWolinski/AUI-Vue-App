package com.example.lab3.user.service;

import com.example.lab3.digest.Sha256Utility;
import com.example.lab3.user.entity.User;
import com.example.lab3.user.event.repository.UserEventRepository;
import com.example.lab3.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    private UserEventRepository eventRepository;

    @Autowired
    public UserService(UserRepository repository, UserEventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    public Optional<User> find(String login) { return repository.findById(login); }

    public Optional<User> find(String login, String password) { return repository.findByLoginAndPassword(login, Sha256Utility.hash(password)); }

    public List<User> findAll() { return repository.findAll(); }

    @Transactional
    public void create(User user) {
        repository.save(user);
        eventRepository.create(user);
    }

    @Transactional
    public void delete(User user) {
        repository.delete(user);
        eventRepository.delete(user);
    }

    @Transactional
    public void update(User user) { repository.save(user); }

}
