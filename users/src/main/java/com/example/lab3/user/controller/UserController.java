package com.example.lab3.user.controller;

import com.example.lab3.user.dto.CreateUserRequest;
import com.example.lab3.user.dto.GetUserResponse;
import com.example.lab3.user.dto.GetUsersResponse;
import com.example.lab3.user.dto.UpdateUserRequest;
import com.example.lab3.user.entity.User;
import com.example.lab3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(GetUsersResponse.entityToDto().apply(userService.findAll()));
    }

    @GetMapping("{login}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("login") String login) {
        return userService.find(login)
                .map(user -> ResponseEntity.ok(GetUserResponse.entityToDto().apply(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {
        User user = CreateUserRequest.dtoToEntity().apply(request);

        userService.create(user);

        return ResponseEntity.created(builder.pathSegment("api", "users", "{login}")
                .buildAndExpand(user.getLogin()).toUri()).build();

    }

    @DeleteMapping("{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable("login") String login) {
        Optional<User> user = userService.find(login);

        if (user.isPresent()) {
            userService.delete(user.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{login}")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequest request,
                                           @PathVariable("login") String login) {
        Optional<User> user = userService.find(login);

        if (user.isPresent()) {
            UpdateUserRequest.dtoToEntity().apply(user.get(), request);
            userService.update(user.get());

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
