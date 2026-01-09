package com.studentportal.service;

import com.studentportal.dto.StudentDTO;
import com.studentportal.model.Student;
import com.studentportal.model.User;
import com.studentportal.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> findByUser(User user) {
        return studentRepository.findByUser(user);
    }
    
    public Optional<Student> findByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }
    
    public Optional<Student> findByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }
    
    public List<Student> findByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }
    
    public List<Student> findBySemester(String semester) {
        return studentRepository.findBySemester(semester);
    }
    
    public List<Student> findByDepartmentAndSemester(String department, String semester) {
        return studentRepository.findByDepartmentAndSemester(department, semester);
    }
    
    public StudentDTO toDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .username(student.getUser().getUsername())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .rollNumber(student.getRollNumber())
                .dateOfBirth(student.getDateOfBirth())
                .phone(student.getPhone())
                .address(student.getAddress())
                .department(student.getDepartment())
                .semester(student.getSemester())
                .admissionYear(student.getAdmissionYear())
                .guardianName(student.getGuardianName())
                .guardianPhone(student.getGuardianPhone())
                .build();
    }
    
    public long count() {
        return studentRepository.count();
    }
}
