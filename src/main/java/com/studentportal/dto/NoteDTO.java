package com.studentportal.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class NoteDTO {
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    @NotNull(message = "Subject is required")
    private Long subjectId;
    
    private String subjectName;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String uploadedByName;
    private LocalDateTime uploadedAt;
    
    public NoteDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    
    public String getUploadedByName() { return uploadedByName; }
    public void setUploadedByName(String uploadedByName) { this.uploadedByName = uploadedByName; }
    
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    
    // Builder
    public static NoteDTOBuilder builder() { return new NoteDTOBuilder(); }
    
    public static class NoteDTOBuilder {
        private NoteDTO dto = new NoteDTO();
        
        public NoteDTOBuilder id(Long id) { dto.id = id; return this; }
        public NoteDTOBuilder title(String title) { dto.title = title; return this; }
        public NoteDTOBuilder description(String description) { dto.description = description; return this; }
        public NoteDTOBuilder subjectId(Long subjectId) { dto.subjectId = subjectId; return this; }
        public NoteDTOBuilder subjectName(String subjectName) { dto.subjectName = subjectName; return this; }
        public NoteDTOBuilder fileName(String fileName) { dto.fileName = fileName; return this; }
        public NoteDTOBuilder filePath(String filePath) { dto.filePath = filePath; return this; }
        public NoteDTOBuilder fileSize(Long fileSize) { dto.fileSize = fileSize; return this; }
        public NoteDTOBuilder uploadedByName(String uploadedByName) { dto.uploadedByName = uploadedByName; return this; }
        public NoteDTOBuilder uploadedAt(LocalDateTime uploadedAt) { dto.uploadedAt = uploadedAt; return this; }
        
        public NoteDTO build() { return dto; }
    }
}
