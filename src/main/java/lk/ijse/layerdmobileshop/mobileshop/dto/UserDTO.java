package lk.ijse.layerdmobileshop.mobileshop.dto;

public class UserDTO {

    private String empId;
    private String username;
    private String password;
    private String role;

    public UserDTO(String id, String username, String password, String role) {
        this.empId = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return empId;
    }

    public void setId(String id) {
        this.empId = id;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + empId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
