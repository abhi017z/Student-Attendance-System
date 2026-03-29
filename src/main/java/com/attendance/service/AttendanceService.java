package com.attendance.service;

import com.attendance.dto.AttendanceResponse;
import com.attendance.entity.Attendance;
import com.attendance.entity.AttendanceStatus;
import com.attendance.entity.Student;
import com.attendance.exception.AttendanceException;
import com.attendance.exception.StudentNotFoundException;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAttendanceByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with id: " + studentId));
        
        return attendanceRepository.findByStudent(student).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public AttendanceResponse markAttendance(Long studentId, String status) {
        // Validate student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with id: " + studentId));
        
        // Validate status
        AttendanceStatus attendanceStatus;
        try {
            attendanceStatus = AttendanceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AttendanceException(
                    "Invalid status. Must be PRESENT or ABSENT");
        }
        
        // Check if attendance already marked for today
        LocalDate today = LocalDate.now();
        List<Attendance> existingAttendance = attendanceRepository.findByDate(today);
        boolean alreadyMarked = existingAttendance.stream()
                .anyMatch(a -> a.getStudent().getId().equals(studentId));
        
        if (alreadyMarked) {
            throw new AttendanceException(
                    "Attendance already marked for this student today");
        }
        
        // Create and save attendance record
        Attendance attendance = new Attendance();
        attendance.setDate(today);
        attendance.setStatus(attendanceStatus);
        attendance.setStudent(student);
        
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToResponse(savedAttendance);
    }
    
    private AttendanceResponse convertToResponse(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getDate(),
                attendance.getStatus(),
                attendance.getStudent().getId(),
                attendance.getStudent().getName()
        );
    }
}
