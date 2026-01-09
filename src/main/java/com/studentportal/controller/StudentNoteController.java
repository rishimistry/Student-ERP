package com.studentportal.controller;

import com.studentportal.model.Note;
import com.studentportal.service.NoteService;
import com.studentportal.service.SubjectService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student/notes")
public class StudentNoteController {
    
    private final NoteService noteService;
    private final SubjectService subjectService;
    
    public StudentNoteController(NoteService noteService, SubjectService subjectService) {
        this.noteService = noteService;
        this.subjectService = subjectService;
    }
    
    @GetMapping
    public String listNotes(
            @RequestParam(required = false) Long subjectId,
            Model model) {
        
        model.addAttribute("subjects", subjectService.findAll());
        
        if (subjectId != null) {
            subjectService.findById(subjectId).ifPresent(subject -> {
                model.addAttribute("notes", noteService.findBySubject(subject).stream()
                        .map(noteService::toDTO)
                        .collect(Collectors.toList()));
                model.addAttribute("selectedSubject", subject);
            });
        } else {
            model.addAttribute("notes", noteService.findAll().stream()
                    .map(noteService::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return "student/notes";
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadNote(@PathVariable Long id) throws IOException {
        Note note = noteService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
        
        Path filePath = noteService.getFilePath(note);
        Resource resource = new UrlResource(filePath.toUri());
        
        if (!resource.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + note.getFileName() + "\"")
                .body(resource);
    }
}
