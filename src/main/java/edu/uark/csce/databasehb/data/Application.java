package edu.uark.csce.databasehb.data;

public class Application {
    private int applicationId;
    private long studentId;
    private int jobId;

    public Application(int applicationId, long studentId, int jobId) {
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
