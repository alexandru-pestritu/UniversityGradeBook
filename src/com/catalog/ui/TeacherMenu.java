package com.catalog.ui;
import com.catalog.models.*;
import com.catalog.data.UserSession;
import com.catalog.services.CourseService;
import com.catalog.services.StudentService;
import com.catalog.services.TeacherService;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

public class TeacherMenu {
    private final Scanner scanner;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    public TeacherMenu() {
        this.scanner = new Scanner(System.in);
        this.teacherService = new TeacherService();
        this.studentService = new StudentService();
        this.courseService = new CourseService();
    }

    public void displayMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nMeniul Profesorului");
            System.out.println("1. Gestionare cursuri");
            System.out.println("2. Gestionare note studenti");
            System.out.println("3. Calcul medie finala");
            System.out.println("4. Vizualizare si sortare studenti");
            System.out.println("5. Deconectare");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourseManagementMenu();
                    break;
                case 2:
                    displayGradeManagementMenu();
                    break;
                case 3:
                    displayCalculateFinalGradeMenu();
                    break;
                case 4:
                    displayViewAndSortStudentsMenu();
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


    public void displayCourseManagementMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nGestionare cursuri");
            System.out.println("1. Vizualizare discipline");
            System.out.println("2. Adaugare disciplina");
            System.out.println("3. Modificare disciplina");
            System.out.println("4. Stergere disciplina");
            System.out.println("5. Inapoi");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAllCourses();
                    break;
                case 2:
                    printAddCourse();
                    break;
                case 3:
                    displayUpdateCourseMenu();
                    break;
                case 4:
                    printDeleteCourse();
                    break;
                case 5:
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private void printAllCourses() {
        System.out.println("\nLista cursurilor este:");
        for (Course course : teacherService.getCoursesSortedByName()) {
            System.out.println(course);
        }
    }

    private void printAddCourse(){
        scanner.nextLine();
        System.out.println("Introduceti numele scurt al cursului: ");
        String shortName = scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String name = scanner.nextLine();
        System.out.println("Introduceti anul cursului: ");
        String year = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        if (session.getUserType() == UserSession.UserType.TEACHER) {
            Teacher teacher = (Teacher) session.getUserDetails();
            teacherService.addCourse(shortName, name, teacher, year);
            System.out.println("Cursul a fost adaugat cu succes.");
        }
        else {
            System.out.println("Cursul nu a putut fi adaugat.");
        }

    }

