package edu.uark.csce.databasehb.web.job;

import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.major.Major;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ViewJobForm {
    private Integer selectedMajor = 0;
    private List<Major> majorList = Collections.emptyList();
    private List<Job> jobList = Collections.emptyList();
}
