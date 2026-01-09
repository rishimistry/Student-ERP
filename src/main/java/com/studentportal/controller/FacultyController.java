package com.studentportal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studentportal.model.Faculty;
import com.studentportal.model.User;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;
import com.studentportal.repository.UserRepository;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    
    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    
    public FacultyController(UserRepository userRepository, FacultyRepository facultyRepository,
                            StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("totalStudents", studentRepository.count());
        model.addAttribute("totalSubjects", subjectRepository.count());
        
        return "faculty/dashboard";
    }
    
    @GetMapping("/students")
    public String students(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("students", studentRepository.findAll());
        
        return "faculty/students";
    }
    
    @GetMapping("/attendance")
    public String attendance(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        
        return "faculty/attendance";
    }
    
    @GetMapping("/marks")
    public String marks(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        
        return "faculty/marks";
    }
    
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        
        return "faculty/profile";
    }
}
