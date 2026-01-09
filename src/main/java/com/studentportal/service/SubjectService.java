package com.studentportal.service;

import com.studentportal.dto.SubjectDTO;
import com.studentportal.model.Subject;
import com.studentportal.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    
    private final SubjectRepository subjectRepository;
    
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }
    
    public List<Subject> findByDepartment(String department) {
        return subjectRepository.findByDepartment(department);
    }
    
    public List<Subject> findByDepartmentAndSemester(String department, String semester) {
        return subjectRepository.findByDepartmentAndSemester(department, semester);
    }
    
    @Transactional
    public Subject create(SubjectDTO dto) {
        if (subjectRepository.existsBySubjectCode(dto.getSubjectCode())) {
            throw new IllegalArgumentException("Subject code already exists");
        }
        
        Subject subject = Subject.builder()
                .subjectCode(dto.getSubjectCode())
                .subjectName(dto.getSubjectName())
                .department(dto.getDepartment())
                .semester(dto.getSemester())
                .creditHours(dto.getCreditHours())
                .build();
        
        return subjectRepository.save(subject);
    }
    
    @Transactional
    public Subject update(Long id, SubjectDTO dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        
        subject.setSubjectName(dto.getSubjectName());
        subject.setDepartment(dto.getDepartment());
        subject.setSemester(dto.getSemester());
        subject.setCreditHours(dto.getCreditHours());
        
        return subjectRepository.save(subject);
    }
    
    @Transactional
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
    
    public SubjectDTO toDTO(Subject subject) {
        return SubjectDTO.builder()
                .id(subject.getId())
                .subjectCode(subject.getSubjectCode())
                .subjectName(subject.getSubjectName())
                .department(subject.getDepartment())
                .semester(subject.getSemester())
                .creditHours(subject.getCreditHours())
                .build();
    }
    
    public long count() {
        return subjectRepository.count();
    }
}
