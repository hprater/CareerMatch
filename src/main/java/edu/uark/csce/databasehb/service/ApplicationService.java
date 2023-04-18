package edu.uark.csce.databasehb.service;

import edu.uark.csce.databasehb.data.ApplicationRepository;
import edu.uark.csce.databasehb.data.Job;
import edu.uark.csce.databasehb.data.JobRepository;
import edu.uark.csce.databasehb.data.Major;
import edu.uark.csce.databasehb.data.MajorRepository;
import edu.uark.csce.databasehb.data.Student;
import edu.uark.csce.databasehb.data.StudentRepository;
import edu.uark.csce.databasehb.web.application.ApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationForm;
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


    public boolean addApplication(ApplicationForm form) {
        return applicationRepo.addApplication(form);
    }

    public List<ViewApplicationForm> getAllApplications() {
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

    public List<ViewApplicationForm> getApplicationByMajorId(int major) {
        return applicationRepo.getApplicationByMajorId(major);
    }

    public List<ViewApplicationForm> getApplicationByStudentId(long studentId) {
        return applicationRepo.getApplicationByStudentId(studentId);
    }

    public List<ViewApplicationForm> getApplicationByJobId(long jobId) {
        return applicationRepo.getApplicationByJobId(jobId);
    }
}
