package com.studentportal.repository;

import com.studentportal.model.Student;
import com.studentportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
    Optional<Student> findByUserId(Long userId);
    Optional<Student> findByRollNumber(String rollNumber);
    boolean existsByRollNumber(String rollNumber);
    List<Student> findByDepartment(String department);
    List<Student> findBySemester(String semester);
    
    @Query("SELECT s FROM Student s WHERE s.department = :dept AND s.semester = :sem")
    List<Student> findByDepartmentAndSemester(@Param("dept") String department, @Param("sem") String semester);
}
