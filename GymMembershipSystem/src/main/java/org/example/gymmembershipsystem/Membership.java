package org.example.gymmembershipsystem;

interface Membership {
    String getType();
    double getMonthlyFee();
}

class RegularMembership implements Membership{

    public RegularMembership(){}

    @Override
    public String getType() {
        return "Regular";
    }

    @Override
    public double getMonthlyFee() {
        return 12.99;
    }
}

class PremiumMembership implements Membership{

    @Override
    public String getType() {
        return "Premium";
    }

    @Override
    public double getMonthlyFee() {
        return 22.99;
    }
}

class MembershipFactory{
    public static Membership craete(String type){
        return switch (type){
            case "premium" -> new PremiumMembership();
            case "regular" -> new RegularMembership();
            default ->  throw new IllegalStateException("Unknown Membership Type");
        };
    }
}
