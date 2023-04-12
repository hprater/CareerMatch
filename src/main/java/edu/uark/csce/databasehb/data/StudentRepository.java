package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.StudentForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    private final JdbcTemplate template;

    public StudentRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Student getStudents(String major) {
        List<Student> students = new ArrayList<>();
        template.query("SELECT student_id, student_name, major FROM students WHERE major = ?", new Object[]{major},
                (rs, rowNum) -> new Student(rs.getLong("student_id"), rs.getString("student_name"), rs.getString("major"))
        ).forEach(students::add);
        return students.isEmpty() ? null : students.get(0);
    }

    public void addStudent(StudentForm form){
        // Do a query to insert the student based on the form data
    }
}
