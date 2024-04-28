package com.catalog.services;
import com.catalog.data.DataRepository;
import com.catalog.data.UserSession;
import com.catalog.models.Course;
import com.catalog.models.Student;
import com.catalog.models.Grade;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class StudentService {
    private DataRepository dataRepository = DataRepository.getInstance();

    public Map<String, Grade> getAllStudentGrades(String username) {
        List<Course> courses = dataRepository.getCourses();
        Map<String, Grade> studentGrades = new HashMap<>();

        for (Course course : courses) {
            Map<Student, Grade> students = course.getStudents();
            for (Student student : students.keySet()) {
                if (student.getUsername().equals(username)) {
                    Grade grade = students.get(student);
                    if(grade.getValue()>0) {
                        studentGrades.put(course.getName(), grade);
                    }
                }
            }
        }
        return studentGrades;
    }

    public double getAverageGrade(String username)
    {
        Map<String,Grade> studentGrades = getAllStudentGrades(username);
        double sum = 0;
        for (String courseName : studentGrades.keySet()) {
            Grade grade = studentGrades.get(courseName);
            sum += grade.getValue();
        }
        return sum / studentGrades.size();
    }

    public Map<String,Grade> getFailedCourses() {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        Map<String,Grade> studentGrades = getAllStudentGrades(username);
        Map<String,Grade> failedCourses = new HashMap<>();
        for (String courseName : studentGrades.keySet()) {
            Grade grade = studentGrades.get(courseName);
            if (grade.getValue() < 5 && grade.getValue() > 0) {
                failedCourses.put(courseName, grade);
            }
        }
        return failedCourses;
    }

    public double getAverageGrade() {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        Map<String,Grade> studentGrades = getAllStudentGrades(username);
        double sum = 0;
        for (String courseName : studentGrades.keySet()) {
            Grade grade = studentGrades.get(courseName);
            sum += grade.getValue();
        }
        return sum / studentGrades.size();
    }

    public double getAverageGrade(Student student) {
        Map<String,Grade> studentGrades = getAllStudentGrades(student.getUsername());
        double sum = 0;
        for (String courseName : studentGrades.keySet()) {
            Grade grade = studentGrades.get(courseName);
            sum += grade.getValue();
        }
        return sum / studentGrades.size();
    }

    public List<Course> getEnrolledCourses() {
        UserSession userSession = UserSession.getInstance();
        String studentUsername = userSession.getUsername();
        List<Course> courses = dataRepository.getCourses();
        List<Course> enrolledCourses = new ArrayList<>();
        for (Course course : courses) {
            Map<Student, Grade> students = course.getStudents();
            for (Student student : students.keySet()) {
                if (student.getUsername().equals(studentUsername)) {
                    enrolledCourses.add(course);
                }
            }
        }
        return enrolledCourses;
    }

    public Grade getGradeForCourse(String courseName) {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        Map<String,Grade> studentGrades = getAllStudentGrades(username);
        return studentGrades.get(courseName);
    }

}
