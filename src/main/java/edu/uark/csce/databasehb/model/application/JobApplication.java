package edu.uark.csce.databasehb.model.application;

public class JobApplication {
    private int applicationId;
    private long studentId;
    private int jobId;

    public JobApplication(int applicationId, long studentId, int jobId) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", studentId=" + studentId +
                ", jobId=" + jobId +
                '}';
    }
}