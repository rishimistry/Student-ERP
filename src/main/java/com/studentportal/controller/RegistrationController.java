package com.studentportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentportal.dto.FacultyRegistrationDTO;
import com.studentportal.dto.StudentRegistrationDTO;
import com.studentportal.service.RegistrationService;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private final RegistrationService registrationService;
    
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @GetMapping
    public String registerChoice() {
        return "register/choice";
    }
    
    @GetMapping("/student")
    public String showStudentRegistration(Model model) {
        model.addAttribute("registration", new StudentRegistrationDTO());
        return "register/student";
    }
    
    @PostMapping("/student")
    public String registerStudent(@ModelAttribute("registration") StudentRegistrationDTO dto,
                                  RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerStudent(dto);
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Your account is pending approval. " +
                "You will be able to login once an administrator approves your account. " +
                "Your username will be: " + dto.getRollNumber().toLowerCase().replaceAll("\\s+", ""));
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("registration", dto);
            return "redirect:/register/student";
        }
    }
    
    @GetMapping("/faculty")
    public String showFacultyRegistration(Model model) {
        model.addAttribute("registration", new FacultyRegistrationDTO());
        return "register/faculty";
    }
    
    @PostMapping("/faculty")
    public String registerFaculty(@ModelAttribute("registration") FacultyRegistrationDTO dto,
                                  RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerFaculty(dto);
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Your account is pending approval. " +
                "You will be able to login once an administrator approves your account. " +
                "Your username will be: " + dto.getEmployeeId().toLowerCase().replaceAll("\\s+", ""));
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("registration", dto);
            return "redirect:/register/faculty";
        }
    }
}
