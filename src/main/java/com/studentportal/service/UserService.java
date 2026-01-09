package com.studentportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportal.dto.StudentDTO;
import com.studentportal.model.Role;
import com.studentportal.model.Student;
import com.studentportal.model.User;
import com.studentportal.repository.AttendanceRepository;
import com.studentportal.repository.MarkRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final MarkRepository markRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, StudentRepository studentRepository,
                       AttendanceRepository attendanceRepository, MarkRepository markRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
        this.markRepository = markRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> findAllStudentUsers() {
        return userRepository.findByRole(Role.STUDENT);
    }
    
    @Transactional
    public Student createStudent(StudentDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (studentRepository.existsByRollNumber(dto.getRollNumber())) {
            throw new IllegalArgumentException("Roll number already exists");
        }
        
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(Role.STUDENT)
                .active(true)
                .build();
        user = userRepository.save(user);
        
        Student student = Student.builder()
                .user(user)
                .rollNumber(dto.getRollNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .department(dto.getDepartment())
                .semester(dto.getSemester())
                .admissionYear(dto.getAdmissionYear())
                .guardianName(dto.getGuardianName())
                .guardianPhone(dto.getGuardianPhone())
                .build();
        
        return studentRepository.save(student);
    }
    
    @Transactional
    public Student updateStudent(Long studentId, StudentDTO dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        User user = student.getUser();
        
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);
        
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setDepartment(dto.getDepartment());
        student.setSemester(dto.getSemester());
        student.setAdmissionYear(dto.getAdmissionYear());
        student.setGuardianName(dto.getGuardianName());
        student.setGuardianPhone(dto.getGuardianPhone());
        
        return studentRepository.save(student);
    }
    
    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        User user = student.getUser();
        
        // Delete related records first
        attendanceRepository.deleteByStudent(student);
        markRepository.deleteByStudent(student);
        
        // Now delete student and user
        studentRepository.delete(student);
        userRepository.delete(user);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
