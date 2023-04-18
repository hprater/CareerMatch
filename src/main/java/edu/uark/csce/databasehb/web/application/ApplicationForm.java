package edu.uark.csce.databasehb.web.application;

public class ApplicationForm {
    private long studentId;
    private int jobId;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public boolean isValid(){
        return true;
    }
}
