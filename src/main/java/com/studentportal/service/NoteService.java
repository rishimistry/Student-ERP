package com.studentportal.service;

import com.studentportal.dto.NoteDTO;
import com.studentportal.model.Note;
import com.studentportal.model.Subject;
import com.studentportal.model.User;
import com.studentportal.repository.NoteRepository;
import com.studentportal.repository.SubjectRepository;
import com.studentportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteService {
    
    private final NoteRepository noteRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    
    @Value("${app.upload.dir:uploads/notes}")
    private String uploadDir;
    
    public NoteService(NoteRepository noteRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }
    
    public List<Note> findAll() {
        return noteRepository.findAllByOrderByUploadedAtDesc();
    }
    
    public List<Note> findBySubject(Subject subject) {
        return noteRepository.findBySubject(subject);
    }
    
    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }
    
    @Transactional
    public Note upload(NoteDTO dto, MultipartFile file, Long userId) throws IOException {
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : ".pdf";
        String storedFilename = UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(storedFilename);
        
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        Note note = Note.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .subject(subject)
                .fileName(originalFilename)
                .filePath(storedFilename)
                .fileSize(file.getSize())
                .uploadedBy(user)
                .build();
        
        return noteRepository.save(note);
    }
    
    @Transactional
    public void delete(Long id) throws IOException {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
        
        Path filePath = Paths.get(uploadDir).resolve(note.getFilePath());
        Files.deleteIfExists(filePath);
        
        noteRepository.delete(note);
    }
    
    public Path getFilePath(Note note) {
        return Paths.get(uploadDir).resolve(note.getFilePath());
    }
    
    public NoteDTO toDTO(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .description(note.getDescription())
                .subjectId(note.getSubject().getId())
                .subjectName(note.getSubject().getSubjectName())
                .fileName(note.getFileName())
                .filePath(note.getFilePath())
                .fileSize(note.getFileSize())
                .uploadedByName(note.getUploadedBy() != null ? note.getUploadedBy().getFullName() : "Unknown")
                .uploadedAt(note.getUploadedAt())
                .build();
    }
    
    public List<NoteDTO> toDTOList(List<Note> notes) {
        return notes.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public long count() {
        return noteRepository.count();
    }
}
