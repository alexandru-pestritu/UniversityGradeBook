package com.catalog.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public @Getter class Teacher {
    private @Setter String username;
    private @Setter String name;

    public Teacher(String username, String name) {
        this.username=username;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Teacher{");
        sb.append("username=").append(username);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
