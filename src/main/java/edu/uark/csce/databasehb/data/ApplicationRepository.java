package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.application.ApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationForm;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
@Slf4j
@Repository
public class ApplicationRepository {
    private final JdbcTemplate template;

    public ApplicationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<ViewApplicationForm> getAllApplications() {
        List<ViewApplicationForm> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id",
                (rs, rowNum) -> new ViewApplicationForm(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc"))));
        return applications.isEmpty() ? null : applications;
    }

    public List<ViewApplicationForm> getApplicationByMajorId(int majorId) {
        List<ViewApplicationForm> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND m.major_id = ?",
                (rs, rowNum) -> new ViewApplicationForm(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), majorId));
        return applications.isEmpty() ? null : applications;
    }

    public List<ViewApplicationForm> getApplicationByStudentId(long studentId) {
        List<ViewApplicationForm> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND s.student_id = ?",
                (rs, rowNum) -> new ViewApplicationForm(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), studentId));
        return applications.isEmpty() ? null : applications;
    }

    public List<ViewApplicationForm> getApplicationByJobId(long jobId) {
        List<ViewApplicationForm> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm " +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND j.job_id = ?",
                (rs, rowNum) -> new ViewApplicationForm(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), jobId));
        return applications.isEmpty() ? null : applications;
    }

    public Application getApplicationByStudentIdJobId(ApplicationForm form) {
        List<Application> applications = new ArrayList<>(template.query("SELECT * FROM applications WHERE student_id = ? AND job_id = ?",
                (rs, rowNum) -> new Application(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), form.getStudentId(), form.getJobId()));
        return applications.isEmpty() ? null : applications.get(0);
    }

    public boolean addApplication(ApplicationForm form) {
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
