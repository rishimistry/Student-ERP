package com.studentportal.repository;

import com.studentportal.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByPostedAtDesc();
    List<Notice> findByImportantTrueOrderByPostedAtDesc();
    List<Notice> findByCategory(String category);
    
    @Query("SELECT n FROM Notice n WHERE n.expiresAt IS NULL OR n.expiresAt > :now ORDER BY n.important DESC, n.postedAt DESC")
    List<Notice> findActiveNotices(LocalDateTime now);
    
    List<Notice> findTop5ByOrderByPostedAtDesc();
}
