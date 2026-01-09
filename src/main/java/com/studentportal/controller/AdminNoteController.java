package com.studentportal.controller;

import com.studentportal.dto.NoteDTO;
import com.studentportal.model.User;
import com.studentportal.service.NoteService;
import com.studentportal.service.SubjectService;
import com.studentportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/notes")
public class AdminNoteController {
    
    private final NoteService noteService;
    private final SubjectService subjectService;
    private final UserService userService;
    
    public AdminNoteController(NoteService noteService, SubjectService subjectService, UserService userService) {
        this.noteService = noteService;
        this.subjectService = subjectService;
        this.userService = userService;
    }
    
    @GetMapping
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.findAll().stream()
                .map(noteService::toDTO)
                .collect(Collectors.toList()));
        return "admin/notes/list";
    }
    
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("note", new NoteDTO());
        model.addAttribute("subjects", subjectService.findAll());
        return "admin/notes/upload";
    }
    
    @PostMapping("/upload")
    public String uploadNote(@Valid @ModelAttribute("note") NoteDTO dto,
                            BindingResult result,
                            @RequestParam("file") MultipartFile file,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/notes/upload";
        }
        
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload");
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/notes/upload";
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            model.addAttribute("error", "Only PDF files are allowed");
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/notes/upload";
        }
        
        try {
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            
            noteService.upload(dto, file, user.getId());
            redirectAttributes.addFlashAttribute("success", "Note uploaded successfully");
            return "redirect:/admin/notes";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
            model.addAttribute("subjects", subjectService.findAll());
            return "admin/notes/upload";
        }
    }
    
    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            noteService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Note deleted successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete note");
        }
        return "redirect:/admin/notes";
    }
}
