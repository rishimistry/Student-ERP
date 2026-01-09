package com.studentportal.controller;

import com.studentportal.dto.StudentDTO;
import com.studentportal.model.Student;
import com.studentportal.service.StudentService;
import com.studentportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/students")
public class AdminStudentController {
    
    private final StudentService studentService;
    private final UserService userService;
    
    public AdminStudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }
    
    @GetMapping
    public String listStudents(Model model) {
        List<StudentDTO> students = studentService.findAll().stream()
                .map(studentService::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("students", students);
        return "admin/students/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("isEdit", false);
        return "admin/students/form";
    }
    
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") StudentDTO dto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/students/form";
        }
        
        try {
            userService.createStudent(dto);
            redirectAttributes.addFlashAttribute("success", "Student added successfully");
            return "redirect:/admin/students";
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", "error.student", e.getMessage());
            model.addAttribute("isEdit", false);
            return "admin/students/form";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        model.addAttribute("student", studentService.toDTO(student));
        model.addAttribute("isEdit", true);
        return "admin/students/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                               @Valid @ModelAttribute("student") StudentDTO dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "admin/students/form";
        }
        
        try {
            userService.updateStudent(id, dto);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully");
            return "redirect:/admin/students";
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.student", e.getMessage());
            model.addAttribute("isEdit", true);
            return "admin/students/form";
        }
    }
    
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete student");
        }
        return "redirect:/admin/students";
    }
    
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        model.addAttribute("student", studentService.toDTO(student));
        return "admin/students/view";
    }
}
