package edu.uark.csce.databasehb.model.application;

import edu.uark.csce.databasehb.web.application.AddApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ApplicationRepository {
    private final JdbcTemplate template;

    public ApplicationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<ViewApplicationTable> getAllApplications() {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query(
                "SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a " +
                        "INNER JOIN students s ON a.student_id = s.student_id " +
                        "INNER JOIN jobs j ON a.job_id = j.job_id " +
                        "INNER JOIN job_majors jm on j.job_id = jm.job_id " +
                        "INNER JOIN majors m ON m.major_id = jm.major_id " +
                        "ORDER BY s.student_name, j.salary DESC",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc"))));
        return applications;
    }

    public List<ViewApplicationTable> getApplicationByMajorId(int majorId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query(
                "SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a " +
                        "INNER JOIN students s ON a.student_id = s.student_id " +
                        "INNER JOIN jobs j ON a.job_id = j.job_id " +
                        "INNER JOIN job_majors jm on j.job_id = jm.job_id " +
                        "INNER JOIN majors m ON m.major_id = jm.major_id " +
                        "WHERE jm.major_id = ? " +
                        "ORDER BY s.student_name, j.salary DESC",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), majorId));
        return applications;
    }

    public List<ViewApplicationTable> getApplicationByStudentId(long studentId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query(
                "SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a " +
                        "INNER JOIN students s ON a.student_id = s.student_id " +
                        "INNER JOIN jobs j ON a.job_id = j.job_id " +
                        "INNER JOIN job_majors jm on j.job_id = jm.job_id " +
                        "INNER JOIN majors m ON m.major_id = jm.major_id " +
                        "AND s.student_id = ? " +
                        "ORDER BY s.student_name, j.salary DESC",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), studentId));
        return applications;
    }

    public List<ViewApplicationTable> getApplicationByJobId(long jobId) {
        List<ViewApplicationTable> applications = new ArrayList<>(template.query(
                "SELECT s.student_name, j.company_name, j.salary, m.major_desc " +
                        "FROM applications a " +
                        "INNER JOIN students s ON a.student_id = s.student_id " +
                        "INNER JOIN jobs j ON a.job_id = j.job_id " +
                        "INNER JOIN job_majors jm on j.job_id = jm.job_id " +
                        "INNER JOIN majors m ON m.major_id = jm.major_id " +
                        "AND j.job_id = ? " +
                        "ORDER BY s.student_name, j.salary DESC",
                (rs, rowNum) -> new ViewApplicationTable(rs.getString("student_name"), rs.getString("company_name"),
                        rs.getLong("salary"), rs.getString("major_desc")), jobId));
        return applications;
    }

    public JobApplication getApplicationByStudentIdJobId(AddApplicationForm form) {
        List<JobApplication> jobApplications = new ArrayList<>(template.query("SELECT * FROM applications WHERE student_id = ? AND job_id = ?",
                (rs, rowNum) -> new JobApplication(rs.getInt("application_id"), rs.getLong("student_id"),
                        rs.getInt("job_id")), form.getSelectedStudent(), form.getSelectedJob()));
        return jobApplications.isEmpty() ? null : jobApplications.get(0);
    }

    public void addApplication(AddApplicationForm form) {
            template.update("INSERT INTO applications (student_id, job_id) VALUES (?, ?)", form.getSelectedStudent(), form.getSelectedJob());
    }
}
