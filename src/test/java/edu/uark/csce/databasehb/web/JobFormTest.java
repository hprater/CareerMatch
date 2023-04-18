package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.web.job.JobForm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class JobFormTest {
    JobForm form;
    @BeforeAll
    void setup() {
        form = new JobForm();
    }

    @Test
    void isValidTrue() {
        form.setJobId(1);
        form.setCompanyName("Flintstone");
        form.setJobTitle("Engineer");
        form.setSalary(10);
        assertTrue(form.isValid());
    }

    @Test
    void isValidFalseEmptyCompanyName() {
        form.setJobId(1);
        form.setCompanyName("");
        form.setJobTitle("Engineer");
        form.setSalary(10);
        assertFalse(form.isValid());
    }
}