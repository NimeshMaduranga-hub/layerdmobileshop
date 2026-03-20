package lk.ijse.layerdmobileshop.mobileshop.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Salary {

    private String id;
    private String employeeId;
    private BigDecimal salary;
    private LocalDate payDate;

    public Salary(String id, String employeeId, BigDecimal salary, LocalDate payDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.salary = salary;
        this.payDate = payDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }
}