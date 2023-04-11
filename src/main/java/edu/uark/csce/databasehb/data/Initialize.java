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
        jdbcTemplate.execute("DROP TABLE IF EXISTS people");
        jdbcTemplate.execute("CREATE TABLE people(person_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(55), last_name VARCHAR(55), PRIMARY KEY (person_id));");
        jdbcTemplate.execute("INSERT INTO people(first_name, last_name) VALUES ('Brad','Starkenberg')");
    }
}
