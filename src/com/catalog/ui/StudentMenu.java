package com.catalog.ui;

import java.util.Scanner;

import com.catalog.data.UserSession;
import com.catalog.services.StudentService;
import com.catalog.models.*;
import java.util.Map;

public class StudentMenu {
    private final Scanner scanner;
    private final StudentService studentService;

    public StudentMenu() {
        this.scanner = new Scanner(System.in);
        this.studentService = new StudentService();
    }

    public void displayMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nMeniul Elevului");
            System.out.println("1. Vizualizare toate notele");
            System.out.println("2. Vizualizare nota la un curs");
            System.out.println("3. Vizualizare medie generală");
            System.out.println("4. Cursuri restante");
            System.out.println("5. Deconectare");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAllStudentGrades();
                    break;
                case 2:
                    printEnrolledCourses();
                    printGradeForCourse();
                    break;
                case 3:
                    printAverageGrade();
                    break;
                case 4:
                    printFailedCourses();
                    break;
                case 5:
                    UserSession.getInstance().clearSession();
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    void printAllStudentGrades() {
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        Map<String,Grade> studentGrades = studentService.getAllStudentGrades(username);
        if(!studentGrades.isEmpty()) {
            System.out.println("\nNotele tale sunt:");
            for (String courseName : studentGrades.keySet()) {
                System.out.println(courseName + " " + studentGrades.get(courseName));
            }
        } else {
            System.out.println("\nNu ai nicio notă.");
        }

    }

    void printAverageGrade()
    {
        double averageGrade = studentService.getAverageGrade();
        System.out.println("\nMedia ta este: " + averageGrade);
    }

    void printFailedCourses()
    {
        Map<String,Grade> failedCourses = studentService.getFailedCourses();
        if(!failedCourses.isEmpty()) {
            System.out.println("\nCursurile la care ai picat sunt:");
            for (String courseName : failedCourses.keySet()) {
                System.out.println(courseName + " " + failedCourses.get(courseName));
            }
        } else {
            System.out.println("\nNu ai picat niciun curs.");
        }
    }

    void printEnrolledCourses()
    {   int courseIndex = 1;
        System.out.println("\nCursurile la care ești înscris sunt:");
        for (Course course : studentService.getEnrolledCourses()) {
            System.out.println(courseIndex + ". " + course.getName());
            courseIndex++;
        }
    }

    void printGradeForCourse()
    {
        System.out.print("Introdu numarul cursului: ");
        int courseIndex = scanner.nextInt();
        String courseName = studentService.getEnrolledCourses().get(courseIndex - 1).getName();
        Grade grade = studentService.getGradeForCourse(courseName);
        if(grade != null && grade.getValue() > 0) {
            System.out.println("\nNota ta la cursul " + courseName + " este: " + grade);
        } else {
            System.out.println("\nNu ai nicio notă la cursul " + courseName);
        }
    }
}
