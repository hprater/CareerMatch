package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.Major;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class JobForm {
    private Integer jobId;
    private String companyName;
    private String jobTitle;
    private double salary;

    //    private List<Integer> majors;
    private Integer major;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
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

    //    public List<Integer> getMajors() {
//        return majors;
//    }
//    public void setMajors(List<Integer> majors) {
//        this.majors = majors;
//    }
    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }


    //create method check if inputs are valid.boolean
    public Boolean isValid() {
        if (isBlank(companyName)) return false;
        return true;
    }
}
