package com.catalog.services;

import com.catalog.data.*;
import com.catalog.models.AuthCredentials;
import java.util.Map;


public class AuthService {
    DataRepository dataRepository = DataRepository.getInstance();
    UserSession userSession = UserSession.getInstance();
    public boolean authenticateStudent(String username, String password) {
        Map<String, AuthCredentials> credentials = dataRepository.getAuthStudentsCredentialsMap();
        AuthCredentials studentCredentials = credentials.get(username);
        if(studentCredentials !=null && studentCredentials.getPassword().equals(password)) {
            userSession.setUser(username, UserSession.UserType.STUDENT, dataRepository.findStudentByUsername(username));
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticateTeacher(String username, String password) {
        Map<String, AuthCredentials> credentials = dataRepository.getAuthTeachersCredentialsMap();
        AuthCredentials teacherCredentials = credentials.get(username);
        if(teacherCredentials !=null && teacherCredentials.getPassword().equals(password)) {
            userSession.setUser(username, UserSession.UserType.TEACHER, dataRepository.findTeacherByUsername(username));
            return true;
        } else {
            return false;
        }
    }
}
