package com.studentportal.controller;

import com.studentportal.dto.DashboardDTO;
import com.studentportal.model.Student;
import com.studentportal.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {
    
    private final UserService userService;
    private final StudentService studentService;
    private final DashboardService dashboardService;
    
    public StudentDashboardController(UserService userService, StudentService studentService, DashboardService dashboardService) {
        this.userService = userService;
        this.studentService = studentService;
        this.dashboardService = dashboardService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Student student = getStudentFromUserDetails(userDetails);
        DashboardDTO dashboard = dashboardService.getStudentDashboard(student);
        model.addAttribute("dashboard", dashboard);
        return "student/dashboard";
    }
    
    private Student getStudentFromUserDetails(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .flatMap(user -> studentService.findByUser(user))
                .orElseThrow(() -> new IllegalStateException("Student not found"));
    }
}
