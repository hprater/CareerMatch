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

        jdbcTemplate.execute("CREATE TABLE majors(major_id INT NOT NULL AUTO_INCREMENT, major VARCHAR(8), major_desc VARCHAR (155), PRIMARY KEY (major_id));");
        jdbcTemplate.execute("INSERT INTO majors(major, major_desc) VALUES ('General','Undecided'), ('BSCS','Bachelor of Science in Computer Science'),('BSCmpE','Bachelor of Science in Computer Engineering'), ('BSChE', 'Bachelor of Science in Chemical Engineering')," +
                                "('BSCE', 'Bachelor of Science in Civil Engineering'), ('BSEE', 'Bachelor of Science in Electrical Engineering'), ('BSME', 'Bachelor of Science in Mechanical Engineering'), ('BSIE', 'Bachelor of Science in Industrial Engineering')," +
                                "('BSBmE', 'Bachelor of Science in Biomedical Engineering'), ('BSBE', 'Bachelor of Science in Biological and Agricultural Engineering'), ('BSDTSC', 'Bachelor of Science in Data Science');");

        jdbcTemplate.execute("CREATE TABLE students(student_id INT NOT NULL, student_name VARCHAR(55), major_id INT, PRIMARY KEY (student_id), FOREIGN KEY (major_id) REFERENCES majors(major_id));");
        jdbcTemplate.execute("INSERT INTO students(student_id, student_name, major_id) VALUES (010931631, 'Hayden Prater', 3), (010852905, 'Bailey Grimes', 3), (043578654, 'Timmy Jones', 10), (098345876, 'Amy Stark', 9), (032894765, 'Megan Turner', 7), (034578362, 'Kim Lou', 2);");

        jdbcTemplate.execute("CREATE TABLE jobs(job_id INT NOT NULL AUTO_INCREMENT, company_name VARCHAR(55), job_title VARCHAR(55), salary INTEGER DEFAULT 0, PRIMARY KEY (job_id));");
        jdbcTemplate.execute("CREATE UNIQUE INDEX job_info ON jobs (company_name, job_title, salary);");
        jdbcTemplate.execute("INSERT INTO jobs(company_name, job_title, salary) VALUES ('Walmart','Software Engineer I', 70000), ('JB Hunt', 'Software Engineer I', 60000), ('Tyson', 'Automation and Robotics Engineer', 93200), ('U.S. Department of Agriculture', 'Natural Resource Specialist', 69100), ('Toyota', 'Engine Calibration OBD Engineer', 95000), ('Mercy', 'Biomedical Engineer', 57000);");

        jdbcTemplate.execute("CREATE TABLE job_majors(job_id INT NOT NULL, major_id INT NOT NULL, PRIMARY KEY (job_id, major_id), FOREIGN KEY (job_id) REFERENCES jobs(job_id), FOREIGN KEY (major_id) REFERENCES majors(major_id));");
        jdbcTemplate.execute("INSERT INTO job_majors(job_id, major_id) VALUES (1, 2), (2,2), (3,3), (4,10), (5,7), (6,9);");

        jdbcTemplate.execute("CREATE TABLE applications(student_id INT NOT NULL, job_id INT NOT NULL, PRIMARY KEY (student_id,job_id), FOREIGN KEY (student_id) REFERENCES students(student_id), FOREIGN KEY (job_id) REFERENCES jobs(job_id));");
        jdbcTemplate.execute("INSERT INTO applications(student_id, job_id) VALUES (010931631, 1), (010852905, 2), (010931631, 3), (043578654, 4), (032894765, 5), (098345876, 6), (034578362, 2) ;");

    }
}
