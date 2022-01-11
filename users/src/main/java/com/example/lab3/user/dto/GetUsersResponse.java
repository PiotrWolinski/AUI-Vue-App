package com.example.lab3.user.dto;

import com.example.lab3.user.entity.User;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        private String login;
    }

    @Singular
    private List<User> users;

    public static Function<Collection<com.example.lab3.user.entity.User>, GetUsersResponse> entityToDto() {
        return characters -> {
            GetUsersResponseBuilder response = GetUsersResponse.builder();
            characters.stream()
                    .map( user -> User.builder()
                            .login(user.getLogin())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}
