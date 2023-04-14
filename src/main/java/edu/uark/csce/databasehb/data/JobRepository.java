package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.JobForm;
import edu.uark.csce.databasehb.web.StudentForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JobRepository {
    private final JdbcTemplate template;

    public JobRepository(JdbcTemplate template) {
        this.template = template;
    }


    public List<Job> getJobByMajor(int major) {
        List<Job> jobs = new ArrayList<>(template.query(
                "SELECT j.job_id, company_name, job_title, salary, major_id FROM jobs j, job_majors m WHERE j.job_id = m.job_id AND m.major_id = ?;",
                (rs, rowNum) -> new Job(rs.getLong("job_id"), rs.getString("company_name"), rs.getString("job_title"), rs.getLong("salary")), major));
        return jobs.isEmpty() ? null : jobs;
    }

    public void addJob(JobForm form) {
        try {
            template.update("INSERT INTO jobs (company_name, job_title, salary) VALUES (?, ?, ?)", form.getCompanyName(), form.getJobTitle(), form.getSalary());
        } catch (DataIntegrityViolationException dup) {
            //Not sure if needed
            //template.update("UPDATE jobs SET company_name = ?, job_title = ? WHERE student_id = ?", form.getCompanyName(), form.getJobTitle(), form.getSalary());
        }
    }

    public Job getJobID(JobForm form) {
        List<Job> jobs = new ArrayList<>();
        template.query("SELECT job_id, company_name, job_title, salary FROM jobs WHERE job_title = ? AND company_name = ? AND salary = ?",
                (rs, rowNum) -> new Job(rs.getLong("job_id"), rs.getString("company_name"), rs.getString("job_title"), rs.getLong("salary")),
                form.getJobTitle(), form.getCompanyName(), form.getSalary()
        ).forEach(jobs::add);
        return jobs.isEmpty() ? null : jobs.get(0);
    }

    public Boolean addJobToJobMajor(JobForm form) {
        Job findingJob = getJobID(form);
        if (findingJob != null) {
            try {
                template.update("INSERT INTO job_majors (job_id, major_id) VALUES (?, ?)", findingJob.getId(), form.getMajor());
                return false;
            } catch (DataIntegrityViolationException dup) {
                //template.update("UPDATE job_majors SET major_id = ? WHERE job_id = ?", form.getJobId(), form.getMajors());
                return true;
            }
        }else {
            return null;
        }
    }
}
