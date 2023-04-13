package edu.uark.csce.databasehb.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class StudentFormTest {
    StudentForm form;

    @BeforeAll
    void setup() {
        form = new StudentForm();
    }

    @Test
    void isValidTrue() {
        form.setStudentId("1");
        form.setStudentName("Hayden");
        form.setMajor("none");
        assertTrue(form.isValid());
    }
    @Test
    void isValidFalseNameIsEmpty() {
        form.setStudentId("1");
        form.setStudentName("");
        form.setMajor("none");
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseMajorIsEmpty() {
        form.setStudentId("1");
        form.setStudentName("Bailey");
        form.setMajor("");
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseNameHasSemiColon() {
        form.setStudentId("1");
        form.setStudentName(";DROP TABLE students;");
        form.setMajor("123");
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseIdHasSemiColon() {
        form.setStudentId("1;");
        form.setStudentName("Barney");
        form.setMajor("123");
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseNonNumericStudentId() {
        form.setStudentId("abc");
        form.setStudentName("Fred");
        form.setMajor("PHYL");
        assertFalse(form.isValid());
    }
}