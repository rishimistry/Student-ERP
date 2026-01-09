package com.studentportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentportal.model.Mark;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByStudent(Student student);
    List<Mark> findByStudentAndSubject(Student student, Subject subject);
    List<Mark> findBySubject(Subject subject);
    Optional<Mark> findByStudentAndSubjectAndExamType(Student student, Subject subject, String examType);
    
    @Query("SELECT AVG(m.marksObtained / m.maxMarks * 100) FROM Mark m WHERE m.student = :student")
    Double calculateAveragePercentageByStudent(@Param("student") Student student);
    
    List<Mark> findByStudentOrderBySubjectSubjectNameAsc(Student student);
    
    void deleteByStudent(Student student);
}
