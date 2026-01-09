package com.studentportal.service;

import com.studentportal.dto.AttendanceDTO;
import com.studentportal.dto.DashboardDTO;
import com.studentportal.model.*;
import com.studentportal.repository.AttendanceRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    
    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }
    
    public List<Attendance> findByStudent(Student student) {
        return attendanceRepository.findByStudentOrderByDateDesc(student);
    }
    
    public List<Attendance> findByStudentAndSubject(Student student, Subject subject) {
        return attendanceRepository.findByStudentAndSubject(student, subject);
    }
    
    public List<Attendance> findBySubjectAndDate(Subject subject, LocalDate date) {
        return attendanceRepository.findBySubjectAndDate(subject, date);
    }
    
    public double calculateAttendancePercentage(Student student) {
        long total = attendanceRepository.countTotalByStudent(student);
        if (total == 0) return 0.0;
        long present = attendanceRepository.countPresentByStudent(student);
        return (double) present / total * 100;
    }
    
    public List<DashboardDTO.AttendanceSummaryDTO> getAttendanceSummary(Student student, List<Subject> subjects) {
        List<DashboardDTO.AttendanceSummaryDTO> summary = new ArrayList<>();
        for (Subject subject : subjects) {
            long total = attendanceRepository.countTotalByStudentAndSubject(student, subject);
            long present = attendanceRepository.countPresentByStudentAndSubject(student, subject);
            double percentage = total > 0 ? (double) present / total * 100 : 0.0;
            
            summary.add(DashboardDTO.AttendanceSummaryDTO.builder()
                    .subjectName(subject.getSubjectName())
                    .present(present)
                    .total(total)
                    .percentage(percentage)
                    .build());
        }
        return summary;
    }
    
    @Transactional
    public Attendance markAttendance(AttendanceDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        
        Optional<Attendance> existing = attendanceRepository
                .findByStudentAndSubjectAndDate(student, subject, dto.getDate());
        
        Attendance attendance;
        if (existing.isPresent()) {
            attendance = existing.get();
            attendance.setStatus(dto.getStatus());
            attendance.setRemarks(dto.getRemarks());
        } else {
            attendance = Attendance.builder()
                    .student(student)
                    .subject(subject)
                    .date(dto.getDate())
                    .status(dto.getStatus())
                    .remarks(dto.getRemarks())
                    .build();
        }
        
        return attendanceRepository.save(attendance);
    }
    
    public AttendanceDTO toDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudent().getId())
                .studentName(attendance.getStudent().getUser().getFullName())
                .rollNumber(attendance.getStudent().getRollNumber())
                .subjectId(attendance.getSubject().getId())
                .subjectName(attendance.getSubject().getSubjectName())
                .date(attendance.getDate())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())
                .build();
    }
}
