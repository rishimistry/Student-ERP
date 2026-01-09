package com.studentportal.dto;

import jakarta.validation.constraints.*;

public class SubjectDTO {
    private Long id;
    
    @NotBlank(message = "Subject code is required")
    @Size(max = 20, message = "Subject code must not exceed 20 characters")
    private String subjectCode;
    
    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String subjectName;
    
    private String department;
    private String semester;
    
    @Min(value = 1, message = "Credit hours must be at least 1")
    @Max(value = 10, message = "Credit hours must not exceed 10")
    private Integer creditHours;
    
    public SubjectDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public Integer getCreditHours() { return creditHours; }
    public void setCreditHours(Integer creditHours) { this.creditHours = creditHours; }
    
    // Builder
    public static SubjectDTOBuilder builder() { return new SubjectDTOBuilder(); }
    
    public static class SubjectDTOBuilder {
        private SubjectDTO dto = new SubjectDTO();
        
        public SubjectDTOBuilder id(Long id) { dto.id = id; return this; }
        public SubjectDTOBuilder subjectCode(String subjectCode) { dto.subjectCode = subjectCode; return this; }
        public SubjectDTOBuilder subjectName(String subjectName) { dto.subjectName = subjectName; return this; }
        public SubjectDTOBuilder department(String department) { dto.department = department; return this; }
        public SubjectDTOBuilder semester(String semester) { dto.semester = semester; return this; }
        public SubjectDTOBuilder creditHours(Integer creditHours) { dto.creditHours = creditHours; return this; }
        
        public SubjectDTO build() { return dto; }
    }
}
