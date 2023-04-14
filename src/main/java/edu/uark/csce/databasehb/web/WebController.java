package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class WebController {

    private final StudentRepository studentRepo;
    private final JobRepository jobRepo;
    private final MajorRepository majorRepository;
    private final ApplicationRepository applicationRepo;

    public WebController(StudentRepository studentRepo, JobRepository jobRepo, MajorRepository majorRepository, ApplicationRepository applicationRepo) {
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
        this.majorRepository = majorRepository;
        this.applicationRepo = applicationRepo;
    }

    @GetMapping("/addStudent")
    public String addStudent(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        return "add_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute StudentForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                studentRepo.addStudent(form);
                toast.setMessage(form.getStudentName() + " has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert alert-danger");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert alert-warning");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        return "add_student";
    }

    @GetMapping("/addJob")
    public String addJob(Model model) {
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        return "add_job";
    }

    @PostMapping("/addJob")
    public String addJob(@ModelAttribute JobForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                jobRepo.addJob(form);
                jobRepo.addJobToJobMajor(form);
                toast.setMessage(form.getCompanyName() + " has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert alert-danger");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert alert-warning");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        return "add_job";
    }

    @GetMapping("/addApplication")
    public String addApplication(Model model) {
        return "add_application";
    }

    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute ApplicationForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                boolean duplicateValue = applicationRepo.addApplication(form);
                if (duplicateValue) {
                    toast.setMessage("Application already exists in the database");
                    toast.setCssClass("alert alert-warning");
                } else
                    toast.setMessage("Application has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert alert-danger");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert alert-warning");
            toast.setMessage("Invalid Value(s) in form");
        }
        model.addAttribute("toast", toast);
        return "add_application";
    }

    @GetMapping("/viewStudent")
    public String viewStudent(Model model) {
        log.info("ViewStudents GET");
        List<Major> majors = majorRepository.getAllMajors();
        Boolean initialLoad = true;
        model.addAttribute("majors", majors);
        model.addAttribute("initialLoad", initialLoad);
        return "view_students";
    }

    @PostMapping("/viewStudent")
    public String viewStudents(@RequestParam("major") Integer major, Model model) {
        log.info("ViewStudents POST");
        List<Major> majors = majorRepository.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.info("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        List<Student> studentList = studentRepo.getStudentsByMajor(major);
        Boolean initialLoad = false;
        model.addAttribute("students", studentList);
        model.addAttribute("initialLoad", initialLoad);
        return "view_students";
    }

    @GetMapping("/viewJob")
    public String viewJob(Model model) {
        log.info("ViewJobs GET");
        List<Major> majors = majorRepository.getAllMajors();
        Boolean initialLoad = true;
        model.addAttribute("majors", majors);
        model.addAttribute("initialLoad", initialLoad);
        return "view_jobs";
    }
    @PostMapping("/viewJob")
    public String viewJob(@RequestParam("major") Integer major, Model model) {
        log.info("ViewJobs POST");
        List<Major> majors = majorRepository.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.info("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        List<Job> jobList = jobRepo.getJobByMajor(major);
        Boolean initialLoad = false;
        model.addAttribute("jobs", jobList);
        model.addAttribute("initialLoad", initialLoad);
        return "view_jobs";
    }

    @GetMapping("/viewApplication")
    public String viewApplication(Model model) {
        return "view_applications";
    }

    @GetMapping("/")
    public String defaultPath(Model model) {
//        String name = repo.getStudents().getmajor();
//        model.addAttribute("name", name);
        return "index";
    }

}
