package org.example;

abstract class User {
    private String loginUsername;
    private String loginPassword;
    private String fName;
    private String lName;
    private String dob;
    private String phoneNo;
    private String address;

    public User(String loginUsername, String loginPassword, String fName, String lName, String dob, String phoneNo, String address) {
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.fName = fName;
        this.lName = lName;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public abstract String getRole();


}

class Employee extends User{
    private String dateHired;
    private String latestLog;

    public Employee(String loginUsername, String loginPassword, String fName, String lName, String dob, String phoneNo, String address, String dateHired, String latestLog) {
        super(loginUsername, loginPassword, fName, lName, dob, phoneNo, address);
        this.dateHired = dateHired;
        this.latestLog = latestLog;
    }

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

    public Member(String loginUsername, String loginPassword, String fName, String lName, String dob, String phoneNo, String address, String membershipCreationDate, Membership membershipType, boolean renewedMembership, String nextPaymentDate, String contractEndDate, String latestEntry) {
        super(loginUsername, loginPassword, fName, lName, dob, phoneNo, address);
        this.membershipCreationDate = membershipCreationDate;
        this.membershipType = membershipType;
        this.renewedMembership = renewedMembership;
        this.nextPaymentDate = nextPaymentDate;
        this.contractEndDate = contractEndDate;
        this.latestEntry = latestEntry;
    }

    @Override
    public String getRole() {
        return "Member";
    }
}
