package com.studentportal.controller;

import com.studentportal.dto.DashboardDTO;
import com.studentportal.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    
    private final DashboardService dashboardService;
    
    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardDTO dashboard = dashboardService.getAdminDashboard();
        model.addAttribute("dashboard", dashboard);
        return "admin/dashboard";
    }
}
