package com.studentportal.controller;

import com.studentportal.dto.NoticeDTO;
import com.studentportal.model.Notice;
import com.studentportal.model.User;
import com.studentportal.service.NoticeService;
import com.studentportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/notices")
public class AdminNoticeController {
    
    private final NoticeService noticeService;
    private final UserService userService;
    
    public AdminNoticeController(NoticeService noticeService, UserService userService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }
    
    @GetMapping
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeService.findAll().stream()
                .map(noticeService::toDTO)
                .collect(Collectors.toList()));
        return "admin/notices/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "admin/notices/form";
    }
    
    @PostMapping("/add")
    public String addNotice(@Valid @ModelAttribute("notice") NoticeDTO dto,
                           BindingResult result,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/notices/form";
        }
        
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        
        noticeService.create(dto, user.getId());
        redirectAttributes.addFlashAttribute("success", "Notice posted successfully");
        return "redirect:/admin/notices";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        model.addAttribute("notice", noticeService.toDTO(notice));
        model.addAttribute("isEdit", true);
        return "admin/notices/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateNotice(@PathVariable Long id,
                              @Valid @ModelAttribute("notice") NoticeDTO dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/notices/form";
        }
        
        noticeService.update(id, dto);
        redirectAttributes.addFlashAttribute("success", "Notice updated successfully");
        return "redirect:/admin/notices";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        noticeService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Notice deleted successfully");
        return "redirect:/admin/notices";
    }
}
