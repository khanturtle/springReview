package com.review.springreview.signup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String nickname;

    @Column
    @NotNull
    private String password;

    @Column
    private UserRoleEnum role;

    public User(String nickname, String password) {
        this.nickname=nickname;
        this.password=password;
    }
}
