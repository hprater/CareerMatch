package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.Major;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;

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
        form.setDesiredMajors(Collections.singletonList(1));
        assertTrue(form.isValid());
    }

    @Test
    void isValidFalseEmptyCompanyName() {
        form.setJobId(1);
        form.setCompanyName("");
        form.setJobTitle("Engineer");
        form.setSalary(10);
        form.setDesiredMajors(Collections.singletonList(1));
        assertFalse(form.isValid());
    }
}