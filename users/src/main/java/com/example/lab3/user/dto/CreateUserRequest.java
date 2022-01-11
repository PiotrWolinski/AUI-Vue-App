package com.example.lab3.user.dto;

import com.example.lab3.digest.Sha256Utility;
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
public class CreateUserRequest {
    private String login;

    private String name;

    private String surname;

    private String email;

    private String password;

    private LocalDate birthDate;

    public static Function<CreateUserRequest, User> dtoToEntity() {
        return request -> User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .password(Sha256Utility.hash(request.getPassword()))
                .build();
    }
}
