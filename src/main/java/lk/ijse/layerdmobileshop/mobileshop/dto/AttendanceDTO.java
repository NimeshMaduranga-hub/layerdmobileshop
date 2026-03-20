package lk.ijse.layerdmobileshop.mobileshop.dto;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {

    private String employeeId;
    private LocalDate date;
    private final ObjectProperty<LocalTime> checkIn = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> checkOut = new SimpleObjectProperty<>();
    private String status;

    public AttendanceDTO() {}

    public AttendanceDTO(String employeeId, LocalDate date, LocalTime checkIn, LocalTime checkOut, String status) {
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn.set(checkIn);
        this.checkOut.set(checkOut);
        this.status = status;
    }

    public AttendanceDTO(String employeeId, LocalDate date, String checkIn, String checkOut, String status) {
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn.set(LocalTime.parse(checkIn));
        this.checkOut.set(LocalTime.parse(checkOut));
        this.status = status;
    }

    // getters / setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getCheckIn() { return checkIn.get(); }
    public void setCheckIn(LocalTime value) { checkIn.set(value); }
    public ObjectProperty<LocalTime> checkInProperty() { return checkIn; }

    public LocalTime getCheckOut() { return checkOut.get(); }
    public void setCheckOut(LocalTime value) { checkOut.set(value); }
    public ObjectProperty<LocalTime> checkOutProperty() { return checkOut; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}