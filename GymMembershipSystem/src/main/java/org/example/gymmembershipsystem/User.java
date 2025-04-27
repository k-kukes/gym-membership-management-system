package org.example.gymmembershipsystem;

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

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
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

    public String getDateHired() {
        return dateHired;
    }

    public void setDateHired(String dateHired) {
        this.dateHired = dateHired;
    }

    public String getLatestLog() {
        return latestLog;
    }

    public void setLatestLog(String latestLog) {
        this.latestLog = latestLog;
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

    public String getMembershipCreationDate() {
        return membershipCreationDate;
    }

    public void setMembershipCreationDate(String membershipCreationDate) {
        this.membershipCreationDate = membershipCreationDate;
    }

    public Membership getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Membership membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isRenewedMembership() {
        return renewedMembership;
    }

    public void setRenewedMembership(boolean renewedMembership) {
        this.renewedMembership = renewedMembership;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getLatestEntry() {
        return latestEntry;
    }

    public void setLatestEntry(String latestEntry) {
        this.latestEntry = latestEntry;
    }
}
