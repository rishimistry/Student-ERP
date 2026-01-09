package com.studentportal.dto;

import java.util.List;

public class DashboardDTO {
    private String studentName;
    private String rollNumber;
    private String department;
    private String semester;
    
    private double attendancePercentage;
    private long totalSubjects;
    private double averageMarks;
    
    private long totalStudents;
    private long totalNotices;
    private long totalNotes;
    
    private List<NoticeDTO> recentNotices;
    private List<AttendanceSummaryDTO> attendanceSummary;
    
    public DashboardDTO() {}
    
    // Getters and Setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(double attendancePercentage) { this.attendancePercentage = attendancePercentage; }
    
    public long getTotalSubjects() { return totalSubjects; }
    public void setTotalSubjects(long totalSubjects) { this.totalSubjects = totalSubjects; }
    
    public double getAverageMarks() { return averageMarks; }
    public void setAverageMarks(double averageMarks) { this.averageMarks = averageMarks; }
    
    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }
    
    public long getTotalNotices() { return totalNotices; }
    public void setTotalNotices(long totalNotices) { this.totalNotices = totalNotices; }
    
    public long getTotalNotes() { return totalNotes; }
    public void setTotalNotes(long totalNotes) { this.totalNotes = totalNotes; }
    
    public List<NoticeDTO> getRecentNotices() { return recentNotices; }
    public void setRecentNotices(List<NoticeDTO> recentNotices) { this.recentNotices = recentNotices; }
    
    public List<AttendanceSummaryDTO> getAttendanceSummary() { return attendanceSummary; }
    public void setAttendanceSummary(List<AttendanceSummaryDTO> attendanceSummary) { this.attendanceSummary = attendanceSummary; }
    
    // Builder
    public static DashboardDTOBuilder builder() { return new DashboardDTOBuilder(); }
    
    public static class DashboardDTOBuilder {
        private DashboardDTO dto = new DashboardDTO();
        
        public DashboardDTOBuilder studentName(String studentName) { dto.studentName = studentName; return this; }
        public DashboardDTOBuilder rollNumber(String rollNumber) { dto.rollNumber = rollNumber; return this; }
        public DashboardDTOBuilder department(String department) { dto.department = department; return this; }
        public DashboardDTOBuilder semester(String semester) { dto.semester = semester; return this; }
        public DashboardDTOBuilder attendancePercentage(double attendancePercentage) { dto.attendancePercentage = attendancePercentage; return this; }
        public DashboardDTOBuilder totalSubjects(long totalSubjects) { dto.totalSubjects = totalSubjects; return this; }
        public DashboardDTOBuilder averageMarks(double averageMarks) { dto.averageMarks = averageMarks; return this; }
        public DashboardDTOBuilder totalStudents(long totalStudents) { dto.totalStudents = totalStudents; return this; }
        public DashboardDTOBuilder totalNotices(long totalNotices) { dto.totalNotices = totalNotices; return this; }
        public DashboardDTOBuilder totalNotes(long totalNotes) { dto.totalNotes = totalNotes; return this; }
        public DashboardDTOBuilder recentNotices(List<NoticeDTO> recentNotices) { dto.recentNotices = recentNotices; return this; }
        public DashboardDTOBuilder attendanceSummary(List<AttendanceSummaryDTO> attendanceSummary) { dto.attendanceSummary = attendanceSummary; return this; }
        
        public DashboardDTO build() { return dto; }
    }
    
    // Inner class for attendance summary
    public static class AttendanceSummaryDTO {
        private String subjectName;
        private long present;
        private long total;
        private double percentage;
        
        public AttendanceSummaryDTO() {}
        
        public String getSubjectName() { return subjectName; }
        public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
        
        public long getPresent() { return present; }
        public void setPresent(long present) { this.present = present; }
        
        public long getTotal() { return total; }
        public void setTotal(long total) { this.total = total; }
        
        public double getPercentage() { return percentage; }
        public void setPercentage(double percentage) { this.percentage = percentage; }
        
        public static AttendanceSummaryDTOBuilder builder() { return new AttendanceSummaryDTOBuilder(); }
        
        public static class AttendanceSummaryDTOBuilder {
            private AttendanceSummaryDTO dto = new AttendanceSummaryDTO();
            
            public AttendanceSummaryDTOBuilder subjectName(String subjectName) { dto.subjectName = subjectName; return this; }
            public AttendanceSummaryDTOBuilder present(long present) { dto.present = present; return this; }
            public AttendanceSummaryDTOBuilder total(long total) { dto.total = total; return this; }
            public AttendanceSummaryDTOBuilder percentage(double percentage) { dto.percentage = percentage; return this; }
            
            public AttendanceSummaryDTO build() { return dto; }
        }
    }
}
