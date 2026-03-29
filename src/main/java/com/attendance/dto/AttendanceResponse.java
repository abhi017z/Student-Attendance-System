package com.attendance.dto;

import com.attendance.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private Long id;
    private LocalDate date;
    private AttendanceStatus status;
    private Long studentId;
    private String studentName;
}
