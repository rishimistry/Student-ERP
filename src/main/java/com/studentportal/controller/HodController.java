package com.studentportal.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentportal.model.Faculty;
import com.studentportal.model.Role;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;
import com.studentportal.model.User;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;
import com.studentportal.repository.UserRepository;

@Controller
@RequestMapping("/hod")
public class HodController {
    
    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    
    public HodController(UserRepository userRepository, FacultyRepository facultyRepository,
                        StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }
    
    private String getHodDepartment(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        if (user != null) {
            Faculty faculty = facultyRepository.findByUser(user).orElse(null);
            if (faculty != null) {
                return faculty.getDepartment();
            }
        }
        return null;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        String department = faculty != null ? faculty.getDepartment() : null;
        
        // Count department-specific stats
        long deptStudents = studentRepository.findAll().stream()
                .filter(s -> department != null && department.equals(s.getDepartment()))
                .count();
        
        long deptFaculty = facultyRepository.findAll().stream()
                .filter(f -> department != null && department.equals(f.getDepartment()) 
                        && f.getUser().getRole() == Role.FACULTY)
                .count();
        
        long deptSubjects = subjectRepository.findAll().stream()
                .filter(s -> department != null && department.equals(s.getDepartment()))
                .count();
        
        // Count pending registrations for department
        long pendingStudents = studentRepository.findAll().stream()
                .filter(s -> !s.getUser().isActive() && department != null && department.equals(s.getDepartment()))
                .count();
        
        long pendingFaculty = facultyRepository.findAll().stream()
                .filter(f -> !f.getUser().isActive() && department != null && department.equals(f.getDepartment()))
                .count();
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        model.addAttribute("department", department);
        model.addAttribute("totalStudents", deptStudents);
        model.addAttribute("totalFaculty", deptFaculty);
        model.addAttribute("totalSubjects", deptSubjects);
        model.addAttribute("pendingRegistrations", pendingStudents + pendingFaculty);
        
        return "hod/dashboard";
    }
    
    @GetMapping("/faculty")
    public String facultyList(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty hod = facultyRepository.findByUser(user).orElse(null);
        String department = hod != null ? hod.getDepartment() : null;
        
        // Get faculty from same department only
        List<Faculty> facultyMembers = facultyRepository.findAll().stream()
                .filter(f -> department != null && department.equals(f.getDepartment()) 
                        && f.getUser().isActive()
                        && !f.getUser().getId().equals(user.getId())) // Exclude self
                .toList();
        
        model.addAttribute("user", user);
        model.addAttribute("hod", hod);
        model.addAttribute("department", department);
        model.addAttribute("facultyList", facultyMembers);
        
        return "hod/faculty";
    }
    
    @GetMapping("/students")
    public String students(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty hod = facultyRepository.findByUser(user).orElse(null);
        String department = hod != null ? hod.getDepartment() : null;
        
        // Get students from same department only
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> department != null && department.equals(s.getDepartment()) && s.getUser().isActive())
                .toList();
        
        model.addAttribute("user", user);
        model.addAttribute("hod", hod);
        model.addAttribute("department", department);
        model.addAttribute("students", students);
        
        return "hod/students";
    }
    
    @GetMapping("/subjects")
    public String subjects(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty hod = facultyRepository.findByUser(user).orElse(null);
        String department = hod != null ? hod.getDepartment() : null;
        
        // Get subjects from same department only
        List<Subject> subjects = subjectRepository.findAll().stream()
                .filter(s -> department != null && department.equals(s.getDepartment()))
                .toList();
        
        model.addAttribute("user", user);
        model.addAttribute("hod", hod);
        model.addAttribute("department", department);
        model.addAttribute("subjects", subjects);
        
        return "hod/subjects";
    }
    
    @GetMapping("/registrations")
    public String registrations(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty hod = facultyRepository.findByUser(user).orElse(null);
        String department = hod != null ? hod.getDepartment() : null;
        
        // Get pending students from same department
        List<Student> pendingStudents = studentRepository.findAll().stream()
                .filter(s -> !s.getUser().isActive() && department != null && department.equals(s.getDepartment()))
                .toList();
        
        // Get pending faculty from same department
        List<Faculty> pendingFaculty = facultyRepository.findAll().stream()
                .filter(f -> !f.getUser().isActive() && department != null && department.equals(f.getDepartment()))
                .toList();
        
        model.addAttribute("user", user);
        model.addAttribute("hod", hod);
        model.addAttribute("department", department);
        model.addAttribute("pendingStudents", pendingStudents);
        model.addAttribute("pendingFaculty", pendingFaculty);
        
        return "hod/registrations";
    }
    
    @PostMapping("/registrations/student/{id}/approve")
    public String approveStudent(@PathVariable Long id, Authentication authentication, 
                                 RedirectAttributes redirectAttributes) {
        String department = getHodDepartment(authentication);
        Student student = studentRepository.findById(id).orElse(null);
        
        if (student != null && department != null && department.equals(student.getDepartment())) {
            User user = student.getUser();
            user.setActive(true);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Student registration approved");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot approve student from different department");
        }
        
        return "redirect:/hod/registrations";
    }
    
    @PostMapping("/registrations/student/{id}/reject")
    public String rejectStudent(@PathVariable Long id, Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        String department = getHodDepartment(authentication);
        Student student = studentRepository.findById(id).orElse(null);
        
        if (student != null && department != null && department.equals(student.getDepartment())) {
            User user = student.getUser();
            studentRepository.delete(student);
            userRepository.delete(user);
            redirectAttributes.addFlashAttribute("success", "Student registration rejected");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot reject student from different department");
        }
        
        return "redirect:/hod/registrations";
    }
    
    @PostMapping("/registrations/faculty/{id}/approve")
    public String approveFaculty(@PathVariable Long id, Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        String department = getHodDepartment(authentication);
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        
        if (faculty != null && department != null && department.equals(faculty.getDepartment())) {
            User user = faculty.getUser();
            user.setActive(true);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Faculty registration approved");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot approve faculty from different department");
        }
        
        return "redirect:/hod/registrations";
    }
    
    @PostMapping("/registrations/faculty/{id}/reject")
    public String rejectFaculty(@PathVariable Long id, Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        String department = getHodDepartment(authentication);
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        
        if (faculty != null && department != null && department.equals(faculty.getDepartment())) {
            User user = faculty.getUser();
            facultyRepository.delete(faculty);
            userRepository.delete(user);
            redirectAttributes.addFlashAttribute("success", "Faculty registration rejected");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot reject faculty from different department");
        }
        
        return "redirect:/hod/registrations";
    }
    
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Faculty faculty = facultyRepository.findByUser(user).orElse(null);
        
        model.addAttribute("user", user);
        model.addAttribute("faculty", faculty);
        
        return "hod/profile";
    }
}
