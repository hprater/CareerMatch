package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
                boolean duplicateValue = studentRepo.addStudent(form);
                if (duplicateValue) {
                    toast.setCssClass("alert alert-warning");
                    toast.setMessage("Student already exists in the database, updating values instead");
                } else
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
                boolean duplicateValue = jobRepo.addJob(form);
                if (duplicateValue) {
                    toast.setCssClass("alert alert-warning");
                    toast.setMessage("Job already exists in the database");
                } else {
                    toast.setMessage(form.getCompanyName() + " has been added to the database");
                }
                jobRepo.addJobToJobMajor(form);
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
        boolean noList = true;
        model.addAttribute("noList", noList);
        return "view_applications";
    }

    @PostMapping("/viewApplication")
    public String viewApplication(@RequestParam("searchMethod") Integer searchMethod, Model model) {
        boolean noList = false;
        switch (searchMethod) {
            case 1 -> { // View all applications
                List<ViewApplicationForm> applications = applicationRepo.getAllApplications();
                model.addAttribute("allApplications", applications);
                model.addAttribute("noList", noList);
            }
            case 2 -> { // View all applications by specified majorId
                List<Major> majors = majorRepository.getAllMajors();
                model.addAttribute("majorList", majors);
                model.addAttribute("noList", noList);
            }
            case 3 -> { // View all applications by specified studentId
                List<Student> students = studentRepo.getAllStudents();
                model.addAttribute("studentList", students);
                model.addAttribute("noList", noList);
            }
            case 4 -> { // View all applications by specified jobId
                List<Job> jobs = jobRepo.getAllJobs();
                model.addAttribute("jobList", jobs);
                model.addAttribute("noList", noList);
            }
            default -> {
                noList = true;
                model.addAttribute("noList", noList);
            }
        }
        return "view_applications";
    }

    @PostMapping("/viewApplication2")
    public String viewApplication(@RequestParam("searchMethod") Integer searchMethod, @RequestParam("major") int major,
                                  @RequestParam("studentId") long studentId, @RequestParam("jobId") long jobId, Model model) {
        boolean noList = false;
        switch (searchMethod) {
            case 2 -> {
                List<Major> majors = majorRepository.getAllMajors();
                model.addAttribute("majorList", majors);
                model.addAttribute("noList", noList);
                List<ViewApplicationForm> applications = applicationRepo.getApplicationByMajorId(major);
                model.addAttribute("majorResults", applications);
            }
            case 3 -> {
                List<Student> students = studentRepo.getAllStudents();
                model.addAttribute("studentList", students);
                model.addAttribute("noList", noList);
                List<ViewApplicationForm> applications = applicationRepo.getApplicationByStudentId(studentId);
                model.addAttribute("studentResults", applications);
            }
            case 4 -> {
                List<Job> jobs = jobRepo.getAllJobs();
                model.addAttribute("jobList", jobs);
                model.addAttribute("noList", noList);
                List<ViewApplicationForm> applications = applicationRepo.getApplicationByJobId(jobId);
                model.addAttribute("jobResults", applications);
            }
            default -> {
                noList = true;
                model.addAttribute("noList", noList);
            }
        }
        return "view_applications";
    }

    @GetMapping("/")
    public String defaultPath(Model model) {
//        String name = repo.getStudents().getmajor();
//        model.addAttribute("name", name);
        return "index";
    }

}
