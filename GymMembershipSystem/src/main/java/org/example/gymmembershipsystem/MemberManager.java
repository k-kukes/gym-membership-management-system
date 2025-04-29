package org.example.gymmembershipsystem;

import java.sql.Connection;
import java.sql.SQLException;

public class MemberManager {
    public MemberManager(){

    }

    public static void registerNewMember(Member member){
        DatabaseManager.insertMember(member);
    }

    public static boolean validateLogin(String username, String password){
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            return false;
        return true;
    }

    public static Member login(String username, String password){
        return DatabaseManager.loginMemberValidation(username,password);
    }

    public void processPayment(Member member){
        String nextPayment = calculateNextPaymentDate();
        member.setNextPaymentDate(nextPayment);

        DatabaseManager.updateMemberPaymentStatus(member);
        System.out.println("Payment processed, next payment date updated");
    }

    private static String calculateNextPaymentDate(){
        return "2025-08-30";
    }

    private double calculatePaymentAmount(Member member){
        if (member.getMembershipType().getType().equals("Premium"))
            return 24.99;
        else
            return 18.99;
    }

    public boolean processPaymentTransaction(double amount){
        System.out.println("Processing payment of " + amount);
        return true;
    }

    public void updatePaymentStatus(Member member){
        String nextPaymentDate = calculateNextPaymentAmount();
        member.setNextPaymentDate(nextPaymentDate);
        member.setRenewedMembership(true);
        DatabaseManager.updateMemberPaymentStatus(member);
    }

    public String calculateNextPaymentAmount(){
        return "2025-08-30";
    }

    public void switchMembership(Member member){
        String current = member.getMembershipType().getType();

        if (current.equals("Premium"))
            member.setMembershipType(new PremiumMembership());
        else
            member.setMembershipType(new RegularMembership());

        DatabaseManager.updateMemberMembershipType(member);

        System.out.println("Membership switched");
    }

    public void cancelMembership(Member member){
        DatabaseManager.processCancellationPenalty(member, 60.00);
        DatabaseManager.updateMemberCancellationStatus(member);
        System.out.println("Membership Cancelled");
    }

    private void applyCancellationPenalty(Member member){
        double penalty = 60.00;
        System.out.println("Paying payment of 60$ for Cancellation!");
    }
}
