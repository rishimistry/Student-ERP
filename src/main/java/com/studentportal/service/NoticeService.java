package com.studentportal.service;

import com.studentportal.dto.NoticeDTO;
import com.studentportal.model.Notice;
import com.studentportal.model.User;
import com.studentportal.repository.NoticeRepository;
import com.studentportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    
    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }
    
    public List<Notice> findAll() {
        return noticeRepository.findAllByOrderByPostedAtDesc();
    }
    
    public List<Notice> findActiveNotices() {
        return noticeRepository.findActiveNotices(LocalDateTime.now());
    }
    
    public List<Notice> findRecentNotices() {
        return noticeRepository.findTop5ByOrderByPostedAtDesc();
    }
    
    public Optional<Notice> findById(Long id) {
        return noticeRepository.findById(id);
    }
    
    @Transactional
    public Notice create(NoticeDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .important(dto.isImportant())
                .postedBy(user)
                .expiresAt(dto.getExpiresAt())
                .build();
        
        return noticeRepository.save(notice);
    }
    
    @Transactional
    public Notice update(Long id, NoticeDTO dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setCategory(dto.getCategory());
        notice.setImportant(dto.isImportant());
        notice.setExpiresAt(dto.getExpiresAt());
        
        return noticeRepository.save(notice);
    }
    
    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
    
    public NoticeDTO toDTO(Notice notice) {
        return NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .category(notice.getCategory())
                .important(notice.isImportant())
                .postedByName(notice.getPostedBy() != null ? notice.getPostedBy().getFullName() : "System")
                .postedAt(notice.getPostedAt())
                .expiresAt(notice.getExpiresAt())
                .build();
    }
    
    public List<NoticeDTO> toDTOList(List<Notice> notices) {
        return notices.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    public long count() {
        return noticeRepository.count();
    }
}
