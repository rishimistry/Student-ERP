package com.studentportal.controller;

import com.studentportal.dto.AttendanceDTO;
import com.studentportal.model.*;
import com.studentportal.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/attendance")
public class AdminAttendanceController {
    
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    
    public AdminAttendanceController(AttendanceService attendanceService, StudentService studentService, SubjectService subjectService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }
    
    @GetMapping
    public String showAttendanceForm(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("date", LocalDate.now());
        return "admin/attendance/form";
    }
    
    @GetMapping("/mark")
    public String markAttendance(
            @RequestParam Long subjectId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        
        Subject subject = subjectService.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        
        List<Student> students = studentService.findByDepartmentAndSemester(
                subject.getDepartment(), subject.getSemester());
        
        List<Attendance> existingAttendance = attendanceService.findBySubjectAndDate(subject, date);
        
        List<AttendanceDTO> attendanceList = students.stream().map(student -> {
            AttendanceDTO dto = AttendanceDTO.builder()
                    .studentId(student.getId())
                    .studentName(student.getUser().getFullName())
                    .rollNumber(student.getRollNumber())
                    .subjectId(subjectId)
                    .date(date)
                    .status(AttendanceStatus.PRESENT)
                    .build();
            
            existingAttendance.stream()
                    .filter(a -> a.getStudent().getId().equals(student.getId()))
                    .findFirst()
                    .ifPresent(a -> dto.setStatus(a.getStatus()));
            
            return dto;
        }).collect(Collectors.toList());
        
        model.addAttribute("subject", subject);
        model.addAttribute("date", date);
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("statuses", AttendanceStatus.values());
        
        return "admin/attendance/mark";
    }
    
    @PostMapping("/save")
    public String saveAttendance(
            @RequestParam Long subjectId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam List<Long> studentIds,
            @RequestParam List<AttendanceStatus> statuses,
            RedirectAttributes redirectAttributes) {
        
        for (int i = 0; i < studentIds.size(); i++) {
            AttendanceDTO dto = AttendanceDTO.builder()
                    .studentId(studentIds.get(i))
                    .subjectId(subjectId)
                    .date(date)
                    .status(statuses.get(i))
                    .build();
            attendanceService.markAttendance(dto);
        }
        
        redirectAttributes.addFlashAttribute("success", "Attendance saved successfully");
        return "redirect:/admin/attendance";
    }
    
    @GetMapping("/view")
    public String viewAttendance(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long studentId,
            Model model) {
        
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("students", studentService.findAll());
        
        if (subjectId != null && studentId != null) {
            Student student = studentService.findById(studentId).orElse(null);
            Subject subject = subjectService.findById(subjectId).orElse(null);
            
            if (student != null && subject != null) {
                List<AttendanceDTO> records = attendanceService
                        .findByStudentAndSubject(student, subject)
                        .stream()
                        .map(attendanceService::toDTO)
                        .collect(Collectors.toList());
                model.addAttribute("records", records);
                model.addAttribute("selectedStudent", student);
                model.addAttribute("selectedSubject", subject);
            }
        }
        
        return "admin/attendance/view";
    }
}
