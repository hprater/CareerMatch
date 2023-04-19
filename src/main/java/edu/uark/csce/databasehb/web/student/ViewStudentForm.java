package edu.uark.csce.databasehb.web.student;

import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.student.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViewStudentForm {
    private Integer selectedMajor = 0;
    private List<Major> majorList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();
}
