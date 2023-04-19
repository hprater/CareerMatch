package edu.uark.csce.databasehb.service;

import edu.uark.csce.databasehb.model.application.ApplicationRepository;
import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.job.JobRepository;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.major.MajorRepository;
import edu.uark.csce.databasehb.model.student.Student;
import edu.uark.csce.databasehb.model.student.StudentRepository;
import edu.uark.csce.databasehb.web.application.AddApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepo;
    private final StudentRepository studentRepo;
    private final JobRepository jobRepo;
    private final MajorRepository majorRepository;

    public ApplicationService(ApplicationRepository applicationRepo, StudentRepository studentRepo, JobRepository jobRepo, MajorRepository majorRepository) {
        this.applicationRepo = applicationRepo;
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
        this.majorRepository = majorRepository;
    }


    public void addApplication(AddApplicationForm form) {
        applicationRepo.addApplication(form);
    }

    public List<ViewApplicationTable> getAllApplications() {
        return applicationRepo.getAllApplications();
    }

    public List<Major> getAllMajors() {
        return majorRepository.getAllMajors();
    }

    public List<Student> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    public List<Job> getAllJobs() {
        return jobRepo.getAllJobs();
    }

    public List<ViewApplicationTable> getApplicationByMajorId(int major) {
        return applicationRepo.getApplicationByMajorId(major);
    }

    public List<ViewApplicationTable> getApplicationByStudentId(long studentId) {
        return applicationRepo.getApplicationByStudentId(studentId);
    }

    public List<ViewApplicationTable> getApplicationByJobId(long jobId) {
        return applicationRepo.getApplicationByJobId(jobId);
    }
}
