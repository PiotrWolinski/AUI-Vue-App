package com.example.lab3.user.event.repository;

import com.example.lab3.user.entity.User;
import com.example.lab3.user.event.dto.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class UserEventRepository {

    private RestTemplate template;

    @Autowired
    public UserEventRepository(@Value("${app.cars.url}") String baseUrl) {
        template = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public void delete(User user) { template.delete("/users/{username}", user.getLogin()); }

    public void create(User user) { template.postForLocation("/users", CreateUserRequest.entityToDto().apply(user)); }
}
