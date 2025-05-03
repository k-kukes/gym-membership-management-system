package org.example.gymmembershipsystem;

public class EmployeeManager {
    public EmployeeManager(){

    }

    public static Employee login(String username, String password){
        return DatabaseManager.loginEmployeeValidation(username, password);
    }
}
