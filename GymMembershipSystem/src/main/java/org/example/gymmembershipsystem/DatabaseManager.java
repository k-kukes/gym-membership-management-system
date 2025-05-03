package org.example.gymmembershipsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager dbObject;
    private Connection connection;

    private DatabaseManager(){
        String Base_Path = "jdbc:sqlite:src/main/resources/database/";
        String DB_Path = Base_Path + "dataNew.db";
        try {
            connection = DriverManager.getConnection(DB_Path);
            System.out.println("Connected to database");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseManager getInstance(){
        if (dbObject == null)
            dbObject = new DatabaseManager();

        return dbObject;
    }

    public Connection getConnection(){
        return connection;
    }

    public static void createMembersTable(){
        // membershipType, renewedMembership, nextPayment, contractEnd, balance should be NOT NULL
        String sql = """
                CREATE TABLE IF NOT EXISTS members (
                    id INTEGER IDENTITY(1,1) PRIMARY KEY,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    firstName TEXT NOT NULL,
                    lastName TEXT NOT NULL,
                    dob DATE,
                    phoneNo TEXT,
                    address TEXT,
                    membershipCreation DATE,
                    membershipType TEXT,
                    renewedMembership TEXT,
                    nextPayment DATE,
                    contractEnd DATE,
                    latestEntry DATE,
                    balance  DECIMAL(10,2)
                )
                """;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Members table created successfully.");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createEmployeesTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS employees(
                    id INTEGER IDENTITY(1,1) PRIMARY KEY,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    firstName TEXT,
                    lastName TEXT,
                    dob DATE,
                    phoneNo TEXT,
                    address TEXT,
                    dateHired DATE,
                    latestLog TEXT
                )
                """;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Employees table created successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void insertMember(Member member){
        String sql = """
                INSERT INTO members (
                username, password, firstName, lastName, dob, phoneNo, address, membershipCreation,
                membershipType, renewedMembership, nextPayment, contractEnd, latestEntry, balance
                )
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, member.getLoginUsername());
            statement.setString(2, member.getLoginPassword());
            statement.setString(3, member.getfName());
            statement.setString(4, member.getlName());
            statement.setString(5, member.getDob());
            statement.setString(6, member.getPhoneNo());
            statement.setString(7, member.getAddress());
            statement.setString(8, member.getMembershipCreationDate());
            statement.setString(9, member.getMembershipType().getType());
            statement.setString(10, member.isRenewedMembership() ? "true" : "false");
            statement.setString(11, member.getNextPaymentDate());
            statement.setString(12, member.getContractEndDate());
            statement.setString(13, member.getLatestEntry());
            statement.setDouble(14, member.getBalance());

            statement.executeUpdate();
            System.out.println("Member was successfully created!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateMember(Member member){
        String sql = """
                UPDATE members
                SET username = ?, password = ?, firstName = ?, lastName = ?, dob = ?, phoneNo = ?, address = ?,
                membershipCreation = ?, membershipType = ?, renewedMembership = ?,
                nextPayment = ?, contractEnd = ?, latestEntry = ?, balance = ?
                WHERE username = ?
                """;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, member.getLoginUsername());
            stmt.setString(2, member.getLoginPassword());
            stmt.setString(3, member.getfName());
            stmt.setString(4, member.getlName());
            stmt.setString(5, member.getDob());
            stmt.setString(6, member.getPhoneNo());
            stmt.setString(7, member.getAddress());
            stmt.setString(8, member.getMembershipCreationDate());
            stmt.setString(9, member.getMembershipType().getType());
            stmt.setBoolean(10, member.isRenewedMembership());
            stmt.setString(11, member.getNextPaymentDate());
            stmt.setString(12, member.getContractEndDate());
            stmt.setString(13, member.getLatestEntry());
            stmt.setDouble(14, member.getBalance());
            stmt.setString(15, member.getLoginUsername());

            stmt.executeUpdate();
            System.out.println("Member updated successfully");
        }
        catch (SQLException e){
            System.out.println("Couldn't Update Member!");
            System.out.println(e.getMessage());
        }
    }

    public static void insertEmployee(Employee employee){
        String sql = """
                INSERT INTO employees(
                    username, password, firstName, lastName, dob, phoneNo, address,
                    dateHired, latestLog
                )
                VALUES (?,?,?,?,?,?,?,?,?)
                """;

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, employee.getLoginUsername());
            statement.setString(2, employee.getLoginPassword());
            statement.setString(3, employee.getfName());
            statement.setString(4, employee.getlName());
            statement.setString(5, employee.getDob());
            statement.setString(6, employee.getPhoneNo());
            statement.setString(7, employee.getAddress());
            statement.setString(8, employee.getDateHired());
            statement.setString(9, employee.getLatestLog());

            statement.executeUpdate();
            System.out.println("Employee inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteMember(String loginUsername){
        String sql = "DELETE FROM members WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginUsername);
            stmt.executeUpdate();
            System.out.println("Member deleted successfully");
        }
        catch (SQLException e){
            System.out.println("Error deleting Member");
            System.out.println(e.getMessage());
        }
    }
    public static void deleteEmployee(String loginUsername){
        String sql = "DELETE FROM employees WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginUsername);
            stmt.executeUpdate();
            System.out.println("Employee deleted successfully");
        }
        catch (SQLException e){
            System.out.println("Error deleting Employee");
            System.out.println(e.getMessage());
        }
    }

    public static List<Employee> searchEmployees(String filter, String value) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE " + filter + " = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, value);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                Employee employee = new Employee(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("dateHired"),
                        set.getString("latestLog")
                );
                employees.add(employee);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public static List<String> getLogs(String username){
        String sql = "SELECT latestLog FROM employees WHERE username = ?";
        List<String> resultLogs = new ArrayList<>();

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();

            if (set.next()){
                String allLogs = set.getString("latestLog");
                String[] logsArray = allLogs.split("\n");

                for (String log : logsArray){
                    if (!log.trim().isEmpty())
                        resultLogs.add(log.trim());
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return resultLogs;
    }

    public static void updateLogs(Employee employee){
        String sql = "UPDATE employees SET latestLog = ? WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, employee.getLatestLog());
            stmt.setString(2, employee.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                Employee employee = new Employee(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("dateHired"),
                        set.getString("latestLog")
                );
                employees.add(employee);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public static Member loginMemberValidation(String username, String password){
        String sql = "SELECT * FROM members WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet set = stmt.executeQuery();
            if (set.next()){
                return new Member(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("membershipCreation"),
                        set.getString("membershipType").equalsIgnoreCase("Premium") ? MembershipFactory.craete("premium") : MembershipFactory.craete("regular"),
                        set.getBoolean("renewedMembership"),
                        set.getString("nextPayment"),
                        set.getString("contractEnd"),
                        set.getString("latestEntry"),
                        set.getDouble("balance")
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static Employee loginEmployeeValidation(String username, String password){
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet set = stmt.executeQuery();

            if (set.next()){
                return new Employee(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("dateHired"),
                        set.getString("latestLog")
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Member> searchMembers(String filter, String value) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE " + filter + " = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, value);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                Member member = new Member(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("membershipCreation"),
                        set.getString("membershipType").equalsIgnoreCase("Premium") ? MembershipFactory.craete("premium") : MembershipFactory.craete("regular"),
                        set.getBoolean("renewedMembership"),
                        set.getString("nextPayment"),
                        set.getString("contractEnd"),
                        set.getString("latestEntry"),
                        set.getDouble("balance")
                );
                members.add(member);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return members;
    }

    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                Member member = new Member(
                        set.getString("username"),
                        set.getString("password"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("dob"),
                        set.getString("phoneNo"),
                        set.getString("address"),
                        set.getString("membershipCreation"),
                        set.getString("membershipType").equalsIgnoreCase("Premium") ? MembershipFactory.craete("premium"): MembershipFactory.craete("regular"),
                        set.getBoolean("renewedMembership"),
                        set.getString("nextPayment"),
                        set.getString("contractEnd"),
                        set.getString("latestEntry"),
                        set.getDouble("balance")
                );
                members.add(member);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return members;
    }

    public static void updateMemberPaymentStatus(Member member){
        String sql = "UPDATE members SET nextPayment = ?, latestEntry = ? WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getNextPaymentDate());
            stmt.setString(2, member.getLatestEntry());
            stmt.setString(3, member.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateMemberMembershipType(Member member){
        String sql = "UPDATE members SET membershipType = ? WHERE username = ?";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getMembershipType().getType());
            stmt.setString(2, member.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void processCancellationPenalty(Member member, double fee){
        String sql = "UPDATE members SET balance = balance - ? WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, fee);
            stmt.setString(2, member.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateMemberCancellationStatus(Member member){
        String sql = "DELETE FROM members WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateMemberBalance(Member member){
        String sql = "UPDATE members SET balance = ? WHERE username = ?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, member.getBalance());
            stmt.setString(2, member.getLoginUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        deleteMember("Kukes");
    }
}
