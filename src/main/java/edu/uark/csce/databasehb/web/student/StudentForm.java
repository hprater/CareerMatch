package edu.uark.csce.databasehb.web.student;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isAlphaSpace;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class StudentForm {
    private Integer studentId;
    private String studentName;
    private Integer major;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    //create method check if inputs are valid.boolean
    public boolean isValid() {
        // Ensure studentId was entered as a number
        if(this.studentId < 1 || String.valueOf(this.studentId).length() > 9) return false;

        // Check for studentName validity
        if(isBlank(this.studentName) || !isAlphaSpace(this.studentName) || this.studentName.length() > 55) return false;

        // Check for major validity
        if(this.major < 1) return false;

        return true;
    }

}
