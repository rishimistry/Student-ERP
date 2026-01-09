package com.studentportal.repository;

import com.studentportal.model.Note;
import com.studentportal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findBySubject(Subject subject);
    List<Note> findBySubjectDepartment(String department);
    List<Note> findAllByOrderByUploadedAtDesc();
    List<Note> findByTitleContainingIgnoreCase(String title);
}
