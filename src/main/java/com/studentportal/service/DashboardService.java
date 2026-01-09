package com.studentportal.service;

import com.studentportal.dto.DashboardDTO;
import com.studentportal.model.Student;
import com.studentportal.model.Subject;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {
    
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AttendanceService attendanceService;
    private final MarkService markService;
    private final NoticeService noticeService;
    private final NoteService noteService;
    
    public DashboardService(StudentService studentService, SubjectService subjectService, 
                           AttendanceService attendanceService, MarkService markService,
                           NoticeService noticeService, NoteService noteService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.attendanceService = attendanceService;
        this.markService = markService;
        this.noticeService = noticeService;
        this.noteService = noteService;
    }
    
    public DashboardDTO getStudentDashboard(Student student) {
        List<Subject> subjects = subjectService.findByDepartmentAndSemester(
                student.getDepartment(), student.getSemester());
        
        return DashboardDTO.builder()
                .studentName(student.getUser().getFullName())
                .rollNumber(student.getRollNumber())
                .department(student.getDepartment())
                .semester(student.getSemester())
                .attendancePercentage(attendanceService.calculateAttendancePercentage(student))
                .totalSubjects(subjects.size())
                .averageMarks(markService.calculateAveragePercentage(student))
                .recentNotices(noticeService.toDTOList(noticeService.findRecentNotices()))
                .attendanceSummary(attendanceService.getAttendanceSummary(student, subjects))
                .build();
    }
    
    public DashboardDTO getAdminDashboard() {
        return DashboardDTO.builder()
                .totalStudents(studentService.count())
                .totalSubjects(subjectService.count())
                .totalNotices(noticeService.count())
                .totalNotes(noteService.count())
                .recentNotices(noticeService.toDTOList(noticeService.findRecentNotices()))
                .build();
    }
}
