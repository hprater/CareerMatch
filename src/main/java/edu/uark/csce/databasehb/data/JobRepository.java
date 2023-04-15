package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.JobForm;
import edu.uark.csce.databasehb.web.StudentForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JobRepository {
    private final JdbcTemplate template;

    public JobRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>(template.query("SELECT * FROM jobs",
                (rs, rowNum) -> new Job(rs.getLong("job_id"), rs.getString("company_name"), rs.getString("job_title"), rs.getLong("salary"))));
        return jobs.isEmpty() ? null : jobs;
    }

    public List<Job> getJobByMajor(int major) {
        List<Job> jobs = new ArrayList<>(template.query(
                "SELECT j.job_id, company_name, job_title, salary, major_id FROM jobs j, job_majors m WHERE j.job_id = m.job_id AND m.major_id = ?;",
                (rs, rowNum) -> new Job(rs.getLong("job_id"), rs.getString("company_name"), rs.getString("job_title"), rs.getLong("salary")), major));
        return jobs.isEmpty() ? null : jobs;
    }

    public boolean addJob(JobForm form) {
        Job findingJob = getJobID(form);
        try {
            if (findingJob != null)
                throw new DataIntegrityViolationException("Duplicate job");
            template.update("INSERT INTO jobs (company_name, job_title, salary) VALUES (?, ?, ?)", form.getCompanyName(), form.getJobTitle(), form.getSalary());
            return false;
        } catch (DataIntegrityViolationException dup) {
            return true;
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

    public boolean addJobToJobMajor(JobForm form) {
        Job findingJob = getJobID(form);
        try {
            if (findingJob != null)
                template.update("INSERT INTO job_majors (job_id, major_id) VALUES (?, ?)", findingJob.getId(), form.getMajor());
            return false;
        } catch (DataIntegrityViolationException dup) {
            return true;
        }
    }
}

