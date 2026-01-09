package com.studentportal.controller;

import com.studentportal.model.Student;
import com.studentportal.service.StudentService;
import com.studentportal.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/profile")
public class StudentProfileController {
    
    private final StudentService studentService;
    private final UserService userService;
    
    public StudentProfileController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }
    
    @GetMapping
    public String viewProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Student student = getStudentFromUserDetails(userDetails);
        model.addAttribute("student", studentService.toDTO(student));
        return "student/profile";
    }
    
    private Student getStudentFromUserDetails(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .flatMap(user -> studentService.findByUser(user))
                .orElseThrow(() -> new IllegalStateException("Student not found"));
    }
}
