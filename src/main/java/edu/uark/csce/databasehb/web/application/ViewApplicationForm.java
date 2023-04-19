package edu.uark.csce.databasehb.web.application;

import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.student.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewApplicationForm {
    private Integer searchMethod = 0;
    private Integer selectedMajor = 0;
    private Long selectedStudent = 0L;
    private Long selectedJob = 0L;
    private List<Major> majorList;
    private List<Student> studentList;
    private List<Job> jobList;
    private List<ViewApplicationTable> applicationTableList = new ArrayList<>();
}
