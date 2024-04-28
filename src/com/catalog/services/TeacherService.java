package com.catalog.services;
import com.catalog.models.*;
import com.catalog.data.DataRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Map;


public class TeacherService {
    private StudentService studentService = new StudentService();
    DataRepository dataRepository = DataRepository.getInstance();

    public boolean isTeacherAssignedToCourse(String username, String courseName) {
                for (Course course : dataRepository.getCourses()) {
                    if (course.getName().equals(courseName)&&course.getTeacher().getUsername().equals(username)) {
                        return true;
                    }
                }
        return false;
    }

    public List<Course> getCoursesSortedByName(){
        List<Course> sortedCourses = new ArrayList<>(dataRepository.getCourses());
        sortedCourses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return sortedCourses;
    }

    public void addCourse(String shortName, String name, Teacher teacher, String year) {
        Course course = new Course(shortName, name, teacher, year);
        dataRepository.addCourse(course);
    }

    public void deleteCourse(String courseName) {
        for (Course course : dataRepository.getCourses()) {
            if (course.getName().equals(courseName)) {
                dataRepository.getCourses().remove(course);
                break;
            }
        }
    }

    public List<Student> getAllStudents() {
        return dataRepository.getStudents();
    }

    public List<Student> getAllStudentsSortedByName() {
        List<Student> sortedStudents = new ArrayList<>(dataRepository.getStudents());
        sortedStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
        return sortedStudents;
    }

    public List<Student> getAllStudentsSortedByAverageGrade() {

        List<Student> sortedStudents = new ArrayList<>(dataRepository.getStudents());
        sortedStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(studentService.getAverageGrade(s2), studentService.getAverageGrade(s1));
            }
        });
        return sortedStudents;
    }

    public void updateGrade(String courseName, String username, Grade grade)
    {
        for (Course course : dataRepository.getCourses()) {
            if (course.getName().equals(courseName)) {
                for (Student student : course.getStudents().keySet()) {
                    if (student.getUsername().equals(username)) {
                        course.getStudents().put(student, grade);
                        break;
                    }
                }
                break;
            }
        }
    }

    public Grade getStudentGrade(String courseName, String username) {
        for (Course course : dataRepository.getCourses()) {
            if (course.getName().equals(courseName)) {
                for (Student student : course.getStudents().keySet()) {
                    if (student.getUsername().equals(username)) {
                        return course.getStudents().get(student);
                    }
                }
                break;
            }
        }
        return null;
    }

    public List<Map.Entry<Student, Grade>> getStudentsAndGradesSortedByDate(String courseName) {
        List<Course> courses = dataRepository.getCourses();

        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                List<Map.Entry<Student, Grade>> studentGrades = new ArrayList<>(course.getStudents().entrySet());

                Collections.sort(studentGrades, new Comparator<Map.Entry<Student, Grade>>() {
                    @Override
                    public int compare(Map.Entry<Student, Grade> o1, Map.Entry<Student, Grade> o2) {
                        return o2.getValue().getDate().compareTo(o1.getValue().getDate());
                    }
                });

                return studentGrades;
            }
        }
        return null;
    }
}
