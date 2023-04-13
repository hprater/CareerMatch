package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.StudentForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
@Repository
public class StudentRepository {
    private final JdbcTemplate template;

    public StudentRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Student> getStudents(String major) {
        List<Student> students = new ArrayList<>(template.query("SELECT student_id, student_name, major FROM students WHERE major = ?",
                (rs, rowNum) -> new Student(rs.getLong("student_id"), rs.getString("student_name"), rs.getString("major")), major));
        return students.isEmpty() ? null : students;
    }

    public void addStudent(StudentForm form) {
        template.update("INSERT INTO students (student_id, student_name, major) VALUES (?, ?, ?)", form.getStudentId(), form.getStudentName(), form.getMajor());
    }
}
