package edu.uark.csce.databasehb.web;

public class JobForm {
    private String jobId;
    private String companyName;
    private String jobTitle;
    private String salary;
    private String desiredMajor;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDesiredMajor() {
        return desiredMajor;
    }

    public void setDesiredMajor(String desiredMajor) {
        this.desiredMajor = desiredMajor;
    }

    //create method check if inputs are valid.boolean
}
