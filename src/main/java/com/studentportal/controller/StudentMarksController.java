package com.studentportal.controller;

import com.studentportal.dto.MarkDTO;
import com.studentportal.model.Student;
import com.studentportal.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student/marks")
public class StudentMarksController {
    
    private final MarkService markService;
    private final StudentService studentService;
    private final UserService userService;
    
    public StudentMarksController(MarkService markService, StudentService studentService, UserService userService) {
        this.markService = markService;
        this.studentService = studentService;
        this.userService = userService;
    }
    
    @GetMapping
    public String viewMarks(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Student student = getStudentFromUserDetails(userDetails);
        
        List<MarkDTO> marks = markService.findByStudent(student)
                .stream()
                .map(markService::toDTO)
                .collect(Collectors.toList());
        
        model.addAttribute("marks", marks);
        model.addAttribute("averagePercentage", markService.calculateAveragePercentage(student));
        
        return "student/marks";
    }
    
    private Student getStudentFromUserDetails(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .flatMap(user -> studentService.findByUser(user))
                .orElseThrow(() -> new IllegalStateException("Student not found"));
    }
}
