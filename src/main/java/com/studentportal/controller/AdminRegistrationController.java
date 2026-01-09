package com.studentportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentportal.service.RegistrationService;

@Controller
@RequestMapping("/admin/registrations")
public class AdminRegistrationController {
    
    private final RegistrationService registrationService;
    
    public AdminRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @GetMapping
    public String showPendingRegistrations(Model model) {
        model.addAttribute("pendingStudents", registrationService.getPendingStudents());
        model.addAttribute("pendingFaculty", registrationService.getPendingFaculty());
        return "admin/registrations";
    }
    
    @PostMapping("/student/{id}/approve")
    public String approveStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.approveStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student registration approved");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }
    
    @PostMapping("/student/{id}/reject")
    public String rejectStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.rejectStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student registration rejected");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }
    
    @PostMapping("/faculty/{id}/approve")
    public String approveFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.approveFaculty(id);
            redirectAttributes.addFlashAttribute("success", "Faculty registration approved");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }
    
    @PostMapping("/faculty/{id}/reject")
    public String rejectFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.rejectFaculty(id);
            redirectAttributes.addFlashAttribute("success", "Faculty registration rejected");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/registrations";
    }
}
