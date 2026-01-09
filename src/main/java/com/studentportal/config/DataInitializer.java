package com.studentportal.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.studentportal.model.Faculty;
import com.studentportal.model.Role;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;
import com.studentportal.model.User;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;
import com.studentportal.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, StudentRepository studentRepository, 
                          SubjectRepository subjectRepository, FacultyRepository facultyRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.facultyRepository = facultyRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) {
        initializeAdmin();
        initializeSubjects();
        initializeSampleStudent();
        initializeSampleFaculty();
        initializeSampleHod();
    }
    
    private void initializeAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("System Administrator")
                    .email("admin@studentportal.com")
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }
    }
    
    private void initializeSubjects() {
        if (subjectRepository.count() == 0) {
            String[][] subjects = {
                {"CS101", "Introduction to Programming", "Computer Science", "1", "4"},
                {"CS102", "Data Structures", "Computer Science", "2", "4"},
                {"CS201", "Database Systems", "Computer Science", "3", "3"},
                {"CS202", "Operating Systems", "Computer Science", "4", "3"},
                {"MATH101", "Calculus I", "Computer Science", "1", "3"},
                {"MATH102", "Linear Algebra", "Computer Science", "2", "3"}
            };
            
            for (String[] s : subjects) {
                Subject subject = Subject.builder()
                        .subjectCode(s[0])
                        .subjectName(s[1])
                        .department(s[2])
                        .semester(s[3])
                        .creditHours(Integer.parseInt(s[4]))
                        .build();
                subjectRepository.save(subject);
            }
            System.out.println("Sample subjects initialized");
        }
    }
    
    private void initializeSampleStudent() {
        if (!userRepository.existsByUsername("student1")) {
            User user = User.builder()
                    .username("student1")
                    .password(passwordEncoder.encode("student123"))
                    .fullName("John Doe")
                    .email("john.doe@student.edu")
                    .role(Role.STUDENT)
                    .active(true)
                    .build();
            user = userRepository.save(user);
            
            Student student = Student.builder()
                    .user(user)
                    .rollNumber("CS2024001")
                    .department("Computer Science")
                    .semester("1")
                    .admissionYear(2024)
                    .phone("1234567890")
                    .address("123 University Ave")
                    .guardianName("Jane Doe")
                    .guardianPhone("0987654321")
                    .build();
            studentRepository.save(student);
            System.out.println("Sample student created: student1 / student123");
        }
    }
    
    private void initializeSampleFaculty() {
        if (!userRepository.existsByUsername("faculty1")) {
            User user = User.builder()
                    .username("faculty1")
                    .password(passwordEncoder.encode("faculty123"))
                    .fullName("Dr. Robert Smith")
                    .email("robert.smith@faculty.edu")
                    .role(Role.FACULTY)
                    .active(true)
                    .build();
            user = userRepository.save(user);
            
            Faculty faculty = Faculty.builder()
                    .user(user)
                    .employeeId("FAC2024001")
                    .department("Computer Science")
                    .designation("Assistant Professor")
                    .specialization("Data Structures")
                    .phone("9876543210")
                    .dateOfJoining(LocalDate.of(2020, 1, 15))
                    .qualification("Ph.D. Computer Science")
                    .build();
            facultyRepository.save(faculty);
            System.out.println("Sample faculty created: faculty1 / faculty123");
        }
    }
    
    private void initializeSampleHod() {
        if (!userRepository.existsByUsername("hod1")) {
            User user = User.builder()
                    .username("hod1")
                    .password(passwordEncoder.encode("hod123"))
                    .fullName("Dr. Sarah Johnson")
                    .email("sarah.johnson@faculty.edu")
                    .role(Role.HOD)
                    .active(true)
                    .build();
            user = userRepository.save(user);
            
            Faculty faculty = Faculty.builder()
                    .user(user)
                    .employeeId("HOD2024001")
                    .department("Computer Science")
                    .designation("Head of Department")
                    .specialization("Artificial Intelligence")
                    .phone("9876543211")
                    .dateOfJoining(LocalDate.of(2015, 6, 1))
                    .qualification("Ph.D. Computer Science")
                    .build();
            facultyRepository.save(faculty);
            System.out.println("Sample HOD created: hod1 / hod123");
        }
    }
}
