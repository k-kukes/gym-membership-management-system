package org.example.gymmembershipsystem;

import java.sql.Connection;

public class MemberManager {
    public MemberManager(){

    }

    public static void registerNewMember(Member member){
        DatabaseManager.insertMember(member);
    }

    public static Member login(String username, String password){
        return DatabaseManager.loginMemberValidation(username, password);
    }

    public static void validateLogin(String username, String password){
    }

    public void processPayment(){
        // Process their monthly payment
    }

    public void switchMembership(){
        // Switch gym membership type from premium to regular or regular to premium
    }

    public void cancelMembership(){
        // Process cancelling membership
    }
}