    public void displayUpdateCourseMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nModificare disciplina");
            System.out.println("1. Adauga student");
            System.out.println("2. Elimina student");
            System.out.println("3. Actualizare informatii disciplina");
            System.out.println("4. Inapoi");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAddStudentToCourse();
                    break;
                case 2:
                    printDeleteStudentFromCourse();
                    break;
                case 3:
                    printUpdateCourse();
                    break;
                case 4:
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private void printAddStudentToCourse(){
        printAllStudents();
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        System.out.println("Introduceti username-ul studentului: ");
        String username = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        String teacherUsername = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(teacherUsername, courseName)) {
            if(courseService.isStudentEnrolled(username, courseName)){
                System.out.println("Studentul este deja inscris la acest curs.");
                return;
            }
            courseService.addStudentToCourse(courseName, username);
            System.out.println("Studentul a fost adaugat cu succes.");
        }
        else {
            System.out.println("Studentul nu a putut fi adaugat.");
        }
    }

    private void printDeleteStudentFromCourse(){
        printAllStudents();
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        System.out.println("Introduceti username-ul studentului: ");
        String username = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        String teacherUsername = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(teacherUsername, courseName)) {
            if(!courseService.isStudentEnrolled(username, courseName)){
                System.out.println("Studentul nu este inscris la acest curs.");
                return;
            }
            courseService.deleteStudentFromCourse(courseName, username);
            System.out.println("Studentul a fost sters cu succes.");
        }
        else {
            System.out.println("Studentul nu a putut fi sters.");
        }
    }

    private void printUpdateCourse(){
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introuceti numele cursului pe care doriti sa il modificati: ");
        String name = scanner.nextLine();
        System.out.println("Introduceti noul nume scurt al cursului: ");
        String newShortName = scanner.nextLine();
        System.out.println("Introduceti noul nume al cursului: ");
        String newName = scanner.nextLine();
        System.out.println("Introduceti noul an al cursului: ");
        String year = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        String username = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(username, name)) {
            courseService.updateCourse(name, newShortName, newName, year);
            System.out.println("Cursul a fost modificat cu succes.");
        }
        else {
            System.out.println("Cursul nu a putut fi modificat.");
        }
    }

    private void printDeleteCourse(){
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        String username = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(username, courseName)) {
            teacherService.deleteCourse(courseName);
            System.out.println("Cursul a fost sters cu succes.");
        }
        else {
            System.out.println("Cursul nu a putut fi sters.");
        }
    }

    public void displayGradeManagementMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nGestionare note studenti");
            System.out.println("1. Adauga nota");
            System.out.println("2. Modificare nota");
            System.out.println("3. Stergere nota");
            System.out.println("4. Sortare note dupa data calendaristica");
            System.out.println("5. Inapoi");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAddGrade();
                    break;
                case 2:
                    printUpdateGrade();
                    break;
                case 3:
                    printDeleteGrade();
                    break;
                case 4:
                    printGradesSortedByDate();
                    break;
                case 5:
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    private void printAddGrade(){
        printAllStudents();
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        System.out.println("Introduceti username-ul studentului: ");
        String username = scanner.nextLine();
        System.out.println("Introduceti nota: ");
        int value = scanner.nextInt();
        UserSession session = UserSession.getInstance();
        String teacherUsername = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(teacherUsername, courseName)) {
            if(!courseService.isStudentEnrolled(username, courseName)){
                System.out.println("Studentul nu este inscris la acest curs.");
                return;
            }
            Grade studentGrade = teacherService.getStudentGrade(username, courseName);
            if(studentGrade != null && studentGrade.getValue() != 0){
                System.out.println("Studentul are deja o nota la acest curs.");
                return;
            }
            Grade grade = new Grade(value);
            teacherService.updateGrade(courseName, username, grade);
            System.out.println("Nota a fost adaugata cu success.");
        }
        else {
            System.out.println("Nota nu a putut fi adaugata.");
        }
    }

    private void printGradesSortedByDate() {
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        List<Map.Entry<Student, Grade>> studentGrades = teacherService.getStudentsAndGradesSortedByDate(courseName);

        if (studentGrades != null) {
            System.out.println("\nLista notelor este:");
            for (Map.Entry<Student, Grade> entry : studentGrades) {
                Student student = entry.getKey();
                Grade grade = entry.getValue();
                System.out.println(student.getName() + " - " + grade.getValue() + ", " + grade.getDate());
            }
        } else {
            System.out.println("Cursul nu a fost găsit.");
        }
    }


    private void printUpdateGrade(){
        printAllStudents();
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        System.out.println("Introduceti username-ul studentului: ");
        String username = scanner.nextLine();
        System.out.println("Introduceti nota: ");
        int value = scanner.nextInt();
        UserSession session = UserSession.getInstance();
        String teacherUsername = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(teacherUsername, courseName)) {
            if(!courseService.isStudentEnrolled(username, courseName)){
                System.out.println("Studentul nu este inscris la acest curs.");
                return;
            }
            Grade grade = new Grade(value);
            teacherService.updateGrade(courseName, username, grade);
            System.out.println("Nota a fost modificata cu success.");
        }
        else {
            System.out.println("Nota nu a putut fi modificata.");
        }
    }

    private void printDeleteGrade(){
        printAllStudents();
        printAllCourses();
        scanner.nextLine();
        System.out.println("Introduceti numele cursului: ");
        String courseName = scanner.nextLine();
        System.out.println("Introduceti username-ul studentului: ");
        String username = scanner.nextLine();
        UserSession session = UserSession.getInstance();
        String teacherUsername = session.getUsername();
        if (teacherService.isTeacherAssignedToCourse(teacherUsername, courseName)) {
            if(!courseService.isStudentEnrolled(username, courseName)){
                System.out.println("Studentul nu este inscris la acest curs.");
                return;
            }
            Grade grade = new Grade(0);
            teacherService.updateGrade(courseName, username, grade);
            System.out.println("Nota a fost stearsa cu success.");
        }
        else {
            System.out.println("Nota nu a putut fi stearsa.");
        }
    }

    public void displayCalculateFinalGradeMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nCalcul medie finala");
            System.out.println("1. Calcul pentru un student");
            System.out.println("2. Calcul pentru toti studentii");
            System.out.println("3. Inapoi");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printGetAverageGradeForStudent();
                    break;
                case 2:
                    printAllStudentsSortedByAverageGrade();
                    break;
                case 3:
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    void printGetAverageGradeForStudent()
    {
        printAllStudents();
        System.out.println("Introduceti username-ul studentului: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        double averageGrade = studentService.getAverageGrade(username);
        System.out.println("Media studentului este: " + averageGrade);
    }

    public void displayViewAndSortStudentsMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\nVizualizare si sortare studenti");
            System.out.println("1. Vizualizare lista studenti");
            System.out.println("2. Sortare studenti dupa nume");
            System.out.println("3. Sortare studenti dupa medie");
            System.out.println("4. Inapoi");

            System.out.print("Alege o opțiune: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAllStudents();
                    break;
                case 2:
                    printAllStudentsSortedByName();
                    break;
                case 3:
                    printAllStudentsSortedByAverageGrade();
                    break;
                case 4:
                    exitMenu = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }

    void printAllStudents(){
        System.out.println("\nLista studentilor este:");
        for (Student student : teacherService.getAllStudents()) {
            System.out.println(student);
        }
    }

    void printAllStudentsSortedByName(){
        System.out.println("\nLista studentilor este:");
        for (Student student : teacherService.getAllStudentsSortedByName()) {
            System.out.println(student);
        }
    }

    void printAllStudentsSortedByAverageGrade(){
        System.out.println("\nLista studentilor este:");
        for (Student student : teacherService.getAllStudentsSortedByAverageGrade()) {
            double grade = studentService.getAverageGrade(student);
            System.out.println(student.getName() + " - " + String.format("%,.2f", grade));
        }
    }
}
