package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.*;
import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.web.application.ApplicationForm;
import edu.uark.csce.databasehb.web.application.ViewApplicationForm;
import edu.uark.csce.databasehb.web.job.JobForm;
import edu.uark.csce.databasehb.web.student.StudentForm;
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

    public WebController(StudentRepository studentRepo, JobRepository jobRepo, MajorRepository majorRepository) {
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
        this.majorRepository = majorRepository;
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



    @GetMapping("/")
    public String defaultPath(Model model) {
        model.addAttribute("viewName", "home");
        return "index";
    }

}
