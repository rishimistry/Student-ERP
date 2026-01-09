package com.studentportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
public class Notice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(length = 50)
    private String category;
    
    @Column(nullable = false)
    private boolean important = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by")
    private User postedBy;
    
    @Column(name = "posted_at")
    private LocalDateTime postedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    public Notice() {}
    
    @PrePersist
    protected void onCreate() {
        postedAt = LocalDateTime.now();
    }
    
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
    
    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
    
    public LocalDateTime getPostedAt() { return postedAt; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    // Builder
    public static NoticeBuilder builder() { return new NoticeBuilder(); }
    
    public static class NoticeBuilder {
        private Notice notice = new Notice();
        
        public NoticeBuilder id(Long id) { notice.id = id; return this; }
        public NoticeBuilder title(String title) { notice.title = title; return this; }
        public NoticeBuilder content(String content) { notice.content = content; return this; }
        public NoticeBuilder category(String category) { notice.category = category; return this; }
        public NoticeBuilder important(boolean important) { notice.important = important; return this; }
        public NoticeBuilder postedBy(User postedBy) { notice.postedBy = postedBy; return this; }
        public NoticeBuilder expiresAt(LocalDateTime expiresAt) { notice.expiresAt = expiresAt; return this; }
        
        public Notice build() { return notice; }
    }
}
