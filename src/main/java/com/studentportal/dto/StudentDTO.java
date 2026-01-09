package com.studentportal.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class StudentDTO {
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    private String username;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Roll number is required")
    @Size(max = 20, message = "Roll number must not exceed 20 characters")
    private String rollNumber;
    
    private LocalDate dateOfBirth;
    
    @Pattern(regexp = "^$|^[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;
    
    private String address;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotBlank(message = "Semester is required")
    private String semester;
    
    private Integer admissionYear;
    private String guardianName;
    private String guardianPhone;
    
    public StudentDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
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
    public static StudentDTOBuilder builder() { return new StudentDTOBuilder(); }
    
    public static class StudentDTOBuilder {
        private StudentDTO dto = new StudentDTO();
        
        public StudentDTOBuilder id(Long id) { dto.id = id; return this; }
        public StudentDTOBuilder username(String username) { dto.username = username; return this; }
        public StudentDTOBuilder password(String password) { dto.password = password; return this; }
        public StudentDTOBuilder fullName(String fullName) { dto.fullName = fullName; return this; }
        public StudentDTOBuilder email(String email) { dto.email = email; return this; }
        public StudentDTOBuilder rollNumber(String rollNumber) { dto.rollNumber = rollNumber; return this; }
        public StudentDTOBuilder dateOfBirth(LocalDate dateOfBirth) { dto.dateOfBirth = dateOfBirth; return this; }
        public StudentDTOBuilder phone(String phone) { dto.phone = phone; return this; }
        public StudentDTOBuilder address(String address) { dto.address = address; return this; }
        public StudentDTOBuilder department(String department) { dto.department = department; return this; }
        public StudentDTOBuilder semester(String semester) { dto.semester = semester; return this; }
        public StudentDTOBuilder admissionYear(Integer admissionYear) { dto.admissionYear = admissionYear; return this; }
        public StudentDTOBuilder guardianName(String guardianName) { dto.guardianName = guardianName; return this; }
        public StudentDTOBuilder guardianPhone(String guardianPhone) { dto.guardianPhone = guardianPhone; return this; }
        
        public StudentDTO build() { return dto; }
    }
}
