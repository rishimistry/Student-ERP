package com.studentportal.service;

import com.studentportal.dto.FacultyRegistrationDTO;
import com.studentportal.dto.StudentRegistrationDTO;
import com.studentportal.model.*;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationService {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;
    
    public RegistrationService(UserRepository userRepository, StudentRepository studentRepository,
                               FacultyRepository facultyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Student registerStudent(StudentRegistrationDTO dto) {
        validateStudentRegistration(dto);
        
        // Create username from roll number (lowercase, no spaces)
        String username = dto.getRollNumber().toLowerCase().replaceAll("\\s+", "");
        
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(Role.STUDENT)
                .active(false) // Requires admin approval
                .build();
        user = userRepository.save(user);
        
        Student student = Student.builder()
                .user(user)
                .rollNumber(dto.getRollNumber().toUpperCase())
                .department(dto.getDepartment())
                .semester(dto.getSemester())
                .admissionYear(dto.getAdmissionYear())
                .dateOfBirth(dto.getDateOfBirth())
                .phone(dto.getPhone())
                .guardianName(dto.getGuardianName())
                .guardianPhone(dto.getGuardianPhone())
                .build();
        
        return studentRepository.save(student);
    }
    
    private void validateStudentRegistration(StudentRegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (dto.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (studentRepository.existsByRollNumber(dto.getRollNumber().toUpperCase())) {
            throw new IllegalArgumentException("Roll number already registered");
        }
        String username = dto.getRollNumber().toLowerCase().replaceAll("\\s+", "");
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
    
    @Transactional
    public Faculty registerFaculty(FacultyRegistrationDTO dto) {
        validateFacultyRegistration(dto);
        
        // Create username from employee ID (lowercase, no spaces)
        String username = dto.getEmployeeId().toLowerCase().replaceAll("\\s+", "");
        
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(Role.FACULTY)
                .active(false) // Requires admin approval
                .build();
        user = userRepository.save(user);
        
        Faculty faculty = Faculty.builder()
                .user(user)
                .employeeId(dto.getEmployeeId().toUpperCase())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .specialization(dto.getSpecialization())
                .qualification(dto.getQualification())
                .phone(dto.getPhone())
                .dateOfJoining(dto.getDateOfJoining())
                .build();
        
        return facultyRepository.save(faculty);
    }
    
    private void validateFacultyRegistration(FacultyRegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (dto.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (facultyRepository.existsByEmployeeId(dto.getEmployeeId().toUpperCase())) {
            throw new IllegalArgumentException("Employee ID already registered");
        }
        String username = dto.getEmployeeId().toLowerCase().replaceAll("\\s+", "");
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
    
    // Get pending registrations (inactive users)
    public List<Student> getPendingStudents() {
        return studentRepository.findAll().stream()
                .filter(s -> !s.getUser().isActive())
                .toList();
    }
    
    public List<Faculty> getPendingFaculty() {
        return facultyRepository.findAll().stream()
                .filter(f -> !f.getUser().isActive() && f.getUser().getRole() == Role.FACULTY)
                .toList();
    }
    
    @Transactional
    public void approveStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        User user = student.getUser();
        user.setActive(true);
        userRepository.save(user);
    }
    
    @Transactional
    public void approveFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
        User user = faculty.getUser();
        user.setActive(true);
        userRepository.save(user);
    }
    
    @Transactional
    public void rejectStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        User user = student.getUser();
        studentRepository.delete(student);
        userRepository.delete(user);
    }
    
    @Transactional
    public void rejectFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
        User user = faculty.getUser();
        facultyRepository.delete(faculty);
        userRepository.delete(user);
    }
    
    @Transactional
    public void promoteFacultyToHod(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
        User user = faculty.getUser();
        user.setRole(Role.HOD);
        userRepository.save(user);
    }
    
    @Transactional
    public void demoteHodToFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
        User user = faculty.getUser();
        if (user.getRole() == Role.HOD) {
            user.setRole(Role.FACULTY);
            userRepository.save(user);
        }
    }
    
    public List<Faculty> getAllActiveFaculty() {
        return facultyRepository.findAll().stream()
                .filter(f -> f.getUser().isActive())
                .toList();
    }
}
