package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.AttendanceBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.AttendanceDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.AttendanceDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Attendance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ATTENDANCE);

    @Override
    public boolean saveAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        Attendance attendance = new Attendance(
                dto.getEmployeeId(),
                dto.getDate(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                dto.getStatus()
        );
        return attendanceDAO.save(attendance);
    }

    @Override
    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<AttendanceDTO> getAttendanceByEmployeeId(String employeeId) throws SQLException, ClassNotFoundException {

        ArrayList<Attendance> attendanceList = attendanceDAO.getAllEmployeeid(employeeId);

        ArrayList<AttendanceDTO> dtoList = new ArrayList<>();

        if (attendanceList != null) {

            for (Attendance a : attendanceList) {

                dtoList.add(new AttendanceDTO(
                        a.getEmployeeId(),
                        a.getDate(),
                        a.getCheckIn().toString(),
                        a.getCheckOut().toString(),
                        a.getStatus()
                ));
            }
        }

        return dtoList;
    }

    @Override
    public void saveSalary(String employeeId, BigDecimal salary, LocalDate salaryDate, String month, String method, String remarks) throws SQLException, ClassNotFoundException {attendanceDAO.saveSalary(
            employeeId,
                salary,
                salaryDate,
                month,
                method,
                remarks
        );
    }

}
