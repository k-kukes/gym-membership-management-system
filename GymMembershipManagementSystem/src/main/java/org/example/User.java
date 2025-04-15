package org.example;

abstract class User {
    private String loginUsername;
    private String loginPassword;
    private String fName;
    private String lName;
    private String dob;
    private String phoneNo;
    private String address;

    public abstract String getRole();
}

class Employee extends User{
    private String dateHired;
    private String latestLog;

    @Override
    public String getRole() {
        return "Employee";
    }
}

class Member extends User{
    private String membershipCreationDate;
    private Membership membershipType;
    private boolean renewedMembership;
    private String nextPaymentDate;
    private String contractEndDate;
    private String latestEntry;

    @Override
    public String getRole() {
        return "Member";
    }
}
