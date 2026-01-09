package com.studentportal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "subject_id", "date"})
})
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;
    
    @Column(length = 255)
    private String remarks;
    
    public Attendance() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    // Builder
    public static AttendanceBuilder builder() { return new AttendanceBuilder(); }
    
    public static class AttendanceBuilder {
        private Attendance attendance = new Attendance();
        
        public AttendanceBuilder id(Long id) { attendance.id = id; return this; }
        public AttendanceBuilder student(Student student) { attendance.student = student; return this; }
        public AttendanceBuilder subject(Subject subject) { attendance.subject = subject; return this; }
        public AttendanceBuilder date(LocalDate date) { attendance.date = date; return this; }
        public AttendanceBuilder status(AttendanceStatus status) { attendance.status = status; return this; }
        public AttendanceBuilder remarks(String remarks) { attendance.remarks = remarks; return this; }
        
        public Attendance build() { return attendance; }
    }
}
