package edu.uark.csce.databasehb.web;

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
        // Check for studentId validity
        int checkStudentId = this.studentId.indexOf(";"); // If -1, then no semicolons exist

        // Ensure studentId was entered as a number
        try {
            Integer.parseInt(this.studentId);
        } catch (NumberFormatException e) {
            return false;
        }

        // Check for studentName validity
        int checkStudentName = this.studentName.indexOf(";");

        // Check for major validity
        int checkMajor = this.major.indexOf(";");

        if(checkStudentId == -1 && checkStudentName == -1 && checkMajor == -1)
            return true;

        return false;
    }

}
