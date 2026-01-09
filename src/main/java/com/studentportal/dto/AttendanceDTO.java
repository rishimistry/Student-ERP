package com.studentportal.dto;

import com.studentportal.model.AttendanceStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AttendanceDTO {
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    private String studentName;
    private String rollNumber;
    
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    
    private String subjectName;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Status is required")
    private AttendanceStatus status;
    
    private String remarks;
    
    public AttendanceDTO() {}
    
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
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    // Builder
    public static AttendanceDTOBuilder builder() { return new AttendanceDTOBuilder(); }
    
    public static class AttendanceDTOBuilder {
        private AttendanceDTO dto = new AttendanceDTO();
        
        public AttendanceDTOBuilder id(Long id) { dto.id = id; return this; }
        public AttendanceDTOBuilder studentId(Long studentId) { dto.studentId = studentId; return this; }
        public AttendanceDTOBuilder studentName(String studentName) { dto.studentName = studentName; return this; }
        public AttendanceDTOBuilder rollNumber(String rollNumber) { dto.rollNumber = rollNumber; return this; }
        public AttendanceDTOBuilder subjectId(Long subjectId) { dto.subjectId = subjectId; return this; }
        public AttendanceDTOBuilder subjectName(String subjectName) { dto.subjectName = subjectName; return this; }
        public AttendanceDTOBuilder date(LocalDate date) { dto.date = date; return this; }
        public AttendanceDTOBuilder status(AttendanceStatus status) { dto.status = status; return this; }
        public AttendanceDTOBuilder remarks(String remarks) { dto.remarks = remarks; return this; }
        
        public AttendanceDTO build() { return dto; }
    }
}
