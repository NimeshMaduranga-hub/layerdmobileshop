package lk.ijse.layerdmobileshop.mobileshop.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance implements Comparable<Attendance> {

    private String id;
    private String employeeId;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String status;

    public Attendance() {}

    public Attendance(String employeeId, LocalDate date, LocalTime checkIn, LocalTime checkOut, String status) {
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Attendance(String id, String employeeId, LocalDate date, LocalTime checkIn, LocalTime checkOut, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.date.compareTo(o.getDate());
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalTime checkIn) { this.checkIn = checkIn; }

    public LocalTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalTime checkOut) { this.checkOut = checkOut; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}