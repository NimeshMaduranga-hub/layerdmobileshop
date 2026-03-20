package lk.ijse.layerdmobileshop.mobileshop.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee implements Comparable<Employee> {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String salary;
    private String role ;

    public Employee(String name, String address, String mobile, String salary) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.salary = salary;
    }



    public Employee(String id, String name, String address, String mobile, String salary, String role) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.salary = salary;
        this.role = role;
    }


    @Override
    public int compareTo(Employee o) {
        return this.id.compareTo(o.getId());
    }
}
