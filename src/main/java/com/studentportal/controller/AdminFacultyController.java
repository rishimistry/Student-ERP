package com.studentportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentportal.model.Role;
import com.studentportal.service.RegistrationService;

@Controller
@RequestMapping("/admin/faculty")
public class AdminFacultyController {
    
    private final RegistrationService registrationService;
    
    public AdminFacultyController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @GetMapping
    public String showFacultyList(Model model) {
        var allFaculty = registrationService.getAllActiveFaculty();
        model.addAttribute("facultyList", allFaculty);
        model.addAttribute("Role", Role.class);
        return "admin/faculty/list";
    }
    
    @PostMapping("/{id}/promote")
    public String promoteToHod(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.promoteFacultyToHod(id);
            redirectAttributes.addFlashAttribute("success", "Faculty promoted to HOD successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/faculty";
    }
    
    @PostMapping("/{id}/demote")
    public String demoteToFaculty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            registrationService.demoteHodToFaculty(id);
            redirectAttributes.addFlashAttribute("success", "HOD demoted to Faculty successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/faculty";
    }
}
