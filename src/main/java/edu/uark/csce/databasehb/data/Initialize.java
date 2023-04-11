package edu.uark.csce.databasehb.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Initialize {

    private final JdbcTemplate jdbcTemplate;

    public Initialize(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS applications");
        jdbcTemplate.execute("DROP TABLE IF EXISTS job_majors");
        jdbcTemplate.execute("DROP TABLE IF EXISTS jobs");
        jdbcTemplate.execute("DROP TABLE IF EXISTS students");
        jdbcTemplate.execute("DROP TABLE IF EXISTS majors");


        jdbcTemplate.execute("CREATE TABLE majors(major_id INT NOT NULL AUTO_INCREMENT, major VARCHAR(6), major_desc VARCHAR (155), PRIMARY KEY (major_id));");
        jdbcTemplate.execute("INSERT INTO majors(major, major_desc) VALUES ('BSCE','Bachelor of Science in Computer Engineering'), ('BSCS', 'Bachelor of Science in Computer Science');");

        jdbcTemplate.execute("CREATE TABLE students(student_id INT NOT NULL, student_name VARCHAR(55), major VARCHAR(6), PRIMARY KEY (student_id));");
        jdbcTemplate.execute("INSERT INTO students(student_id, student_name, major) VALUES (010931631, 'Hayden Prater','BSCE'), (010852905, 'Bailey Grimes', 'BSCE');");

        jdbcTemplate.execute("CREATE TABLE jobs(job_id INT NOT NULL AUTO_INCREMENT, company_name VARCHAR(55), job_title VARCHAR(55), salary INT DEFAULT 0, PRIMARY KEY (job_id));");
        jdbcTemplate.execute("INSERT INTO jobs(company_name, job_title, salary) VALUES ('Walmart','Software Engineer I', 70000), ('JB Hunt', 'Software Engineer I', 60000);");

        jdbcTemplate.execute("CREATE TABLE job_majors(job_id INT NOT NULL, major_id INT NOT NULL, PRIMARY KEY (job_id, major_id), FOREIGN KEY (job_id) REFERENCES jobs(job_id), FOREIGN KEY (major_id) REFERENCES majors(major_id));");
        jdbcTemplate.execute("INSERT INTO job_majors(job_id, major_id) VALUES (1, 1), (1,2), (2,1), (2,2);");

        jdbcTemplate.execute("CREATE TABLE applications(application_id INT NOT NULL AUTO_INCREMENT, student_id INT NOT NULL, job_id INT NOT NULL, PRIMARY KEY (application_id), FOREIGN KEY (student_id) REFERENCES students(student_id), FOREIGN KEY (job_id) REFERENCES jobs(job_id));");
        jdbcTemplate.execute("INSERT INTO applications(student_id, job_id) VALUES (010931631, 1), (010852905, 2);");




    }
}
