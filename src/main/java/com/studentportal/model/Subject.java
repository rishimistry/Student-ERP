package com.studentportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "subject_code", unique = true, nullable = false, length = 20)
    private String subjectCode;
    
    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;
    
    @Column(length = 50)
    private String department;
    
    @Column(length = 20)
    private String semester;
    
    @Column(name = "credit_hours")
    private Integer creditHours;
    
    public Subject() {}
    
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
    public static SubjectBuilder builder() { return new SubjectBuilder(); }
    
    public static class SubjectBuilder {
        private Subject subject = new Subject();
        
        public SubjectBuilder id(Long id) { subject.id = id; return this; }
        public SubjectBuilder subjectCode(String subjectCode) { subject.subjectCode = subjectCode; return this; }
        public SubjectBuilder subjectName(String subjectName) { subject.subjectName = subjectName; return this; }
        public SubjectBuilder department(String department) { subject.department = department; return this; }
        public SubjectBuilder semester(String semester) { subject.semester = semester; return this; }
        public SubjectBuilder creditHours(Integer creditHours) { subject.creditHours = creditHours; return this; }
        
        public Subject build() { return subject; }
    }
}
