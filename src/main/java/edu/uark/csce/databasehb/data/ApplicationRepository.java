package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.ApplicationForm;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.xml.crypto.Data;
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

    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc\n" +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm\n" +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id",
                (rs, rowNum) -> new Application(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id"))));
        return applications.isEmpty() ? null : applications;
    }

    public List<Application> getApplicationByMajorId(int majorId) {
        List<Application> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc\n" +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm\n" +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND m.major_id = ?",
                (rs, rowNum) -> new Application(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), majorId));
        return applications.isEmpty() ? null : applications;
    }

    public List<Application> getApplicationByStudentId(long studentId) {
        List<Application> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc\n" +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm\n" +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND s.student_id = ?",
                (rs, rowNum) -> new Application(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), studentId));
        return applications.isEmpty() ? null : applications;
    }

    public List<Application> getApplicationByJobId(int jobId) {
        List<Application> applications = new ArrayList<>(template.query("SELECT s.student_name, j.company_name, j.salary, m.major_desc\n" +
                        "FROM applications a, students s, jobs j, majors m, job_majors jm\n" +
                        "WHERE a.student_id = s.student_id AND s.major_id = m.major_id AND a.job_id = j.job_id AND j.job_id = jm.job_id AND jm.major_id = m.major_id AND j.job_id = ?",
                (rs, rowNum) -> new Application(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), jobId));
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
