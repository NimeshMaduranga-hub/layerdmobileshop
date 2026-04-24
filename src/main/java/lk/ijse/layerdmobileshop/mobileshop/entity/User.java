package lk.ijse.layerdmobileshop.mobileshop.entity;

public class User {

    private String username;
    private String password;
    private String role;
    private String empId;

    public User() {
    }

    public User(String username, String password, String role, String empId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.empId = empId;
    }

    public User(String username, String password, String role) {
        this.username=username;
        this.password=password;
        this.role=role;
    }

    public User(String username, String role) {
        this.username=username;
        this.role=role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}