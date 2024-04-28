package com.catalog.data;
import com.catalog.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import lombok.Getter;

public class DataRepository {
    private static final DataRepository instance = new DataRepository();

    private @Getter List<Student> students;
    private @Getter List<Teacher> teachers;
    private @Getter List<Course> courses;

    private @Getter Map<String, AuthCredentials> authStudentsCredentialsMap;
    private @Getter Map<String, AuthCredentials> authTeachersCredentialsMap;

    private DataRepository() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
        authStudentsCredentialsMap = new HashMap<>();
        authTeachersCredentialsMap = new HashMap<>();
    }

    public static DataRepository getInstance() {
        return instance;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudentAuthCredentials(AuthCredentials authCredentials) {
        authStudentsCredentialsMap.put(authCredentials.getUsername(), authCredentials);
    }

    public void addTeacherAuthCredentials(AuthCredentials authCredentials) {
        authTeachersCredentialsMap.put(authCredentials.getUsername(), authCredentials);
    }

    public Teacher findTeacherByUsername(String username) {
        for (Teacher teacher : teachers) {
            if (teacher.getUsername().equals(username)) {
                return teacher;
            }
        }
        return null;
    }

    public Student findStudentByUsername(String username) {
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }
}
