package com.studentportal.controller;

import com.studentportal.dto.AttendanceDTO;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;
import com.studentportal.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student/attendance")
public class StudentAttendanceController {
    
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final UserService userService;
    
    public StudentAttendanceController(AttendanceService attendanceService, StudentService studentService, 
                                       SubjectService subjectService, UserService userService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.userService = userService;
    }
    
    @GetMapping
    public String viewAttendance(
            @RequestParam(required = false) Long subjectId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        
        Student student = getStudentFromUserDetails(userDetails);
        List<Subject> subjects = subjectService.findByDepartmentAndSemester(
                student.getDepartment(), student.getSemester());
        
        model.addAttribute("subjects", subjects);
        model.addAttribute("overallPercentage", attendanceService.calculateAttendancePercentage(student));
        model.addAttribute("attendanceSummary", attendanceService.getAttendanceSummary(student, subjects));
        
        if (subjectId != null) {
            Subject subject = subjectService.findById(subjectId).orElse(null);
            if (subject != null) {
                List<AttendanceDTO> records = attendanceService
                        .findByStudentAndSubject(student, subject)
                        .stream()
                        .map(attendanceService::toDTO)
                        .collect(Collectors.toList());
                model.addAttribute("records", records);
                model.addAttribute("selectedSubject", subject);
            }
        }
        
        return "student/attendance";
    }
    
    private Student getStudentFromUserDetails(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .flatMap(user -> studentService.findByUser(user))
                .orElseThrow(() -> new IllegalStateException("Student not found"));
    }
}
