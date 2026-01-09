package com.studentportal.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class NoticeDTO {
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String category;
    private boolean important;
    private String postedByName;
    private LocalDateTime postedAt;
    private LocalDateTime expiresAt;
    
    public NoticeDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
    
    public String getPostedByName() { return postedByName; }
    public void setPostedByName(String postedByName) { this.postedByName = postedByName; }
    
    public LocalDateTime getPostedAt() { return postedAt; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    // Builder
    public static NoticeDTOBuilder builder() { return new NoticeDTOBuilder(); }
    
    public static class NoticeDTOBuilder {
        private NoticeDTO dto = new NoticeDTO();
        
        public NoticeDTOBuilder id(Long id) { dto.id = id; return this; }
        public NoticeDTOBuilder title(String title) { dto.title = title; return this; }
        public NoticeDTOBuilder content(String content) { dto.content = content; return this; }
        public NoticeDTOBuilder category(String category) { dto.category = category; return this; }
        public NoticeDTOBuilder important(boolean important) { dto.important = important; return this; }
        public NoticeDTOBuilder postedByName(String postedByName) { dto.postedByName = postedByName; return this; }
        public NoticeDTOBuilder postedAt(LocalDateTime postedAt) { dto.postedAt = postedAt; return this; }
        public NoticeDTOBuilder expiresAt(LocalDateTime expiresAt) { dto.expiresAt = expiresAt; return this; }
        
        public NoticeDTO build() { return dto; }
    }
}
