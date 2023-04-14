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

    public Application getApplicationByStudentIdJobId(ApplicationForm form) {
        List<Application> applications = new ArrayList<>(template.query("SELECT application_id FROM applications WHERE student_id = ? AND job_id = ?",
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
