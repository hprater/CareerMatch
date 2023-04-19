package edu.uark.csce.databasehb.service;

import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.major.MajorRepository;
import edu.uark.csce.databasehb.model.student.Student;
import edu.uark.csce.databasehb.model.student.StudentRepository;
import edu.uark.csce.databasehb.web.student.StudentForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final MajorRepository majorRepo;

    public StudentService(StudentRepository repo, MajorRepository majorRepo) {
        this.repo = repo;
        this.majorRepo = majorRepo;
    }

    public List<Major> getAllMajors() {
        return majorRepo.getAllMajors();
    }

    public List<Student> getStudentsByMajor(Integer major) {
        return repo.getStudentsByMajor(major);
    }

    public boolean addStudent(StudentForm form) {
        return repo.addStudent(form);
    }
}
