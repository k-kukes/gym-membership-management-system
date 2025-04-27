package org.example.gymmembershipsystem;

public class EmployeeManager {
    public EmployeeManager(){

    }

    public static void registerNewEmployee(Employee employee){
        DatabaseManager.insertEmployee(employee);
    }

    public static Employee login(String username, String password){
        return DatabaseManager.loginEmployeeValidation(username, password);
    }

    public void addNewMember(){
        // to add members
    }

    public void removeMember(){
        //to remove a member
    }

    public void manageMembersInfo(){
        // to manage information about a member
    }

    public void addEmployee(){
        // to add employee
    }

    public void removeEmployee(){
        //to remove employee
    }
}
