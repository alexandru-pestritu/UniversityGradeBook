package com.catalog.models;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.HashMap;

public @Getter class Course {
    private @Setter String shortName;
    private @Setter String name;
    private @Setter Teacher teacher;
    private @Setter String year;
    private Map<Student,Grade> students;

    public Course(String shortName, String name, Teacher teacher, String year) {
        this.shortName= shortName;
        this.name = name;
        this.teacher = teacher;
        this.year = year;
        this.students = new HashMap<>();
    }

    @Override
    public String toString() {
        return name + " - " +
                "Profesor: " + teacher.getName() +
                ", Studenti: " + students.size();
    }
}
