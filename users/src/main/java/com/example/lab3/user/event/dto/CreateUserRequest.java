package com.example.lab3.user.event.dto;

import com.example.lab3.user.entity.User;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    private String login;

    public static Function<User, CreateUserRequest> entityToDto() {
        return entity -> CreateUserRequest.builder()
                .login(entity.getLogin())
                .build();
    }
}
