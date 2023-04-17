package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("viewName", "add_student");
        return "add_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute StudentForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                boolean duplicateValue = studentRepo.addStudent(form);
                if (duplicateValue) {
                    toast.setCssClass("alert-warning", "fa-question-circle");
                    toast.setMessage("Student already exists in the database, updating values instead");
                } else
                    toast.setMessage(form.getStudentName() + " has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert-danger", "fa-exclamation-triangle");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert-warning", "fa-question-circle");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_student");
        return "add_student";
    }

    @GetMapping("/addJob")
    public String addJob(Model model) {
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_job");
        return "add_job";
    }

    @PostMapping("/addJob")
    public String addJob(@ModelAttribute JobForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                boolean duplicateValue = jobRepo.addJob(form);
                if (duplicateValue) {
                    toast.setCssClass("alert-warning", "fa-question-circle");
                    toast.setMessage("Job already exists in the database");
                } else {
                    toast.setMessage(form.getCompanyName() + " has been added to the database");
                }
                jobRepo.addJobToJobMajor(form);
            } catch (Exception e) {
                toast.setCssClass("alert-danger", "fa-exclamation-triangle");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert-warning", "fa-question-circle");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = majorRepository.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_job");
        return "add_job";
    }

    @GetMapping("/addApplication")
    public String addApplication(Model model) {
        model.addAttribute("viewName", "add_application");
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
                    toast.setCssClass("alert-warning", "fa-question-circle");
                } else
                    toast.setMessage("Application has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert-danger", "fa-exclamation-triangle");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert-warning", "fa-question-circle");
            toast.setMessage("Invalid Value(s) in form");
        }
        model.addAttribute("toast", toast);
        model.addAttribute("viewName", "add_application");
        return "add_application";
    }

    @GetMapping("/viewStudent")
    public String viewStudent(Model model) {
        log.info("ViewStudents GET");
        model.addAttribute("majors", majorRepository.getAllMajors());
        model.addAttribute("initialLoad", true);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @PostMapping("/viewStudent")
    public String viewStudents(@RequestParam("major") Integer major, Model model) {
        log.info("ViewStudents POST");
        List<Major> majors = majorRepository.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.info("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        model.addAttribute("students", studentRepo.getStudentsByMajor(major));
        model.addAttribute("initialLoad", false);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @GetMapping("/viewJob")
    public String viewJob(Model model) {
        log.info("ViewJobs GET");
        model.addAttribute("majors", majorRepository.getAllMajors());
        model.addAttribute("initialLoad", true);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }

    @PostMapping("/viewJob")
    public String viewJob(@RequestParam("major") Integer major, Model model) {
        log.trace("ViewJobs POST");
        List<Major> majors = majorRepository.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.debug("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        model.addAttribute("jobs", jobRepo.getJobByMajor(major));
        model.addAttribute("initialLoad", false);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }

    @GetMapping("/viewApplication")
    public String viewApplication(Model model) {
        model.addAttribute("noList", true);
        model.addAttribute("searchMethod", 0);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    @PostMapping("/viewApplication")
    public String viewApplication(@RequestParam("searchMethod") Integer searchMethod, Model model) {
        log.info("***** viewApplication *****");
        boolean noList = true;
        switch (searchMethod) {
            case 1 -> { // View all applications
                List<ViewApplicationForm> applications = applicationRepo.getAllApplications();
                model.addAttribute("allApplications", applications);
                noList = false;
            }
            case 2 -> { // View all applications by specified majorId
                List<Major> majors = majorRepository.getAllMajors();
                model.addAttribute("majorList", majors);
            }
            case 3 -> { // View all applications by specified studentId
                List<Student> students = studentRepo.getAllStudents();
                model.addAttribute("studentList", students);
            }
            case 4 -> { // View all applications by specified jobId
                List<Job> jobs = jobRepo.getAllJobs();
                model.addAttribute("jobList", jobs);
            }
        }
        model.addAttribute("noList", noList);
        model.addAttribute("searchMethod", searchMethod);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    @PostMapping("/viewApplication2")
    public String viewApplication(@RequestParam(value = "searchMethod", required = false, defaultValue = "0") Integer searchMethod
            , @RequestParam(value = "major", required = false, defaultValue = "0") int major
            , @RequestParam(value = "studentId", required = false, defaultValue = "0") long studentId
            , @RequestParam(value = "jobId", required = false, defaultValue = "0") long jobId, Model model) {
        log.info("***** viewApplication2 *****");
        boolean noList = false;
        switch (searchMethod) {
            case 2 -> {
                model.addAttribute("majorList", majorRepository.getAllMajors());
                model.addAttribute("majorResults", applicationRepo.getApplicationByMajorId(major));
            }
            case 3 -> {
                model.addAttribute("studentList", studentRepo.getAllStudents());
                model.addAttribute("studentResults", applicationRepo.getApplicationByStudentId(studentId));
            }
            case 4 -> {
                model.addAttribute("jobList", jobRepo.getAllJobs());
                model.addAttribute("jobResults", applicationRepo.getApplicationByJobId(jobId));
            }
            default -> {
                noList = true;
            }
        }
        model.addAttribute("noList", noList);
        model.addAttribute("searchMethod", searchMethod);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    @GetMapping("/")
    public String defaultPath(Model model) {
        model.addAttribute("viewName", "home");
        return "index";
    }

}
