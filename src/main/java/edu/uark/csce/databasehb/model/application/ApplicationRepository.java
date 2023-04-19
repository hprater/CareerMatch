package edu.uark.csce.databasehb.model.application;

import edu.uark.csce.databasehb.web.application.AddApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationTable;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class ApplicationRepository {
    private final JdbcTemplate template;

    public ApplicationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<ViewApplicationTable> getAllApplications() {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc"))));
        return applications.isEmpty() ? Collections.emptyList() : applications;
    }

    public List<ViewApplicationTable> getApplicationByMajorId(int majorId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND m.major_id = ?",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), majorId));
        return applications.isEmpty() ? Collections.emptyList() : applications;
    }

    public List<ViewApplicationTable> getApplicationByStudentId(long studentId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND s.student_id = ?",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), studentId));
        return applications.isEmpty() ? Collections.emptyList() : applications;
    }

    public List<ViewApplicationTable> getApplicationByJobId(long jobId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND j.job_id = ?",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), jobId));
        return applications.isEmpty() ? null : applications;
    }

    public JobApplication getApplicationByStudentIdJobId(AddApplicationForm form) {
        List<JobApplication> jobApplications = new ArrayList<>(template.query("SELECT * FROM applications WHERE student_id = ? AND job_id = ?",
                (rs, rowNum) -> new JobApplication(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), form.getStudentId(), form.getJobId()));
        return jobApplications.isEmpty() ? null : jobApplications.get(0);
    }

    public boolean addApplication(AddApplicationForm form) {
        try {
            if (getApplicationByStudentIdJobId(form) != null) {
                log.info("Application is null");
                throw new DataIntegrityViolationException("Duplicate application");
            }

            template.update("INSERT INTO applications (student_id, job_id) VALUES (?, ?)", form.getStudentId(), form.getJobId());
            return false;
        } catch (DataIntegrityViolationException dup) {
            return true;
        }
    }
}
