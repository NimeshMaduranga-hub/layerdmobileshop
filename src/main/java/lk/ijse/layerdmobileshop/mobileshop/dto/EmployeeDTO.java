package lk.ijse.layerdmobileshop.mobileshop.dto;

import java.math.BigDecimal;

public class EmployeeDTO {

    private String id;
    private String name;
    private String address;
    private String mobile;
    private String salary;
    private String role;

    public EmployeeDTO(String id, String name, String address, String mobile, String salary, String role) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.salary = salary;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", salary='" + salary + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
