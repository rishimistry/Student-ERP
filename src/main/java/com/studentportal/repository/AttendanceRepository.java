package com.studentportal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentportal.model.Attendance;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(Student student);
    List<Attendance> findByStudentAndSubject(Student student, Subject subject);
    List<Attendance> findBySubjectAndDate(Subject subject, LocalDate date);
    Optional<Attendance> findByStudentAndSubjectAndDate(Student student, Subject subject, LocalDate date);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.status = 'PRESENT'")
    long countPresentByStudent(@Param("student") Student student);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student")
    long countTotalByStudent(@Param("student") Student student);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.subject = :subject AND a.status = 'PRESENT'")
    long countPresentByStudentAndSubject(@Param("student") Student student, @Param("subject") Subject subject);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.subject = :subject")
    long countTotalByStudentAndSubject(@Param("student") Student student, @Param("subject") Subject subject);
    
    List<Attendance> findByStudentOrderByDateDesc(Student student);
    List<Attendance> findByDateBetweenAndStudent(LocalDate startDate, LocalDate endDate, Student student);
    
    void deleteByStudent(Student student);
}
