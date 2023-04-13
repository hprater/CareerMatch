package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.Major;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class JobForm {
    private int jobId;
    private String companyName;
    private String jobTitle;
    private double salary;
    private List<Integer> desiredMajors;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Integer> getDesiredMajors() {
        return desiredMajors;
    }

    public void setDesiredMajors(List<Integer> desiredMajors) {
        this.desiredMajors = desiredMajors;
    }

    //create method check if inputs are valid.boolean
    public Boolean isValid() {
        if(isBlank(companyName)) return false;
        return true;
    }
}
