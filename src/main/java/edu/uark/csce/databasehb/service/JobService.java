package edu.uark.csce.databasehb.service;

import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.job.JobRepository;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.major.MajorRepository;
import edu.uark.csce.databasehb.web.job.JobForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobRepository repo;
    private final MajorRepository majorRepo;

    public JobService(JobRepository repo, MajorRepository majorRepo) {
        this.repo = repo;
        this.majorRepo = majorRepo;
    }


    public List<Major> getAllMajors() {
        return majorRepo.getAllMajors();
    }

    public List<Job> getJobByMajor(Integer major) {
        return repo.getJobByMajor(major);
    }

    public void addJob(JobForm form) {
        Job job = repo.addJob(form);
        Major major = majorRepo.getMajorById(form.getMajor());
        repo.addJobMajor(job, major);
    }
}
