package com.catalog.models;
import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Student {
    private String username;
    private String name;
    private String group;

    public Student(String username, String name, String group) {
        this.username = username;
        this.name = name;
        this.group = group;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{");
        sb.append("username=").append(username);
        sb.append(", name='").append(name).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
