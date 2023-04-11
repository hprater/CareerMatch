package net.starkenberg.uofa.demo.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    private final JdbcTemplate template;

    public PersonRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Person getFirstPerson() {
        List<Person> people = new ArrayList<>();
        template.query("SELECT person_id, first_name, last_name FROM people WHERE first_name = ?", new Object[]{"Brad"},
                (rs, rowNum) -> new Person(rs.getLong("person_id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(people::add);
        return people.isEmpty() ? null : people.get(0);
    }
}
