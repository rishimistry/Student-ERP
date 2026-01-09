package com.studentportal.service;

import com.studentportal.dto.MarkDTO;
import com.studentportal.model.*;
import com.studentportal.repository.MarkRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarkService {
    
    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    
    public MarkService(MarkRepository markRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }
    
    public List<Mark> findByStudent(Student student) {
        return markRepository.findByStudentOrderBySubjectSubjectNameAsc(student);
    }
    
    public Optional<Mark> findById(Long id) {
        return markRepository.findById(id);
    }
    
    public Double calculateAveragePercentage(Student student) {
        Double avg = markRepository.calculateAveragePercentageByStudent(student);
        return avg != null ? avg : 0.0;
    }
    
    @Transactional
    public Mark saveOrUpdate(MarkDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        
        Optional<Mark> existing = markRepository
                .findByStudentAndSubjectAndExamType(student, subject, dto.getExamType());
        
        Mark mark;
        if (existing.isPresent()) {
            mark = existing.get();
            mark.setMarksObtained(dto.getMarksObtained());
            mark.setMaxMarks(dto.getMaxMarks());
            mark.setRemarks(dto.getRemarks());
        } else {
            mark = Mark.builder()
                    .student(student)
                    .subject(subject)
                    .examType(dto.getExamType())
                    .marksObtained(dto.getMarksObtained())
                    .maxMarks(dto.getMaxMarks())
                    .remarks(dto.getRemarks())
                    .build();
        }
        
        return markRepository.save(mark);
    }
    
    @Transactional
    public void delete(Long id) {
        markRepository.deleteById(id);
    }
    
    public MarkDTO toDTO(Mark mark) {
        return MarkDTO.builder()
                .id(mark.getId())
                .studentId(mark.getStudent().getId())
                .studentName(mark.getStudent().getUser().getFullName())
                .rollNumber(mark.getStudent().getRollNumber())
                .subjectId(mark.getSubject().getId())
                .subjectName(mark.getSubject().getSubjectName())
                .examType(mark.getExamType())
                .marksObtained(mark.getMarksObtained())
                .maxMarks(mark.getMaxMarks())
                .remarks(mark.getRemarks())
                .percentage(mark.getPercentage())
                .build();
    }
    
    public List<MarkDTO> toDTOList(List<Mark> marks) {
        return marks.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
