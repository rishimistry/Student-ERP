package com.studentportal.controller;

import com.studentportal.dto.SubjectDTO;
import com.studentportal.model.Subject;
import com.studentportal.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/subjects")
public class AdminSubjectController {
    
    private final SubjectService subjectService;
    
    public AdminSubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll().stream()
                .map(subjectService::toDTO)
                .collect(Collectors.toList()));
        return "admin/subjects/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new SubjectDTO());
        return "admin/subjects/form";
    }
    
    @PostMapping("/add")
    public String addSubject(@Valid @ModelAttribute("subject") SubjectDTO dto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/subjects/form";
        }
        
        try {
            subjectService.create(dto);
            redirectAttributes.addFlashAttribute("success", "Subject added successfully");
            return "redirect:/admin/subjects";
        } catch (IllegalArgumentException e) {
            result.rejectValue("subjectCode", "error.subject", e.getMessage());
            return "admin/subjects/form";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        model.addAttribute("subject", subjectService.toDTO(subject));
        model.addAttribute("isEdit", true);
        return "admin/subjects/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateSubject(@PathVariable Long id,
                               @Valid @ModelAttribute("subject") SubjectDTO dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/subjects/form";
        }
        
        subjectService.update(id, dto);
        redirectAttributes.addFlashAttribute("success", "Subject updated successfully");
        return "redirect:/admin/subjects";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subjectService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Subject deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete subject with existing records");
        }
        return "redirect:/admin/subjects";
    }
}
