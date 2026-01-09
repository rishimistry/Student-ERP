package com.studentportal.controller;

import com.studentportal.model.Notice;
import com.studentportal.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student/notices")
public class StudentNoticeController {
    
    private final NoticeService noticeService;
    
    public StudentNoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    
    @GetMapping
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeService.findActiveNotices().stream()
                .map(noticeService::toDTO)
                .collect(Collectors.toList()));
        return "student/notices";
    }
    
    @GetMapping("/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        model.addAttribute("notice", noticeService.toDTO(notice));
        return "student/notice-detail";
    }
}
