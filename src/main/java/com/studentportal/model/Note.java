package com.studentportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(name = "file_name", nullable = false)
    private String fileName;
    
    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
    
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    public Note() {}
    
    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public User getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(User uploadedBy) { this.uploadedBy = uploadedBy; }
    
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    
    // Builder
    public static NoteBuilder builder() { return new NoteBuilder(); }
    
    public static class NoteBuilder {
        private Note note = new Note();
        
        public NoteBuilder id(Long id) { note.id = id; return this; }
        public NoteBuilder title(String title) { note.title = title; return this; }
        public NoteBuilder description(String description) { note.description = description; return this; }
        public NoteBuilder subject(Subject subject) { note.subject = subject; return this; }
        public NoteBuilder fileName(String fileName) { note.fileName = fileName; return this; }
        public NoteBuilder filePath(String filePath) { note.filePath = filePath; return this; }
        public NoteBuilder fileSize(Long fileSize) { note.fileSize = fileSize; return this; }
        public NoteBuilder uploadedBy(User uploadedBy) { note.uploadedBy = uploadedBy; return this; }
        
        public Note build() { return note; }
    }
}
