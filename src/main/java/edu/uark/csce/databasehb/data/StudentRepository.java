package edu.uark.csce.databasehb.data;

import edu.uark.csce.databasehb.web.StudentForm;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>(template.query("SELECT * FROM students",
                (rs, rowNum) -> new Student(rs.getLong("student_id"), rs.getString("student_name"), rs.getString("major_id"))));
        return students.isEmpty() ? null : students;
    }

    public List<Student> getStudentsByMajor(int major) {
        List<Student> students = new ArrayList<>(template.query(
                        "SELECT student_id, student_name, major FROM students s, majors m WHERE s.major_id = m.major_id AND m.major_id = ?;",
                (rs, rowNum) -> new Student(rs.getLong("student_id"), rs.getString("student_name"), rs.getString("major")), major));
        return students.isEmpty() ? null : students;
    }

    public boolean addStudent(StudentForm form) {
        try {
            template.update("INSERT INTO students (student_id, student_name, major_id) VALUES (?, ?, ?)", form.getStudentId(), form.getStudentName(), form.getMajor());
            return false;
        } catch (DataIntegrityViolationException dup) {
            template.update("UPDATE students SET student_name = ?, major_id = ? WHERE student_id = ?", form.getStudentName(), form.getMajor(), form.getStudentId());
            return true;
        }
    }
}
