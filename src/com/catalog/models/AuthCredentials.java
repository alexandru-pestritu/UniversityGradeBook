package com.catalog.models;
import lombok.Getter;

public @Getter class AuthCredentials {
    private final String username;
    private final String password;

    public AuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
