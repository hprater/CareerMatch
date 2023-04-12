package edu.uark.csce.databasehb.data;
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

    public Job getJob(String jobTitle) {
        List<Job> jobs = new ArrayList<>();
        template.query("SELECT job_id, company_name, job_title, salary FROM jobs WHERE job_title = ?", new Object[]{jobTitle},
                (rs, rowNum) -> new Job(rs.getLong("job_id"), rs.getString("company_name"), rs.getString("job_title"), rs.getLong("salary"))
        ).forEach(jobs::add);
        return jobs.isEmpty() ? null : jobs.get(0);
    }
}
