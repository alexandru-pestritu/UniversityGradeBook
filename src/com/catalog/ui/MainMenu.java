package com.catalog.ui;
import com.catalog.data.UserSession;
import com.catalog.io.FileHandler;
import com.catalog.services.AuthService;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    UserSession userSession = UserSession.getInstance();

    public MainMenu() {

        scanner=new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nMeniul Principal");
            System.out.println("1. Autentificare Student");
            System.out.println("2. Autentificare Profesor");
            System.out.println("3. Ieșire");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    authenticateStudent();
                    break;
                case 2:
                    authenticateTeacher();
                    break;
                case 3:
                    System.out.println("Ieșire din aplicație.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private void authenticateStudent() {
        scanner.nextLine();
        System.out.print("Introduceți username-ul studentului: ");
        String username = scanner.nextLine();
        System.out.print("Introduceți parola: ");
        String password = scanner.nextLine();
        AuthService authService = new AuthService();
        boolean isAuthenticated = authService.authenticateStudent(username, password);
        if (isAuthenticated) {
            System.out.println("Bun venit, " + username + "!");
            StudentMenu studentMenu = new StudentMenu();
            studentMenu.displayMenu();
        } else {
            System.out.println("Autentificare eșuată.");
        }
    }

    private void authenticateTeacher() {
        scanner.nextLine();
        System.out.print("Introduceți username-ul profesorului: ");
        String username = scanner.nextLine();
        System.out.print("Introduceți parola: ");
        String password = scanner.nextLine();
        AuthService authService = new AuthService();
        boolean isAuthenticated = authService.authenticateTeacher(username, password);
        if (isAuthenticated) {
            System.out.println("Bun venit, " + username + "!");
            TeacherMenu teacherMenu = new TeacherMenu();
            teacherMenu.displayMenu();
        } else {
            System.out.println("Autentificare eșuată.");
        }
    }
}
