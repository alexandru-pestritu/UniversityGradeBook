package com.catalog.io;
import com.catalog.models.*;
import com.catalog.data.DataRepository;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileHandler {
    DataRepository dataRepository = DataRepository.getInstance();

    public void writeStudentsToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<Student> students = dataRepository.getStudents();
            for (Student student : students) {
                writer.append(student.getUsername())
                        .append(',')
                        .append(student.getName())
                        .append(',')
                        .append(student.getGroup())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readStudentsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    Student student = new Student(values[0], values[1], values[2]);
                    dataRepository.addStudent(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTeachersToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<Teacher> teachers = dataRepository.getTeachers();
            for (Teacher teacher : teachers) {
                writer.append(teacher.getUsername())
                        .append(',')
                        .append(teacher.getName())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTeachersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    Teacher teacher = new Teacher(values[0], values[1]);
                    dataRepository.addTeacher(teacher);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStudentAuthCredentialsToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (AuthCredentials credentials : dataRepository.getAuthStudentsCredentialsMap().values()) {
                writer.append(credentials.getUsername())
                        .append(',')
                        .append(credentials.getPassword())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTeacherAuthCredentialsToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (AuthCredentials credentials : dataRepository.getAuthTeachersCredentialsMap().values()) {
                writer.append(credentials.getUsername())
                        .append(',')
                        .append(credentials.getPassword())
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readStudentAuthCredentialsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    AuthCredentials credentials = new AuthCredentials(values[0], values[1]);
                    dataRepository.addStudentAuthCredentials(credentials);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTeacherAuthCredentialsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    AuthCredentials credentials = new AuthCredentials(values[0], values[1]);
                    dataRepository.addTeacherAuthCredentials(credentials);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Course findOrCreateCourse(String shortName, String name, Teacher teacher, String year) {
        for (Course course : dataRepository.getCourses()) {
            if (course.getShortName().equals(shortName) &&
                    course.getName().equals(name) &&
                    course.getTeacher().equals(teacher) &&
                    course.getYear().equals(year)) {
                return course;
            }
        }
        Course newCourse = new Course(shortName, name, teacher, year);
        dataRepository.addCourse(newCourse);
        return newCourse;
    }

    public void writeCoursesToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<Course> courses = dataRepository.getCourses();
            for (Course course : courses) {
                for (Map.Entry<Student, Grade> entry : course.getStudents().entrySet()) {
                    Student student = entry.getKey();
                    Grade grade = entry.getValue();
                    writer.append(course.getShortName())
                            .append(',')
                            .append(course.getName())
                            .append(',')
                            .append(course.getTeacher().getUsername())
                            .append(',')
                            .append(course.getYear())
                            .append(',')
                            .append(student.getUsername())
                            .append(',')
                            .append(String.valueOf(grade.getValue()))
                            .append(',')
                            .append(grade.getDate().toString())
                            .append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCoursesFromFile(String filePath) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7) {
                    String shortName = values[0].trim();
                    String courseName = values[1].trim();
                    String teacherUsername = values[2].trim();
                    String year = values[3].trim();
                    String studentUsername = values[4].trim();
                    int gradeValue = Integer.parseInt(values[5].trim());
                    LocalDate date = LocalDate.parse(values[6].trim(), dateFormatter);

                    Teacher teacher = dataRepository.findTeacherByUsername(teacherUsername);
                    if (teacher == null) {
                        throw new RuntimeException("Teacher not found: " + teacherUsername);
                    }

                    Student student = dataRepository.findStudentByUsername(studentUsername);
                    if (student == null) {
                        throw new RuntimeException("Student not found: " + studentUsername);
                    }

                    Grade grade = new Grade(gradeValue, date);
                    Course course = findOrCreateCourse(shortName, courseName, teacher, year);
                    course.getStudents().put(student, grade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataFromCSVFiles() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.readStudentsFromFile("src/students.csv");
        fileHandler.readStudentAuthCredentialsFromFile("src/students_credentials.csv");
        fileHandler.readTeachersFromFile("src/teachers.csv");
        fileHandler.readTeacherAuthCredentialsFromFile("src/teachers_credentials.csv");
        fileHandler.readCoursesFromFile("src/courses.csv");
    }

    public void writeDataToCSVFiles() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.writeStudentsToFile("src/students.csv");
        fileHandler.writeStudentAuthCredentialsToFile("src/students_credentials.csv");
        fileHandler.writeTeachersToFile("src/teachers.csv");
        fileHandler.writeTeacherAuthCredentialsToFile("src/teachers_credentials.csv");
        fileHandler.writeCoursesToFile("src/courses.csv");
    }
}
