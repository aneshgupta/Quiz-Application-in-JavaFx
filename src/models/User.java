
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String password;
    private String gender;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email, String mobileNo, String password, String gender) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.password = password;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", mobileNo=" + mobileNo + ", password=" + password + ", gender=" + gender + '}';
    }
    
    public static void createTable() {
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "CREATE TABLE if NOT EXISTS user(user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " first_name VARCHAR(30),last_name VARCHAR(30), "
                + "email VARCHAR(30) unique, mobile_no VARCHAR(10),gender VARCHAR(6), "
                + "password VARCHAR(20) )";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println(b);
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    
    }
    
    public User saveUser(){
        
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "INSERT INTO user(first_name,last_name,email,mobile_no,gender,password) values(?,?,?,?,?,?)";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, this.firstName);
            ps.setString(2, this.lastName);
            ps.setString(3, this.email);
            ps.setString(4, this.mobileNo);
            ps.setString(5, this.gender);
            ps.setString(6, this.password);
            
            int i =  ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            while(keys.next()){
                this.userId = keys.getInt(1);
            }
            
            System.out.println(i+" rows updated...");
            System.out.println("Key: "+this.userId);
            
            conn.close();
            return this;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
      
    }
    
    public static ArrayList<User> getUSerDetails() {
        ArrayList<User> users = new ArrayList<>();
        User user = null;
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT * FROM user";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setMobileNo(rs.getString(5));
                user.setGender(rs.getString(6));
                user.setPassword(rs.getString(7));
                
                users.add(user);
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return users;
    }
    
    public boolean userLogin(){
        boolean flag = false;
        String URL = "jdbc:sqlite:quiz_db.db";
        String query = "SELECT * FROM user WHERE email=? AND password=?";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, this.getEmail());
            ps.setString(2, this.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                this.setUserId(rs.getInt(1));
                this.setFirstName(rs.getString(2));
                this.setLastName(rs.getString(3));
                this.setEmail(rs.getString(4));
                this.setMobileNo(rs.getString(5));
                this.setGender(rs.getString(6));
                this.setPassword(rs.getString(7));
                flag = true;  
            }
            else {
                flag = false;
            }
            
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return flag;
    }
    
}
