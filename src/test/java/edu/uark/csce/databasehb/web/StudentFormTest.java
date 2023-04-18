package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.web.student.StudentForm;
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
        form.setStudentId(11);
        form.setStudentName("Hayden");
        form.setMajor(1);
        assertTrue(form.isValid());
    }
    @Test
    void isValidFalseNameIsEmpty() {
        form.setStudentId(1);
        form.setStudentName("");
        form.setMajor(1);
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseMajorIsNegative() {
        form.setStudentId(1);
        form.setStudentName("Bailey");
        form.setMajor(-1);
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseStudentIdIsNegative() {
        form.setStudentId(-1);
        form.setStudentName("Bailey");
        form.setMajor(1);
        assertFalse(form.isValid());
    }
    @Test
    void isValidFalseNameHasSemiColon() {
        form.setStudentId(1);
        form.setStudentName(";DROP TABLE students;");
        form.setMajor(123);
        assertFalse(form.isValid());
    }
}