package lk.ijse.layerdmobileshop.mobileshop.dto;

import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryDTO {

    private StringProperty employeeId;
    private ObjectProperty<BigDecimal> salary;
    private ObjectProperty<LocalDate> payDate;
    private StringProperty payForMonth;
    private StringProperty method;
    private StringProperty remarks;

    public SalaryDTO(String employeeId, BigDecimal salary, LocalDate payDate,
                     String payForMonth, String method, String remarks) {
        this.employeeId = new SimpleStringProperty(employeeId != null ? employeeId : "");
        this.salary = new SimpleObjectProperty<>(salary);
        this.payDate = new SimpleObjectProperty<>(payDate);
        this.payForMonth = new SimpleStringProperty(payForMonth != null ? payForMonth : "");
        this.method = new SimpleStringProperty(method != null ? method : "");
        this.remarks = new SimpleStringProperty(remarks != null ? remarks : "");
    }

    public SalaryDTO(String employeeId, BigDecimal salary, LocalDate payDate) {
        this(employeeId, salary, payDate, "", "", "");
    }

    public String getEmployeeId() { return employeeId.get(); }
    public BigDecimal getSalary() { return salary.get(); }
    public LocalDate getPayDate() { return payDate.get(); }
    public String getPayForMonth() { return payForMonth.get(); }
    public String getMethod() { return method.get(); }
    public String getRemarks() { return remarks.get(); }

    public StringProperty employeeIdProperty() { return employeeId; }
    public ObjectProperty<BigDecimal> salaryProperty() { return salary; }
    public ObjectProperty<LocalDate> payDateProperty() { return payDate; }
    public StringProperty payForMonthProperty() { return payForMonth; }
    public StringProperty methodProperty() { return method; }
    public StringProperty remarksProperty() { return remarks; }
}