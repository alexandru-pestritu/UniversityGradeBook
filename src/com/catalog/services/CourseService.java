package com.catalog.services;
import com.catalog.data.DataRepository;
import com.catalog.models.*;
import java.util.List;
import java.util.Map;

public class CourseService {
    DataRepository dataRepository = DataRepository.getInstance();

    public void addCourse(String shortName, String name, Teacher teacher, String year) {
        Course course = new Course(shortName, name, teacher, year);
        dataRepository.addCourse(course);
    }

    public void deleteCourse(String name) {
        List<Course> courses = dataRepository.getCourses();
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                courses.remove(course);
                break;
            }
        }
    }

    public void updateCourse(String name, String newShortName, String newName, String year) {
        List<Course> courses = dataRepository.getCourses();
        for (Course course : courses) {
            if(course.getName().equals(name)) {
                course.setShortName(newShortName);
                course.setName(newName);
                course.setYear(year);
                break;
            }
        }
    }

    public boolean isStudentEnrolled(String username, String courseName) {
        List<Course> courses = dataRepository.getCourses();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                Map<Student, Grade> students = course.getStudents();
                for (Student student : students.keySet()) {
                    if (student.getUsername().equals(username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addStudentToCourse(String courseName, String username){
        List<Course> courses = dataRepository.getCourses();
        List<Student> students = dataRepository.getStudents();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                for (Student student : students) {
                    if (student.getUsername().equals(username)) {
                        course.getStudents().put(student, new Grade(0));
                        break;
                    }
                }
                break;
            }
        }
    }

    public void deleteStudentFromCourse(String courseName, String username){
        List<Course> courses = dataRepository.getCourses();
        List<Student> students = dataRepository.getStudents();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                for (Student student : students) {
                    if (student.getUsername().equals(username)) {
                        course.getStudents().remove(student);
                        break;
                    }
                }
                break;
            }
        }
    }



}
