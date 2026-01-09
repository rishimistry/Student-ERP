package com.studentportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "roll_number", unique = true, nullable = false, length = 20)
    private String rollNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 255)
    private String address;
    
    @Column(length = 50)
    private String department;
    
    @Column(length = 20)
    private String semester;
    
    @Column(name = "admission_year")
    private Integer admissionYear;
    
    @Column(name = "guardian_name", length = 100)
    private String guardianName;
    
    @Column(name = "guardian_phone", length = 20)
    private String guardianPhone;
    
    public Student() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public Integer getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }
    
    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }
    
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    
    // Builder
    public static StudentBuilder builder() { return new StudentBuilder(); }
    
    public static class StudentBuilder {
        private Student student = new Student();
        
        public StudentBuilder id(Long id) { student.id = id; return this; }
        public StudentBuilder user(User user) { student.user = user; return this; }
        public StudentBuilder rollNumber(String rollNumber) { student.rollNumber = rollNumber; return this; }
        public StudentBuilder dateOfBirth(LocalDate dateOfBirth) { student.dateOfBirth = dateOfBirth; return this; }
        public StudentBuilder phone(String phone) { student.phone = phone; return this; }
        public StudentBuilder address(String address) { student.address = address; return this; }
        public StudentBuilder department(String department) { student.department = department; return this; }
        public StudentBuilder semester(String semester) { student.semester = semester; return this; }
        public StudentBuilder admissionYear(Integer admissionYear) { student.admissionYear = admissionYear; return this; }
        public StudentBuilder guardianName(String guardianName) { student.guardianName = guardianName; return this; }
        public StudentBuilder guardianPhone(String guardianPhone) { student.guardianPhone = guardianPhone; return this; }
        
        public Student build() { return student; }
    }
}
