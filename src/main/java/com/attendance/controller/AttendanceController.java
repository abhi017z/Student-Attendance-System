package com.attendance.controller;

import com.attendance.dto.AttendanceResponse;
import com.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @PostMapping("/{studentId}")
    public ResponseEntity<AttendanceResponse> markAttendance(
            @PathVariable Long studentId,
            @RequestParam String status) {
        AttendanceResponse response = attendanceService.markAttendance(studentId, status);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {
        List<AttendanceResponse> attendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByStudent(
            @PathVariable Long studentId) {
        List<AttendanceResponse> attendance = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/date")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceResponse> attendance = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(attendance);
    }
}
