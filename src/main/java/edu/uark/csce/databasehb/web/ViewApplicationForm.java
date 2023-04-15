package edu.uark.csce.databasehb.web;

public class ViewApplicationForm {
    private String studentName;
    private String companyName;
    private long salary;
    private String majorDescription;

    public ViewApplicationForm(String studentName, String companyName, long salary, String majorDescription) {
        this.studentName = studentName;
        this.companyName = companyName;
        this.salary = salary;
        this.majorDescription = majorDescription;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getMajorDescription() {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        this.majorDescription = majorDescription;
    }

    @Override
    public String toString() {
        return "ViewApplicationForm{" +
                "studentName='" + studentName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", salary=" + salary +
                ", majorDescription='" + majorDescription + '\'' +
                '}';
    }
}
