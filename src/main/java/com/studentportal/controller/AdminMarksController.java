package com.studentportal.controller;

import com.studentportal.dto.MarkDTO;
import com.studentportal.model.*;
import com.studentportal.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/marks")
public class AdminMarksController {
    
    private final MarkService markService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    
    public AdminMarksController(MarkService markService, StudentService studentService, SubjectService subjectService) {
        this.markService = markService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }
    
    @GetMapping
    public String listMarks(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "admin/marks/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("mark", new MarkDTO());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "admin/marks/form";
    }
    
    @PostMapping("/add")
    public String addMark(@Valid @ModelAttribute("mark") MarkDTO dto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/marks/form";
        }
        
        try {
            markService.saveOrUpdate(dto);
            redirectAttributes.addFlashAttribute("success", "Marks saved successfully");
            return "redirect:/admin/marks";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/marks/form";
        }
    }
    
    @GetMapping("/view")
    public String viewMarks(
            @RequestParam(required = false) Long studentId,
            Model model) {
        
        model.addAttribute("students", studentService.findAll());
        
        if (studentId != null) {
            Student student = studentService.findById(studentId).orElse(null);
            if (student != null) {
                List<MarkDTO> marks = markService.findByStudent(student)
                        .stream()
                        .map(markService::toDTO)
                        .collect(Collectors.toList());
                model.addAttribute("marks", marks);
                model.addAttribute("selectedStudent", studentService.toDTO(student));
            }
        }
        
        return "admin/marks/view";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteMark(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        markService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Mark deleted successfully");
        return "redirect:/admin/marks";
    }
}
