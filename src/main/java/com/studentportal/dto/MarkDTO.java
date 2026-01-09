package com.studentportal.dto;

import jakarta.validation.constraints.*;

public class MarkDTO {
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    private String studentName;
    private String rollNumber;
    
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    
    private String subjectName;
    
    @NotBlank(message = "Exam type is required")
    private String examType;
    
    @NotNull(message = "Marks obtained is required")
    @Min(value = 0, message = "Marks cannot be negative")
    private Double marksObtained;
    
    @NotNull(message = "Maximum marks is required")
    @Min(value = 1, message = "Maximum marks must be at least 1")
    private Double maxMarks;
    
    private String remarks;
    private Double percentage;
    
    public MarkDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    
    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { this.marksObtained = marksObtained; }
    
    public Double getMaxMarks() { return maxMarks; }
    public void setMaxMarks(Double maxMarks) { this.maxMarks = maxMarks; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    
    // Builder
    public static MarkDTOBuilder builder() { return new MarkDTOBuilder(); }
    
    public static class MarkDTOBuilder {
        private MarkDTO dto = new MarkDTO();
        
        public MarkDTOBuilder id(Long id) { dto.id = id; return this; }
        public MarkDTOBuilder studentId(Long studentId) { dto.studentId = studentId; return this; }
        public MarkDTOBuilder studentName(String studentName) { dto.studentName = studentName; return this; }
        public MarkDTOBuilder rollNumber(String rollNumber) { dto.rollNumber = rollNumber; return this; }
        public MarkDTOBuilder subjectId(Long subjectId) { dto.subjectId = subjectId; return this; }
        public MarkDTOBuilder subjectName(String subjectName) { dto.subjectName = subjectName; return this; }
        public MarkDTOBuilder examType(String examType) { dto.examType = examType; return this; }
        public MarkDTOBuilder marksObtained(Double marksObtained) { dto.marksObtained = marksObtained; return this; }
        public MarkDTOBuilder maxMarks(Double maxMarks) { dto.maxMarks = maxMarks; return this; }
        public MarkDTOBuilder remarks(String remarks) { dto.remarks = remarks; return this; }
        public MarkDTOBuilder percentage(Double percentage) { dto.percentage = percentage; return this; }
        
        public MarkDTO build() { return dto; }
    }
}
