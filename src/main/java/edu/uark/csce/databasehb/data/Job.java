package edu.uark.csce.databasehb.data;

public class Job {
    private long id, salary;
    private String companyName;
    private String jobTitle;
    private String desiredMajor;
    public Job(long id, String companyName, String jobTitle, long salary, String desiredMajor) {
        this.id = id;
        this.salary = salary;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.desiredMajor = desiredMajor;
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
    public String getDesiredMajor(){return desiredMajor;}
}
