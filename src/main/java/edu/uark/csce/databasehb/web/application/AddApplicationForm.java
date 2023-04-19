package edu.uark.csce.databasehb.web.application;

import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.student.Student;
import lombok.Data;

import java.util.List;

@Data
public class AddApplicationForm {
    private Long selectedStudent = 0L;
    private Integer selectedJob = 0;
    private List<Job> jobList;
    private List<Student> studentList;
}
