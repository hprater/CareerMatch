package edu.uark.csce.databasehb.web;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class StudentForm {
    private String studentId;
    private String studentName;
    private String major;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    //create method check if inputs are valid.boolean
    public boolean isValid() {
        // Ensure studentId was entered as a number
        if(!isNumeric(this.studentId)) return false;

        // Check for studentName validity
        if(isBlank(this.studentName) || this.studentName.contains(";")) return false;

        // Check for major validity
        if(isBlank(this.major) || this.major.contains(";")) return false;

        return true;
    }

}
