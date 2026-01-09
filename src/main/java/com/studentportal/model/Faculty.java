package com.studentportal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "employee_id", unique = true, nullable = false, length = 20)
    private String employeeId;
    
    @Column(length = 50)
    private String department;
    
    @Column(length = 50)
    private String designation;
    
    @Column(length = 100)
    private String specialization;
    
    @Column(length = 20)
    private String phone;
    
    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;
    
    @Column(length = 50)
    private String qualification;
    
    public Faculty() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    // Builder
    public static FacultyBuilder builder() { return new FacultyBuilder(); }
    
    public static class FacultyBuilder {
        private Faculty faculty = new Faculty();
        
        public FacultyBuilder id(Long id) { faculty.id = id; return this; }
        public FacultyBuilder user(User user) { faculty.user = user; return this; }
        public FacultyBuilder employeeId(String employeeId) { faculty.employeeId = employeeId; return this; }
        public FacultyBuilder department(String department) { faculty.department = department; return this; }
        public FacultyBuilder designation(String designation) { faculty.designation = designation; return this; }
        public FacultyBuilder specialization(String specialization) { faculty.specialization = specialization; return this; }
        public FacultyBuilder phone(String phone) { faculty.phone = phone; return this; }
        public FacultyBuilder dateOfJoining(LocalDate dateOfJoining) { faculty.dateOfJoining = dateOfJoining; return this; }
        public FacultyBuilder qualification(String qualification) { faculty.qualification = qualification; return this; }
        
        public Faculty build() { return faculty; }
    }
}
