package edu.uark.csce.databasehb.model.job;

import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.web.job.JobForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public Job addJob(JobForm form) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO jobs (company_name, job_title, salary) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, form.getCompanyName());
            ps.setString(2, form.getJobTitle());
            ps.setDouble(3, form.getSalary());
            return ps;
        }, keyHolder);
        BigInteger jobId = (BigInteger) keyHolder.getKey();
        return new Job(jobId.longValue(), form.getCompanyName(), form.getJobTitle(), form.getSalary());
    }

    public Long getJobID(JobForm form) {
        return template.queryForObject("SELECT job_id FROM jobs WHERE job_title = ? AND company_name = ? AND salary = ?", Long.class, form.getJobTitle(), form.getCompanyName(), form.getSalary());
    }

    public void addJobMajor(Job job, Major major) {
        template.update("INSERT INTO job_majors (job_id, major_id) VALUES (?, ?)", job.getId(), major.getMajorId());
    }
}

