package edu.uark.csce.databasehb.data;

public class Job {
    private long id, salary;
    private String companyName;
    private String jobTitle;
    public Job(long id, String companyName, String jobTitle, long salary) {
        this.id = id;
        this.salary = salary;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
    }
    public long getId() {
        return id;
    }
    public long getSalary() {
        return salary;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getJobTitle() {
        return jobTitle;
    }
}
