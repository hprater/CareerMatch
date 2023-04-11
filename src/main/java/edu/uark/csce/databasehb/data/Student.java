package edu.uark.csce.databasehb.data;

public class Student {
    private long id;
    private String studentName;
    private String major;
    public Student(long id, String studentName, String major) {
        this.id = id;
        this.studentName = studentName;
        this.major = major;
    }
    public long getId() {
        return id;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getmajor() {
        return major;
    }
}
