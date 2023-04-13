package edu.uark.csce.databasehb.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MajorRepository {
    private final JdbcTemplate template;

    public MajorRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Major> getAllMajors() {
        List<Major> majors = new ArrayList<>(template.query("SELECT major_id, major, major_desc FROM majors",
                (rs, rowNum) -> new Major(rs.getInt("major_id"), rs.getString("major"), rs.getString("major_desc"))));
        return majors;
    }
}
