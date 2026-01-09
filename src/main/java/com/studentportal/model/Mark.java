package com.studentportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marks", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "subject_id", "exam_type"})
})
public class Mark {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(name = "exam_type", nullable = false, length = 50)
    private String examType;
    
    @Column(name = "marks_obtained", nullable = false)
    private Double marksObtained;
    
    @Column(name = "max_marks", nullable = false)
    private Double maxMarks;
    
    @Column(length = 255)
    private String remarks;
    
    public Mark() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    
    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { this.marksObtained = marksObtained; }
    
    public Double getMaxMarks() { return maxMarks; }
    public void setMaxMarks(Double maxMarks) { this.maxMarks = maxMarks; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public Double getPercentage() {
        if (maxMarks == null || maxMarks == 0) return 0.0;
        return (marksObtained / maxMarks) * 100;
    }
    
    // Builder
    public static MarkBuilder builder() { return new MarkBuilder(); }
    
    public static class MarkBuilder {
        private Mark mark = new Mark();
        
        public MarkBuilder id(Long id) { mark.id = id; return this; }
        public MarkBuilder student(Student student) { mark.student = student; return this; }
        public MarkBuilder subject(Subject subject) { mark.subject = subject; return this; }
        public MarkBuilder examType(String examType) { mark.examType = examType; return this; }
        public MarkBuilder marksObtained(Double marksObtained) { mark.marksObtained = marksObtained; return this; }
        public MarkBuilder maxMarks(Double maxMarks) { mark.maxMarks = maxMarks; return this; }
        public MarkBuilder remarks(String remarks) { mark.remarks = remarks; return this; }
        
        public Mark build() { return mark; }
    }
}
