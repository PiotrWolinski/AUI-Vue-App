package com.example.lab3.user.dto;

import com.example.lab3.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    private String login;

    private String name;

    private String surname;

    private String email;

    private LocalDate birthDate;

    public static Function<User, GetUserResponse> entityToDto() {
        return user -> GetUserResponse.builder()
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
    }
}
